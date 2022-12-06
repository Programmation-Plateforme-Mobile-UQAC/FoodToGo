package com.example.foodtogo.utils;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtogo.R;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.AddFragment;
import com.example.foodtogo.ui.ChatFragment;
import com.example.foodtogo.ui.FavoriteFragment;
import com.example.foodtogo.ui.HomeFragment;
import com.example.foodtogo.ui.ProfilFragment;

public class Util {
    public static void navigateTo(View view, MyFragment destination){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, destination).commit();
    }

    public static MyFragment createFragmentFromId(int type, Authenticated service){
        MyFragment fragment = new HomeFragment();

        if (type == R.id.home){
            fragment = new HomeFragment();
            fragment.setService(service);
            return fragment;
        } else if (type == R.id.favorite){
            fragment = new FavoriteFragment();
            fragment.setService(service);
            return fragment;
        }else if (type == R.id.add){
            fragment = new AddFragment();
            fragment.setService(service);
            return fragment;
        }else if (type == R.id.message){
            fragment = new ChatFragment();
            fragment.setService(service);
            return fragment;
        }else if (type == R.id.profile){
            fragment = new ProfilFragment();
            fragment.setService(service);
            return fragment;
        }

        Toast.makeText(fragment.getContext(), "Un erreur s'est produite", Toast.LENGTH_SHORT).show();
        return fragment;
    }
}
