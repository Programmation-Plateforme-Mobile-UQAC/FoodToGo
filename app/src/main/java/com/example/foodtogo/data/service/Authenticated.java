package com.example.foodtogo.data.service;
import com.example.foodtogo.data.model.User;
import java.util.List;

public class Authenticated {

    public User user_authenticated;

    public boolean register(String username){
        System.out.println("register");
        User new_user = new User(username);
        if(new_user.onVerify()){
            new_user.save();
            return true;
        }
        return false;
    }

    public boolean login(String username){
        System.out.println("login");
        List<User> check_user = User.find(User.class, "username = ?", username);
        if(check_user.size() > 0){
            user_authenticated = check_user.get(0);
            return true;
        }
        return false;
    }

    public boolean isExist(String username){
        List<User> check_user = User.find(User.class, "username = ?", username);
        if(check_user.size() > 0){
            return true;
        }
        return false;
    }


}
