package com.example.foodtogo.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

@Entity
public class Category extends SugarRecord {
    public String tableName =  this.getSqlName();

    @PrimaryKey
    @NonNull
    public UUID id;
    private String name;
    private String summary;

    public Category(){}

    public Category(String username, String summary){
        this.id = UUID.randomUUID();
        this.name = username;
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public String getName() {
        return name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Long convertToLong(UUID id){
        Long val = -1L;
        final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(id.getLeastSignificantBits());
        buffer.putLong(id.getMostSignificantBits());
        final BigInteger bi = new BigInteger(buffer.array());
        val = bi.longValue() & Long.MAX_VALUE;
        return val;
    }

}