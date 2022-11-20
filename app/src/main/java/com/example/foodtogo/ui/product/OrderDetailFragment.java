package com.example.foodtogo.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Order;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.databinding.ActivityOrderDetailBinding;
import com.example.foodtogo.ui.HomeFragment;

public class OrderDetailFragment extends Fragment {
    ActivityOrderDetailBinding binding;
    Order product;

    public OrderDetailFragment(){

    }

    public OrderDetailFragment(Order order){
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
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        });

        binding.productDetailDescriptionText.setText(product.getSummary());
        if (User.findById(User.class, Order.convertToLong(product.getUser_id())) != null)
            binding.productdetailByText.setText(User.findById(User.class, Order.convertToLong(product.getUser_id())).getUsername());
        binding.productdetailExpiresOnText.setText(product.getExpirationDate());
    }
}
