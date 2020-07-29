package com.example.ankit.watsapplistner.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankit.watsapplistner.R;
import com.example.ankit.watsapplistner.framework.MainApplication;
import com.example.ankit.watsapplistner.model.MessageData;

import com.example.ankit.watsapplistner.utilities.MessageAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_NOTIFICATION_LISTENER_SETTINGS = 101;
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 102;
    private LinearLayoutManager llManager;
    private RecyclerView rvMessage;
    private TextView no;
    private MessageAdapter adapterMessage;
    private List<MessageData> messageDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        if (!isNotificationServiceRunning()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivityForResult(intent, ACTION_NOTIFICATION_LISTENER_SETTINGS);
        } else {
            setData();
        }
    }


    private void setData() {
        MainApplication mainApplication = (MainApplication) (MainApplication) getApplication();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                MainApplication mainApplication = (MainApplication) getApplication();

                messageDataList = mainApplication.getDatabaseInstance().dataRepo().getAll();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setProperData();
            }
        }.execute();


    }

    private void setProperData() {
        if (messageDataList != null) {
            no.setVisibility(View.GONE);
            rvMessage.setVisibility(View.VISIBLE);
            adapterMessage = new MessageAdapter(MainActivity.this, messageDataList);
            rvMessage.setAdapter(adapterMessage);
        } else {
            if (messageDataList.size() > 0) {
                no.setVisibility(View.GONE);
                rvMessage.setVisibility(View.VISIBLE);
                messageDataList.clear();
                messageDataList.addAll(messageDataList);
                adapterMessage.notifyDataSetChanged();
            } else {
                no.setVisibility(View.VISIBLE);
                rvMessage.setVisibility(View.GONE);
            }
        }
    }

    private void findView() {
        rvMessage = (RecyclerView) findViewById(R.id.rvMessage);
        no = (TextView) findViewById(R.id.no);
        llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(llManager);
    }

    private boolean isNotificationServiceRunning() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_NOTIFICATION_LISTENER_SETTINGS) {
            if(isNotificationServiceRunning())
            {
            if (!Settings.canDrawOverlays(this)) {
                checkPermissionOverLays();
            } else {
                setData();
            }}else {
                Toast.makeText(this, "You not able to get Whatsapp Notification", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
                if (!Settings.canDrawOverlays(this)) {
                    // You don't have permission
                    Toast.makeText(this, "You not able to get Pop up bubble", Toast.LENGTH_LONG).show();
                } else {
                    // Do as per your logic
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void checkPermissionOverLays () {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }
