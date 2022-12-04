package com.example.foodtogo.data.model;


import com.orm.SugarRecord;
import com.orm.dsl.Table;

public class Message extends SugarRecord {
    public String test;

    public Message(){
    }

    public Message(String message){
        test = message;
    }
}
