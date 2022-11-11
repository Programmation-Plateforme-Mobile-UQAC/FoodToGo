package com.example.foodtogo.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.foodtogo.databinding.ActivityProductDetailBinding;

public class ProductDetailActivity extends AppCompatActivity {
    ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setProductDetailInfo();
    }

    private void setProductDetailInfo(){
        binding.productDetailDescriptionText.setText(getIntent().getStringExtra("PRODUCT_DESCRIPTION"));
        binding.productdetailPublishedText.setText(getIntent().getStringExtra("PRODUCT_PUBLISH_DATE"));
        binding.productdetailByText.setText(getIntent().getStringExtra("PRODUCT_PUBLISHER_NAME"));
        binding.productdetailExpiresOnText.setText(getIntent().getStringExtra("PRODUCT_EXPIRE_DATE"));
        binding.ratingBar.setRating(getIntent().getFloatExtra("PRODUCT_RATING_VALUE", 0.0f));
    }
}