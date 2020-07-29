package com.example.ankit.watsapplistner.utilities;

import com.example.ankit.watsapplistner.model.MessageData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface Repo {
    @Query("SELECT * FROM message")
    List<MessageData> getAll();
    @Insert
    void insert(MessageData padDatas);

}


