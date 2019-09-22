package com.packt.backgroundservicedemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MyJobIntentService2 extends JobIntentService {

    static Context contextMain = null;
    private final static String TAG = MyJobIntentService2.class.getSimpleName();

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, MyJobIntentService2.class, 18, intent);
        contextMain = context;
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String content = "";

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI, null, null, null, null);

        Log.i(TAG, "cursor: " + cursor);

        int wordCol = cursor.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);
        int defCol = cursor.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);

        Log.i(TAG, wordCol + "-" + defCol);

        while(cursor.moveToNext()) {
            String word = cursor.getString(wordCol);
            String definition =  cursor.getString(defCol);
            Log.i(TAG, word + "-" + definition);
            content += word + ": " + definition + "\r\n";
        }

        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("androidTerms", content);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        cursor.close();
    }
}
