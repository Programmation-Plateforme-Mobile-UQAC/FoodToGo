package com.example.foodtogo.ui.order;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.databinding.ActivityOrderDetailBinding;
import com.example.foodtogo.ui.HomeFragment;

import java.text.DateFormat;
import java.util.Date;

public class OrderDetailFragment extends Fragment {
    ActivityOrderDetailBinding binding;
    Product product;

    public OrderDetailFragment(){

    }

    public OrderDetailFragment(Product order){
        this.product = order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityOrderDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.productDetailBackButton.setOnClickListener(l -> {
            AppCompatActivity activity = (AppCompatActivity) l.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new HomeFragment()).commit();
        });

        binding.productDetailDescriptionText.setText(product.getSummary());
        if (User.findById(User.class, product.user_id) != null)
            binding.productdetailByText.setText(User.findById(User.class, product.user_id).firstName);
        binding.productdetailExpiresOnText.setText(product.getExpirationDate());
        binding.productdetailPublishedText.setText(DateFormat.getDateInstance().format(new Date(product.getCreated_at())));

        byte[] decodedString = Base64.decode(product.getImage(), Base64.DEFAULT);
        binding.productImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
    }
}
