package com.example.foodtogo.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.foodtogo.R;

import org.jetbrains.annotations.Nullable;

public class AddProductActivity extends AppCompatActivity  {
    ImageButton imageButton1, imageButton2, imageButton3;
    EditText foodTypeEdit, namePostEdit, productDescriptionEdit, expirationDateEdit;
    Button addPostButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_product);

        bindVariables();

    }

    private void bindVariables(){
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);

        foodTypeEdit = findViewById(R.id.foodTypeEdit);
        namePostEdit = findViewById(R.id.namePostEdit);
        productDescriptionEdit = findViewById(R.id.productDescriptionEdit);
        expirationDateEdit = findViewById(R.id.expirationDateEdit);

        addPostButton = findViewById(R.id.addPostButton);
    }
}