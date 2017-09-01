package com.example.ehsanullah.loginandregistration.StressDetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.Bluetooth.MyService;
import com.example.ehsanullah.loginandregistration.R;

import java.util.ArrayList;

public class StressDetectionActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvconnecting,tvnoHand, tvresult;
    ArrayList<Integer> averageValues;
    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_detection);
        progressBar = (ProgressBar) findViewById(R.id.stressProgressBar);
        tvconnecting = (TextView) findViewById(R.id.tvcalculating);
        tvnoHand = (TextView) findViewById(R.id.tvnohand);
        tvresult = (TextView) findViewById(R.id.tvresult);
         averageValues = new ArrayList<>();


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                   String valuesLine= intent.getStringExtra("valuesLine");
                   String all_values[]= valuesLine.split(":");

                if(all_values.length>3)
                {
                    if(!all_values[3].equals("0")){

                        averageValues.add(Integer.valueOf(all_values[3]));
                        tvnoHand.setText("Reading your values");
                        progressBar.setProgress(averageValues.size());
                    }
                    else
                    {
                        tvnoHand.setText("Please place your hand on the sensor");
                    }
                }
                else
                {
                    tvnoHand.setText("Please place your hand on the sensor");

                }

                if(averageValues.size()==100) {
                    stopService(new Intent(StressDetectionActivity.this, MyService.class));

                    int average = calculateAverage(averageValues);
                    progressBar.setVisibility(View.INVISIBLE);

                    if (average > 6)
                        tvresult.setText("You are feeling stressed");
                    else
                        tvresult.setText("You are feeling normal");
                }

            }
        };

        registerReceiver(receiver,new IntentFilter(MyService.TAG));

        MyService.isStressActivityRunnning = true;

    }

    private int calculateAverage(ArrayList<Integer> averageValues) {
        int sum = 0;
        for(int i = 0 ; i<averageValues.size();i++){
            sum += averageValues.get(i);
        }

        return sum/averageValues.size();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(MyService.TAG));
    }
}
