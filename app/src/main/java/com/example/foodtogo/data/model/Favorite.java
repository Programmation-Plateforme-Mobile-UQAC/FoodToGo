package com.example.foodtogo.data.model;



import com.example.foodtogo.data.database.SugarOrmApp;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

public class Favorite extends SugarRecord {
    public String tableName =  "Favorite";

    public long id;
    private long product_id;

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
