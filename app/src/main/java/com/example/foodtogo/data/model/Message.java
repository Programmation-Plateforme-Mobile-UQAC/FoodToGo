package com.example.foodtogo.data.model;

import androidx.room.Entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Entity
public class Message extends SugarRecord {
    public String test;

    public Message(){
    }

    public Message(String message){
        test = message;
    }
}
