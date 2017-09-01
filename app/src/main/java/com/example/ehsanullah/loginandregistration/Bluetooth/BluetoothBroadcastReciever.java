package com.example.ehsanullah.loginandregistration.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by SZ on 7/26/2017.
 */

public class BluetoothBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:

                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i("Broadcast status", "Bluetooth turning off broadcast Recieved");

                    if (BluetoothService.isRunning == true) {
                        context.stopService(new Intent(context, BluetoothService.class));
                        Log.i("Service status", "Service stopped from Broadcast reciever");
                        BluetoothService.isRunning = false;
                    }
                    break;
                case BluetoothAdapter.STATE_ON:
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.i("Broadcast status", "Bluetooth turning on broadcast Recieved");
                    if (BluetoothService.isRunning == false) {
                        context.startService(new Intent(context, BluetoothService.class));
                        Log.i("Service status", "Service started from Broadcast Reciever");
                        BluetoothService.isRunning = true;
                    }
                    break;
            }

        }
    }
}
