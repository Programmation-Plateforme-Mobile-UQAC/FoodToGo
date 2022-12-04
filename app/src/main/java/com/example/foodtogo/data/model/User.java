package com.example.foodtogo.data.model;
import android.util.Log;

import androidx.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class User extends SugarRecord {
    public String tableName =  "User";

    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public long birthday;
    public Boolean verificated;
    public long created_at;
    public long updated_at;

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

    public String getPassword() {
        return password;
    }
}