package com.packt.backgroundservicedemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;


public class MyJobIntentService extends JobIntentService {

    private static final String TAG = MyJobIntentService.class.getSimpleName();

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, MyJobIntentService.class, 17, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "MyJobIntentService Starts", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(TAG, "onHandleWork Thread " + Thread.currentThread().getName());

        int duration = intent.getIntExtra("sleepTime", -1);
        Log.i(TAG, "sleepTime: " + duration);
        int cntr = 0;

        while(cntr < duration) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cntr++;

            Log.i(TAG, cntr + " seconds");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "MyJobIntentService Finishes", Toast.LENGTH_SHORT).show();
    }
}
