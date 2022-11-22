package com.example.foodtogo.data.model;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class User extends SugarRecord {

    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    private String password;
    public long birthday;
    private Boolean verificated;
    private long created_at;
    private long updated_at;

    public User(){}

    public User(String firstName,String lastName,String email,String password){
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.created_at = System.currentTimeMillis();
        this.updated_at = System.currentTimeMillis();
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setUpdated_at(){
        this.updated_at = System.currentTimeMillis();
    }

    public boolean onVerifyPassword(String password){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        if(result.verified) return true;
        return false;
    }

    public boolean onVerify(){
        if(this.email.isEmpty() == false && this.password.isEmpty() == false) return true;
        return false;
    }
}