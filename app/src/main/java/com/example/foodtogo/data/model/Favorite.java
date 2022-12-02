package com.example.foodtogo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.util.List;

@Entity
public class Favorite extends SugarRecord<Favorite> {
    public String tableName =  this.getSqlName();

    @PrimaryKey
    public long id;
    @ColumnInfo(name = "product_id")
    private long product_id;


    public Favorite(long product_id){
        this.product_id = product_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
