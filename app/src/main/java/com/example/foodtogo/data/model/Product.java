package com.example.foodtogo.data.model;


import com.orm.SugarRecord;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Product extends SugarRecord {
    public long productRoomId;
    public long user_id;
    public long category_id;
    public long buy_by;
    public String title;
    public String summary;
    public String image;
    private String expirationDate;
    private long created_at;
    private long updated_at;
    public String status;
    public boolean cancel_by_order;
    public Float rating;
    public String code;
    public Boolean reclame_code_creator;
    public Boolean reclame_code_buy;

    public Product(){}

    public Product(long user_id, long category_id,
                 String title, String summary, String image,
                 String expirationDate) throws Exception {

        //productRoomId = this.id;
        try{
            if (Category.findById(Category.class, category_id) != null){
                this.category_id = category_id;
            }
        }catch (android.database.sqlite.SQLiteException e){
           throw new Exception("Category no exist");
        }
        try{
            if (User.findById(User.class, user_id) != null){
                this.user_id = user_id;
            }
        }catch (android.database.sqlite.SQLiteException e){
            throw new Exception("User no exist");
        }

        this.title = title;
        this.summary = summary;
        this.image = image == null ? "" : image;
        this.expirationDate = expirationDate;
        this.created_at = System.currentTimeMillis();
        this.updated_at = System.currentTimeMillis();
        this.cancel_by_order = false;
        this.status = "CREATED";
        this.rating = 0f;
        this.code = "";
        this.reclame_code_creator = false;
        this.reclame_code_buy = false;
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


    public static String convertDateToString(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ca", "CA"));
        return dateFormat.format(date);
    }

    public static Date convertStringToDate(String date) throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ca", "CA"));
        return dateFormat.parse(date);
    }

    /// setter & getter


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getBuy_by() {
        return buy_by;
    }

    public void setBuy_by(long buy_by) {
        this.buy_by = buy_by;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCancel_by_order() {
        return cancel_by_order;
    }

    public void setCancel_by_order(boolean cancel_by_order) {
        this.cancel_by_order = cancel_by_order;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public Boolean getReclame_code_creator() {
        return reclame_code_creator;
    }

    public void setReclame_code_creator(Boolean reclame_code_creator) {
        this.reclame_code_creator = reclame_code_creator;
    }

    public Boolean getReclame_code_buy() {
        return reclame_code_buy;
    }

    public void setReclame_code_buy(Boolean reclame_code_buy) {
        this.reclame_code_buy = reclame_code_buy;
    }
}