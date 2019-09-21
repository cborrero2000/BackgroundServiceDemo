package com.packt.backgroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private String result;
    private TextView txvResult;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.textView);
    }

    public void startBackgroundService(View view) {
        Intent intent = new Intent(this, MyBackgroundService.class);
        startService(intent);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.i(TAG, "startBackgroundService");
        ft.replace(R.id.activityFragment, new BlankFragment());
        ft.commit();
    }

    public void stopBackgroundService(View view) {
        Intent intent = new Intent(this, MyBackgroundService.class);
        stopService(intent);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.i(TAG, "stopBackgroundService");
        ft.replace(R.id.activityFragment, new BlankFragment2());
        ft.commit();
    }

    public void startIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("sleepTime", 12);
        startService(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("my.local.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(MyLocalBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver MyLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        result = intent.getStringExtra("result");
            Log.i("MAIN ACTIVITY", "result" + result);
            txvResult.setText(result);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(MyLocalBroadcastReceiver);
    }

    public void startJobIntentService(View view) {
        Intent i = new Intent(this, MyJobIntentService.class);
        i.putExtra("sleepTime", 12);
        MyJobIntentService.enqueueWork(this, i);

    }
}
