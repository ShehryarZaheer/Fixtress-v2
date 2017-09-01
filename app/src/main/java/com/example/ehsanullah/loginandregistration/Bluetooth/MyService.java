package com.example.ehsanullah.loginandregistration.Bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static com.example.sz.sensorconnection.R.id.myLabel;


/**
 * Created by SZ on 8/20/2017.
 */

public class MyService extends Service {
    static final String TAG = "com.example.sz.sensorconnection.MyService";
    Intent broadcastIntent = new Intent(TAG);
    public static boolean isRunning;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private BluetoothAdapter mBluetoothAdapter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //android.os.Debug.waitForDebugger();
        isRunning = true;

        Toast.makeText(getApplicationContext(),"Service is started",Toast.LENGTH_SHORT).show();
//        sendBroadcast(broadcastIntent);


       new bufferedReaderCollector().execute();

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Service has stopped", Toast.LENGTH_SHORT).show();
    }


    boolean openBT() {

        boolean btconnected = false;

        //region Connecting and retrieving data from the bluetooth device
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
        // SerialPortService
        // ID
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            mmSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            mmOutputStream = mmSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            mmInputStream = mmSocket.getInputStream();
            btconnected = true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Log.i("mmInputStream bytes ava", mmInputStream.available() + "");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //endregion


        return btconnected;
    }

    boolean findBT() {

        boolean btfound= false;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //region Checking if the smartphone has bluetooth
        if (mBluetoothAdapter == null) {
            return false;
            //    myLabel.setText("No bluetooth adapter available");
        }
        //endregion

        //region Checking if the bluetooth is enabled or not, if not turn it on
/*
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
*/
        //endregion

        //region Looping through the bluetooth devices and find device with name HC-05
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) // this name have to be
                // replaced with your
                // bluetooth device name
                {
                    btfound = true;
                    mmDevice = device;
                    Log.v("ArduinoBT",
                            "findBT found device named " + mmDevice.getName());
                    Log.v("ArduinoBT",
                            "device address is " + mmDevice.getAddress());
                    break;
                }
            }
        }
        //endregion

        return btfound;
    }


    class bufferedReaderCollector extends AsyncTask<Void,String,String>{

        @Override
        protected String doInBackground(Void... params) {
            boolean btcheck = false;
            if(findBT()) {

              //  sendBroadcast(broadcastIntent);

                btcheck = openBT();
            }
            if(btcheck==true) {
                broadcastIntent.putExtra("btcheck",btcheck);
                sendBroadcast(broadcastIntent);
                InputStreamReader reader = new InputStreamReader(mmInputStream);


                final BufferedReader br = new BufferedReader(reader);
                /*try {
                    if (br.readLine() == null)
                        Log.i("buffered reader", "buffered reader is null");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                int lines = 0;
                try {
                    lines = br.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("lines", lines + "");
                try {
                    while (br.readLine() != null)
                        publishProgress(br.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                broadcastIntent.putExtra("btcheck",btcheck);
                sendBroadcast(broadcastIntent);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            broadcastIntent.putExtra("btcheck",true);
            broadcastIntent.putExtra("valuesLine",values[0]);
            sendBroadcast(broadcastIntent);
        }
    }
}
