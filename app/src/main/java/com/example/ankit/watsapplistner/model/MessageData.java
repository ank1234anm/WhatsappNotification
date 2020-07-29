package com.example.ankit.watsapplistner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message")
public class MessageData {
    @PrimaryKey(autoGenerate = true)
    public long id ;
    @ColumnInfo(name = "name")
    String name ;
    @ColumnInfo(name = "phone")
    String phone ;
    @ColumnInfo(name = "message")
    String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
