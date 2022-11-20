package com.example.foodtogo.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.foodtogo.databinding.ActivityOrderDetailBinding;

public class OrderDetailActivity extends AppCompatActivity {
    ActivityOrderDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setProductDetailInfo();
    }

    private void setProductDetailInfo(){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            binding.productDetailDescriptionText.setText(extras.getString("PRODUCT_DESCRIPTION"));
            binding.productdetailPublishedText.setText(extras.getString("PRODUCT_PUBLISH_DATE"));
            binding.productdetailByText.setText(extras.getString("PRODUCT_PUBLISHER_NAME"));
            binding.productdetailExpiresOnText.setText(extras.getString("PRODUCT_EXPIRE_DATE"));
            binding.ratingBar.setRating(extras.getFloat("PRODUCT_RATING_VALUE", 0.0f));
        }
    }
}