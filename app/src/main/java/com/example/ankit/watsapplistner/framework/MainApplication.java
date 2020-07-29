package com.example.ankit.watsapplistner.framework;

import android.app.Application;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

public class MainApplication extends MultiDexApplication {
    DataBase dataBase ;
    public DataBase getDatabaseInstance(){
        if(dataBase == null) {
            dataBase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "database ").fallbackToDestructiveMigration().build();
        }
        return dataBase;
    }

}
