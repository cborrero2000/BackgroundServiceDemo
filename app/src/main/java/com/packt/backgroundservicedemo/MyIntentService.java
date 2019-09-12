package com.packt.backgroundservicedemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyIntentService() {
        super("MyBackgroundThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Task Execution Starts", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     *               This may be null if the service is being restarted after
     *               its process has gone away; see
     *               {@link Service#onStartCommand}
     *               for details.
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent Thread " + Thread.currentThread().getName());

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

        Intent localIntent = new Intent("my.local.broadcast");
        localIntent.putExtra( "result","Task executed in " + cntr + " seconds");
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy Thread " + Thread.currentThread().getName());
        Toast.makeText(this, "Task Execution Finishes", Toast.LENGTH_SHORT).show();
    }
}
