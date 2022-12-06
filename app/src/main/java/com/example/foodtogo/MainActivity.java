package com.example.foodtogo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Message;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.AddFragment;
import com.example.foodtogo.ui.ChatFragment;
import com.example.foodtogo.ui.FavoriteFragment;
import com.example.foodtogo.ui.HomeFragment;
import com.example.foodtogo.ui.ProfilFragment;
import com.example.foodtogo.ui.authenticated.LoginFragment;
import com.orm.SugarDb;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Authenticated service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = mainBinding.getRoot();
        this.setContentView(v);
        this.service = new Authenticated(getApplicationContext());

        mainBinding.bottomNavigationView.setSelectedItemId(R.id.home);
        HomeFragment firstFragmentStart = new HomeFragment();
        firstFragmentStart.setService(this.service);
        replaceFragment(firstFragmentStart);
        chunk();

        mainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                HomeFragment frag = new HomeFragment();
                frag.setService(this.service);
                replaceFragment(frag);
            }

            if (item.getItemId() == R.id.add) {
                if (this.service.user_authenticated == null) {
                    LoginFragment frag = new LoginFragment(R.id.add);
                    frag.setService(this.service);
                    replaceFragment(frag);
                } else {
                    AddFragment frag = new AddFragment(mainBinding);
                    frag.setService(this.service);
                    replaceFragment(frag);
                }
            }

            if (item.getItemId() == R.id.favorite) {
                if (this.service.user_authenticated == null) {
                    LoginFragment frag = new LoginFragment(R.id.favorite);
                    frag.setService(this.service);
                    replaceFragment(frag);
                } else {
                    FavoriteFragment frag = new FavoriteFragment();
                    frag.setService(this.service);
                    replaceFragment(frag);
                }
            }

            if (item.getItemId() == R.id.profile) {
                if (this.service.user_authenticated == null) {
                    LoginFragment frag = new LoginFragment( R.id.profile);
                    frag.setService(this.service);
                    replaceFragment(frag);
                } else {
                    ProfilFragment frag = new ProfilFragment();
                    frag.setService(this.service);
                    replaceFragment(frag);
                }
            }

            if (item.getItemId() == R.id.message) {
                if (this.service.user_authenticated == null) {
                    LoginFragment frag = new LoginFragment(R.id.message);
                    frag.setService(this.service);
                    replaceFragment(frag);
                } else {
                    ChatFragment frag = new ChatFragment();
                    frag.setService(this.service);
                    replaceFragment(frag);
                }
            }

            return true;
        });
    }

    public void chunk() {
       try {
           Category.deleteAll(Category.class);
           Category new_cat = new Category("Fruits", "...");
           Category new_cat2 = new Category("Legume", "...");
           new_cat.save();
           new_cat2.save();
       } catch (Exception e){
           Toast.makeText(getApplicationContext(), "Error loading chunk", Toast.LENGTH_SHORT).show();
           e.printStackTrace();
       }
    }

    private void replaceFragment(MyFragment fragment) {
        this.service = fragment.getService();
        fragment.setService(this.service);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}