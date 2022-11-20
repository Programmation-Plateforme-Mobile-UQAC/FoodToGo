package com.example.foodtogo.data.model;

import android.util.Log;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

public class Product extends SugarRecord<Product> {
    private long user_id;
    private long category_id;
    private String title;
    private String summary;
    private String image;

    public Product(){}

    public Product( UUID user_id, UUID category_id,
                    String title, String summary, String image) {

        try{
            long longId = convertToLong(category_id);
            if (Category.findById(Category.class, longId) != null){
                this.category_id = longId;
            }
        }catch (android.database.sqlite.SQLiteException e){
            this.category_id = convertToLong(UUID.randomUUID());
        }

        try{
            long longId = convertToLong(user_id);
            if (User.findById(User.class, longId) != null){
                this.user_id = longId;
            }
        }catch (android.database.sqlite.SQLiteException e){
            this.user_id = convertToLong(UUID.randomUUID());
        }

        this.title = title;
        this.summary = summary;
        this.image = image == null ? "" : image;
        this.id = id;
    }

    public Long convertToLong(UUID id){
        Long val = -1L;
        final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(id.getLeastSignificantBits());
        buffer.putLong(id.getMostSignificantBits());
        final BigInteger bi = new BigInteger(buffer.array());
        val = bi.longValue() & Long.MAX_VALUE;
        return val;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public long getCategory_id() {
        return category_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = convertToLong(category_id);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = convertToLong(user_id);
    }
}