package com.example.ankit.watsapplistner.service;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.example.ankit.watsapplistner.framework.MainApplication;
import com.example.ankit.watsapplistner.model.MessageData;

import java.io.ByteArrayOutputStream;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    Context context;
    private String phonenumber;
    private MessageData messageData;
    String[] phone ;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        //  String ticker = sbn.getNotification().tickerText.toString();
        String ticker = "";
        if (sbn.getNotification().tickerText != null) {
            ticker = sbn.getNotification().tickerText.toString();
        }


        Bundle extras = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            extras = sbn.getNotification().extras;
        }
        String title = extras.getString("android.title");
        // String text = extras.getCharSequence("android.text").toString();
        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
        Bitmap id = sbn.getNotification().largeIcon;

        String text = null;
        if (extras.getCharSequence("android.text") != null) {
            text = extras.getCharSequence("android.text").toString();
        }
        if (text == null) {
            if (extras.get("android.textLines") != null) {
                CharSequence[] charText = (CharSequence[]) extras
                        .get("android.textLines");
                if (charText.length > 0) {
                    text = charText[charText.length - 1].toString();
                }
            }
        }


        //   Log.i("Package",pack);
//    Log.i("Ticker",ticker);
        //   Log.i("Title",title);
        //  Log.i("Text",text);

        if (pack.equals("com.whatsapp")) {
            Intent msgrcv = new Intent("Msg");
             //  msgrcv.putExtra("package", pack);
            msgrcv.putExtra("ticker", ticker);
            msgrcv.putExtra("title", title);
            msgrcv.putExtra("text", text);
            if(sbn.getNotification() !=null && sbn.getNotification().getShortcutId()!=null) {
                 phone = sbn.getNotification().getShortcutId().split("@");
            }
            if(phone !=null) {
                 phonenumber = phone[0];
            }


            if (id != null) {

                 messageData = new MessageData();
                messageData.setMessage(text);
                messageData.setName(title);
                messageData.setPhone(phonenumber);

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        MainApplication mainApplication = (MainApplication) getApplication();

                            mainApplication.getDatabaseInstance().dataRepo().insert(messageData);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        startService(new Intent(NotificationService.this, ChatHeadService.class));
                    }
                }.execute();


            }
        } else {
            return;
        }

    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");

    }
}