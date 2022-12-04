package com.example.foodtogo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodtogo.data.database.SugarOrmApp;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

@Entity
public class Favorite extends SugarRecord {
    public String tableName =  "Favorite";

    @PrimaryKey
    public long id;
    @ColumnInfo(name = "PRODUCTID")
    private long product_id;

    @ColumnInfo(name = "USERID")
    private long user_id;

    public Favorite(){
    }

    public Favorite(long product_id, long user_id){
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
