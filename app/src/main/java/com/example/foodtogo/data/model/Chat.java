package com.example.foodtogo.data.model;


import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Chat extends SugarRecord {
    public UUID id;
    public long send_by;
    public long send_to;
    public long product_id;
    public long created_at;

    public Chat(){
    }

    public Chat(long send_by, long send_to,long product_id){

        this.id = UUID.randomUUID();
        this.send_by = send_by;
        this.send_to = send_to;
        this.product_id = product_id;
        this.created_at = System.currentTimeMillis();

    }
}
