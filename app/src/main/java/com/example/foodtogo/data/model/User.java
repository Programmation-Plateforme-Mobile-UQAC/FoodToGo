package com.example.foodtogo.data.model;
import com.orm.SugarRecord;


public class User extends SugarRecord {

    public String username;

    public User(){}

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean onVerify(){
        if(this.username.isEmpty() == false) return true;
        return false;
    }
}