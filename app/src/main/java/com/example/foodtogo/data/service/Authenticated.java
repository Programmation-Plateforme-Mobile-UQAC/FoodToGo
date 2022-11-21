package com.example.foodtogo.data.service;
import com.example.foodtogo.data.model.User;
import java.util.List;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Authenticated {

    public User user_authenticated;

    public boolean register(String firstName,String lastName,String email,String password){
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User new_user = new User(firstName,lastName,email,bcryptHashString);
        if(new_user.onVerify()){
            new_user.save();
            return true;
        }
        return false;
    }

    public boolean login(String email,String password){
        List<User> check_user = User.find(User.class, "email = ?", email);
        if(check_user.size() > 0){
            if(check_user.get(0).onVerifyPassword(password)){
                user_authenticated = check_user.get(0);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isExist(String email){
        List<User> check_user = User.find(User.class, "email = ?", email);
        if(check_user.size() > 0){
            return true;
        }
        return false;
    }


}
