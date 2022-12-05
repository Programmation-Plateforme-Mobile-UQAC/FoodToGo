package com.example.foodtogo.data.service;

import android.content.Context;

import androidx.room.Room;

import com.example.foodtogo.data.database.AppDatabase;
import com.example.foodtogo.data.model.User;
import java.util.List;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Authenticated {

    public User user_authenticated;
    public AppDatabase db;

    public Authenticated(Context context){
        db = Room.databaseBuilder(context, AppDatabase.class, "databaseFoodToGo.db")
                .createFromAsset("test234.db")
                .allowMainThreadQueries()
                .build();
    }

    public Authenticated(){

    }


    public boolean register(String firstName,String lastName,String email,String password){
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User new_user = new User(firstName,lastName,email,bcryptHashString);
        if(new_user.onVerify()){
            new_user.save();
            return true;
        }
        return false;
    }

    public User login(String email,String password) throws Exception{
        List<User> check_user = User.find(User.class, "email = ?", email);
        if(check_user.size() > 0){
            if(check_user.get(0).onVerifyPassword(password)){
                return check_user.get(0);
            }
            throw new Exception("Bad password");
        }
        throw new Exception("User not exist");
    }

    public boolean isExist(String email){
        List<User> check_user = User.find(User.class, "email = ?", email);
        if(check_user.size() > 0){
            return true;
        }
        return false;
    }


}
