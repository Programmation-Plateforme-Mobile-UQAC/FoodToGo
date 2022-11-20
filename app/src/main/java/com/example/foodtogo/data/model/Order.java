package com.example.foodtogo.data.model;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Order extends SugarRecord<Order> {
    private UUID user_id;
    private UUID category_id;
    private UUID buy_by;
    private String type;
    private String title;
    private String summary;
    private String image;
    private Date expirationDate;
    private Date created_at;
    private boolean status;
    private boolean cancel_by_order;

    public Order(){}

    public Order(UUID user_id, UUID category_id,
                 String title, String type, String summary, String image, String expirationDate) throws ParseException {

        try{
            long longId = convertToLong(category_id);
            if (Category.findById(Category.class, longId) != null){
                this.category_id = category_id;
            }
        }catch (android.database.sqlite.SQLiteException e){
            this.category_id = UUID.randomUUID();
        }

        try{
            long longId = convertToLong(user_id);
            if (User.findById(User.class, longId) != null){
                this.user_id = user_id;
            }
        }catch (android.database.sqlite.SQLiteException e){
            this.user_id = UUID.randomUUID();
        }

        this.title = title;
        this.type = type;
        this.summary = summary;
        this.image = image == null ? "" : image;
        this.expirationDate = convertStringToDate(expirationDate);
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

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public String getExpirationDate() {
        return convertDateToString(expirationDate);
    }

    public UUID getBuy_by() {
        return buy_by;
    }

    public boolean isCancel_by_order() {
        return cancel_by_order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
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
        this.user_id = user_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCancel_by_order(boolean cancel_by_order) {
        this.cancel_by_order = cancel_by_order;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private String convertDateToString(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ca", "CA"));
        return dateFormat.format(date);
    }

    private Date convertStringToDate(String date) throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ca", "CA"));
        return dateFormat.parse(date);
    }
}