package com.example.ehsanullah.loginandregistration.Bluetooth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.ehsanullah.loginandregistration.activities.BluetoothConnectionActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by SZ on 7/21/2017.
 */

public class BluetoothService extends Service {
    public final static String TAG = "com.example.linvor.MainActivity";
    Intent intent1 = new Intent(TAG);
    static boolean isRunning = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        android.os.Debug.waitForDebugger();

        super.onStartCommand(intent, flags, startId);
        //android.os.Debug.waitForDebugger();
        Log.i("service status","service started");
        isRunning = true;
        InputStreamReader reader = new InputStreamReader(BluetoothConnectionActivity.mmInputStream);

        BufferedReader br = new BufferedReader(reader);
         /*   try {
                if (br.readLine() == null)
                    Log.i("buffered reader", "buffered reader is null");
            } catch (IOException e) {
                e.printStackTrace();
            }
*/

        String line;
        try {
            int lines = br.read();
            Log.i("lines",lines+"");
            while ((br.readLine()) != null) {
                // arrayList.add(br.readLine());
                Log.i("line", br.readLine());
                intent1.putExtra("valuesLine",br.readLine());
                sendBroadcast(intent1);
                // perform your task here
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
