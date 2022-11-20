package com.example.foodtogo.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.foodtogo.databinding.ActivityAddOrderBinding;


public class AddOrderActivity extends AppCompatActivity  {
    ActivityAddOrderBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
    }
}