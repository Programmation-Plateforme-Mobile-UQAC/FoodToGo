package com.example.foodtogo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.adapters.ProductRecycleViewAdapter;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.databinding.FragmentHomeBinding;
import com.example.foodtogo.databinding.FragmentProductCardEmptyBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Product> productList;
    FragmentHomeBinding binding;
    FragmentProductCardEmptyBinding cardEmptyBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            productList = new ArrayList<>(Product.listAll(Product.class));
        }catch (java.lang.RuntimeException exception){
            productList = new ArrayList<>();
        }

        if (productList.isEmpty()){
            cardEmptyBinding = FragmentProductCardEmptyBinding.inflate(inflater, container, false);
            return cardEmptyBinding.getRoot();
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!productList.isEmpty()){
            ProductRecycleViewAdapter productRecycleViewAdapter = new ProductRecycleViewAdapter(getContext(), productList);

            RecyclerView recyclerView = binding.productRecycleView;
            recyclerView.setAdapter(productRecycleViewAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setHasFixedSize(true);
        }
    }
}