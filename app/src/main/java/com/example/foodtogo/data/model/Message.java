package com.example.foodtogo.data.model;


import com.orm.SugarRecord;

import java.util.Date;
import java.util.UUID;

public class Message extends SugarRecord {
    public UUID id;
    public long send_by;
    public long chat_id;
    public String message;
    public long created_at;


    public Message(){
    }

    public Message(String message,long send_by,long chat_id){

        this.id = UUID.randomUUID();
        this.send_by = send_by;
        this.chat_id = chat_id;
        this.message = message;
        this.created_at = System.currentTimeMillis();

    }
}
