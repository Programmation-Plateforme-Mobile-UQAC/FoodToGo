package com.example.foodtogo.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.foodtogo.R;
import com.example.foodtogo.databinding.ActivityAddProductBinding;

import org.jetbrains.annotations.Nullable;

public class AddProductActivity extends AppCompatActivity  {
    ActivityAddProductBinding binding;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
    }
}