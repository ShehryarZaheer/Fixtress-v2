package com.example.ehsanullah.loginandregistration.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.RecognizeActivity;
import com.example.ehsanullah.loginandregistration.activities.StressDetectionActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BluetoothConnectionActivity extends Activity {

    ProgressBar progressBar;
    TextView myLabel;
    EditText myTextbox;
    boolean bluetoothFound = false;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    public static InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    private Uri mUriPhotoTaken;
    private BroadcastReceiver receiver;
    //region Commented variables
    //TextView tvheart, tvskin, tvresp, tvaverage;
    // BroadcastReceiver receiver;
    //endregion


    static InputStream getMmInputStream() {
        return mmInputStream;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connection);
        progressBar = (ProgressBar) findViewById(R.id.bluetooth_progress_bar);

        //region Broadcast receiver onReceive
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                progressBar.setVisibility(View.GONE);
                if (intent.getBooleanExtra("btcheck", false) == true) {


                    new SweetAlertDialog(BluetoothConnectionActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Connection Successful")
                            .setContentText("You are ready to check yourself")
                            .setConfirmText("Continue to Stress Detection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(BluetoothConnectionActivity.this,StressDetectionActivity.class));
                                }
                            })
                            .show();
                } else {

                    stopService(new Intent(BluetoothConnectionActivity.this, MyService.class));
                    final SweetAlertDialog dialog = new SweetAlertDialog(BluetoothConnectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Connection Unsuccessful")
                            .setContentText("Device not connected")
                            .setCancelText("Retry")
                            .setConfirmText("Continue to Image Detection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    takePhoto();
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startService(new Intent(BluetoothConnectionActivity.this, MyService.class));
                                    sweetAlertDialog.dismissWithAnimation();
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });
                    dialog.show();


                }

            }
        };
        //endregion

        registerReceiver(receiver, new IntentFilter(MyService.TAG));
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
            progressBar.setVisibility(View.GONE);

        } else {

            startService(new Intent(BluetoothConnectionActivity.this, MyService.class));
        }

        //region Commented BroadCast Reciever code
/*
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String valuesLine = intent.getStringExtra("valuesLine");
                String valuess[] = valuesLine.split(":");
                myLabel.setText(valuesLine);
                tvskin.setText(valuess[0]);
                if (valuess.length > 1)
                    tvheart.setText(valuess[1]);
                if (valuess.length > 2)
                    tvresp.setText(valuess[2]);
                if (valuess.length > 3) {
                    if (Integer.parseInt(valuess[3]) <= 10)
                        tvaverage.setText(valuess[3]);
                }
            }
        };

        Button openButton = (Button) findViewById(R.id.open);
        Button closeButton = (Button) findViewById(R.id.close);
        tvheart = (TextView) findViewById(R.id.tvheart);
        tvskin = (TextView) findViewById(R.id.tvskin);
        tvresp = (TextView) findViewById(R.id.tvrespiration);
        tvaverage = (TextView) findViewById(R.id.tvaverage);
        myLabel = (TextView) findViewById(R.id.mylabel);
        // Open Button
*/
        //endregion

        //new dataLoader().execute();

    }

    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        } else {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("HC-05")) // this name have to be
                    // replaced with your
                    // bluetooth device name
                    {
                        mmDevice = device;
                        Log.v("ArduinoBT",
                                "findBT found device named " + mmDevice.getName());
                        Log.v("ArduinoBT",
                                "device address is " + mmDevice.getAddress());
                        break;
                    /*new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Connection Successful")
                            .setContentText("Your device has been conneted")
                            .setConfirmText("Continue")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                }
                            })

                            .show();
*/

                    }
                }
            }
        }
    }
// myLabel.setText("Bluetooth Device Found");


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("HC-05")) // this name have to be
                    // replaced with your
                    // bluetooth device name
                    {
                        bluetoothFound = true;
                        mmDevice = device;
                        Log.v("ArduinoBT",
                                "findBT found device named " + mmDevice.getName());
                        Log.v("ArduinoBT",
                                "device address is " + mmDevice.getAddress());
                        break;
                    /*new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Connection Successful")
                            .setContentText("Your device has been conneted")
                            .setConfirmText("Continue")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                }
                            })

                            .show();
*/

                    }
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(receiver, new IntentFilter(BluetoothService.TAG));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //      unregisterReceiver(receiver);
    }

    void openBT() throws IOException {

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
        // SerialPortService
        // ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        Log.i("mmInputStream bytes ava", mmInputStream.available() + "");
        //myLabel.setText("Bluetooth Opened");
        //beginListenForData();
        bluetoothFound = true;

    }

    /*void beginListenForData() {

        // character


        final Handler handler = new Handler();
        final byte delimiter = 10; // This is the ASCII code for a newline

        final ArrayList<String> arrayList = new ArrayList<String>();
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                StringBuffer stringBuffer = new StringBuffer();

                int charcounter = 0;

                InputStreamReader reader;
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        reader = new InputStreamReader(mmInputStream);
                        reader.read();

                        BufferedReader br = new BufferedReader(reader);
                        if (br.readLine() == null)
                            Log.i("buffered reader", "buffered reader is null");


                        String line;
                        try {
                            while ((line = br.readLine()) != null) {
                                // arrayList.add(br.readLine());
                                Log.i("line", br.readLine());

                                // perform your task here
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            stopWorker = true;
                        }
*//*

                int bytesAvailable = 0;
                try {
                    bytesAvailable = mmInputStream.available();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bytesAvailable > 0) {
                    byte[] packetBytes = new byte[bytesAvailable];
                    try {
                        mmInputStream.read(packetBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                        stopWorker = true;
                    }
                    for (int i = 0; i < bytesAvailable; i++) {
                        byte b = packetBytes[i];
                        byte[] encodedBytes = new byte[readBufferPosition];
                        System.arraycopy(readBuffer, 0,
                                encodedBytes, 0,
                                encodedBytes.length);
                        String data = null;
                        try {
                            data = new String(
                                    encodedBytes, "US-ASCII");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        stringBuffer.append(data);
                        if (data.equals(":"))
                            charcounter++;

                        readBufferPosition = 0;

                        readBuffer[readBufferPosition++] = b;
                        if (b==10) {
                            arrayList.add(stringBuffer.toString());
                            Log.e("string buffer", stringBuffer.toString());

                            final StringBuffer finalStringBuffer = stringBuffer;
                            final String array[] = finalStringBuffer.toString().split(":");
                            handler.post(new Runnable() {
                                public void run() {
                                    tvheart.setText(array[0] + "");
                                    if (array[1] != null)
                                        tvskin.setText(array[1] + "");
                                    if (array[2] != null)
                                        tvresp.setText(array[2] + "");
                                }
                            });

                            stringBuffer = new StringBuffer();
                            charcounter = 0;


                        }


                    }
                }

            }


        });

        workerThread.start();
*//*
*//*
                        Intent intent = new Intent(MainActivity.this, BluetoothService.class);
                        startService(intent);
*//*

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
*/
    void sendData() throws IOException {
        String msg = myTextbox.getText().toString();
        msg += "";
        myLabel.setText("Data Sent " + msg);
    }

    void onButton() throws IOException {
        mmOutputStream.write("1".getBytes());
    }

    void offButton() throws IOException {
        mmOutputStream.write("2".getBytes());
    }

    void closeBT() throws IOException {

        //stopService(new Intent(MainActivity.this, BluetoothService.class));
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }

    /*class dataLoader extends AsyncTask<InputStream, String, Void> {

        @Override
        protected Void doInBackground(InputStream... inputStreams) {
            findBT();
            try {
                openBT();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStreamReader reader = new InputStreamReader(mmInputStream);

            BufferedReader br = new BufferedReader(reader);
         *//*   try {
                if (br.readLine() == null)
                    Log.i("buffered reader", "buffered reader is null");
            } catch (IOException e) {
                e.printStackTrace();
            }
*//*

            String line;
            try {
                int lines = br.read();
                while ((br.readLine()) != null) {
                    // arrayList.add(br.readLine());
                    //  Log.i("line", br.readLine());
                    publishProgress(br.readLine() + "");
                    // perform your task here
                }
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String valuess[] = values[0].split(":");
            myLabel.setText(values[0]);
            tvskin.setText(valuess[0]);
            if (valuess.length > 1)
                tvheart.setText(valuess[1]);
            if (valuess.length > 2)
                tvresp.setText(valuess[2]);
            if (valuess.length > 3) {
                if (Integer.parseInt(valuess[3]) <= 10)
                    tvaverage.setText(valuess[3]);

            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    */
    class dataLoader extends AsyncTask<InputStream, String, Boolean> {

        @Override
        protected Boolean doInBackground(InputStream... inputStreams) {
            findBT();
            //region opeBT
            try {
                openBT();
            } catch (IOException e) {
                e.printStackTrace();
                bluetoothFound = false;
            }

            //endregion
            return bluetoothFound;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressBar.setVisibility(View.GONE);
            new SweetAlertDialog(BluetoothConnectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Connection Unsuccessful")
                    .setContentText("Device not connected")
                    .setConfirmText("Continue to Image Detection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            takePhoto();
                        }
                    })
                    .show();
        }
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                mUriPhotoTaken = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                startActivity(new Intent(this, RecognizeActivity.class));
            } catch (IOException e) {
                //setInfo(e.getMessage());
            }
        }
    }
}

