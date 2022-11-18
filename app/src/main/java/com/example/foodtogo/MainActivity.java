package com.example.foodtogo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
    }
}