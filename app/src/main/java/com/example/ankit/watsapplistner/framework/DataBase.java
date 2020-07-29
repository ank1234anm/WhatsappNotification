package com.example.ankit.watsapplistner.framework;

import com.example.ankit.watsapplistner.model.MessageData;
import com.example.ankit.watsapplistner.utilities.Repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MessageData.class}, version = 1)
public abstract  class DataBase extends RoomDatabase {
    public abstract Repo dataRepo();
}