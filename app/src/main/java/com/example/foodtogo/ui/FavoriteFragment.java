package com.example.foodtogo.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.adapters.ProductRecycleViewAdapter;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.FragmentFavoriteBinding;
import com.example.foodtogo.databinding.FragmentOrderCardEmptyBinding;

import java.util.ArrayList;

public class FavoriteFragment extends MyFragment {
    FragmentFavoriteBinding binding;
    ArrayList<Product> favoriteProducts = new ArrayList<>();
    ProductRecycleViewAdapter productRecycleViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Favorite.listAll(Favorite.class).forEach(favorite -> {
                    favoriteProducts.add(Product.findById(Product.class, favorite.getProduct_id()));
                });
            } else
                favoriteProducts = new ArrayList<>();

            productRecycleViewAdapter = new ProductRecycleViewAdapter(getContext(), favoriteProducts, getService().user_authenticated.getId());
        }catch (Exception e){
            favoriteProducts = new ArrayList<>();
        }


        if (favoriteProducts.isEmpty()){
            return FragmentOrderCardEmptyBinding.inflate(inflater, container, false).getRoot();
        }

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

       if (!favoriteProducts.isEmpty()){
           RecyclerView recyclerView = binding.favoriteProductRecycleView;
           recyclerView.setAdapter(productRecycleViewAdapter);
           recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
           recyclerView.setHasFixedSize(true);
       }
    }
}
