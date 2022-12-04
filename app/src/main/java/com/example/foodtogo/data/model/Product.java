package com.example.foodtogo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodtogo.data.database.SugarOrmApp;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Entity
public class Product extends SugarRecord {
    public String tableName =  "Product";

    @PrimaryKey
    public long productRoomId;
    @ColumnInfo(name = "user_id")
    public long user_id;
    @ColumnInfo(name = "category_id")
    public long category_id;
    @ColumnInfo(name = "buy_by")
    private long buy_by;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "summary")
    public String summary;
    @ColumnInfo(name = "image")
    public String image;
    @ColumnInfo(name = "expirationDate")
    private String expirationDate;
    private long created_at;
    private long updated_at;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "cancel_by_order")
    public boolean cancel_by_order;

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
        this.status = "Created";
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
}