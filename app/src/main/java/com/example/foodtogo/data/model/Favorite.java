package com.example.foodtogo.data.model;



import com.orm.SugarRecord;

public class Favorite extends SugarRecord {
    public long id;
    public long product_id;
    public long user_id;

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
