package com.example.foodtogo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.data.viewmodel.UserViewModel;
import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.AddFragment;
import com.example.foodtogo.ui.HomeFragment;
import com.example.foodtogo.ui.authenticated.LoginFragment;

import java.util.List;
import java.util.Random;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Authenticated service;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = mainBinding.getRoot();
        this.setContentView(v);
        this.service = new Authenticated();

        mainBinding.bottomNavigationView.setSelectedItemId(R.id.home);
        HomeFragment firstFragmentStart = new HomeFragment();
        firstFragmentStart.setService(this.service);
        replaceFragment(firstFragmentStart);

        mainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                HomeFragment frag = new HomeFragment();
                frag.setService(this.service);
                replaceFragment(frag);
            }

            if (item.getItemId() == R.id.add){
                if(this.service.user_authenticated == null){
                    LoginFragment frag = new LoginFragment();
                    frag.setService(this.service);
                    replaceFragment(frag);
                }else{
                    AddFragment frag = new AddFragment(mainBinding);
                    frag.setService(this.service);
                    replaceFragment(frag);
                }
            }

            if(item.getItemId() == R.id.favorite){
                List<User> users = User.listAll(User.class);
                long uuid_user = users.get(0).getId();
                Product.deleteAll(Product.class);
                Category.deleteAll(Category.class);
                Category new_cat = new Category("Fruits","...");
                Category new_cat2 = new Category("Legume","...");
                new_cat.save();
                new_cat2.save();
                List<Category> categories = Category.listAll(Category.class);
                Integer i = 0;
                String[] random_orders = {
                        "Orange",
                        "Banane",
                        "Peche",
                        "Tomate",
                };
                while (i <= 4) {
                    Category cat_selected = categories.get(0);
                    try {
                        Product new_order = new Product(
                                uuid_user,cat_selected.getId(),
                                random_orders[new Random().nextInt(random_orders.length)],
                                "Test description","",
                                "01/02/2023"
                        );
                        new_order.save();

                    }catch (Exception e){
                        Log.w("Error",e.getMessage());
                    }


                    ++i;
                }

            }

            return true;
        });
    }

    private void replaceFragment(MyFragment fragment){
        this.service = fragment.getService();
        fragment.setService(this.service);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}