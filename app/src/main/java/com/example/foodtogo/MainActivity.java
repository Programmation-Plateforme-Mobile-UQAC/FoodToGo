package com.example.foodtogo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.AddFragment;
import com.example.foodtogo.ui.HomeFragment;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = mainBinding.getRoot();
        this.setContentView(v);

        mainBinding.bottomNavigationView.setSelectedItemId(R.id.home);
        replaceFragment(new HomeFragment());

        mainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
            }

            if (item.getItemId() == R.id.add){
                replaceFragment(new AddFragment(mainBinding));
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}