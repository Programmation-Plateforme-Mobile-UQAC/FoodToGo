package com.example.foodtogo.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.ProductRecycleViewAdapter;
import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.FragmentHomeBinding;
import com.example.foodtogo.databinding.FragmentOrderCardEmptyBinding;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HomeFragment extends MyFragment {
    ArrayList<Product> productList;
    FragmentHomeBinding binding;
    FragmentOrderCardEmptyBinding cardEmptyBinding;
    PopupMenu popupMenu;
    List<Category> categories;
    ProductRecycleViewAdapter productRecycleViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            productList = new ArrayList<>(Product.find(Product.class,"status = 'CREATED' "));
            if (getService().user_authenticated != null)
                productRecycleViewAdapter = new ProductRecycleViewAdapter(getContext(), productList, getService().user_authenticated.getId(),getService());
            else
                productRecycleViewAdapter = new ProductRecycleViewAdapter(getContext(), productList,getService());

        }catch (Exception exception){
            exception.printStackTrace();
        }

        categories =  Category.listAll(Category.class);

        /*if (productList.isEmpty()){
            cardEmptyBinding = FragmentOrderCardEmptyBinding.inflate(inflater, container, false);
            return cardEmptyBinding.getRoot();
        }*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manageCategoryDropDown();
        searchListener();

        if (!productList.isEmpty()){
            RecyclerView recyclerView = binding.productRecycleView;
            recyclerView.setAdapter(productRecycleViewAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setHasFixedSize(true);

        } else
            binding.orderViewStub.inflate();
    }

    private void searchListener(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
    }

    private void filter(String text){
        ArrayList<Product> filteredlist = new ArrayList<>();

        for (Product item : productList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }

        productRecycleViewAdapter.setItems(filteredlist);
    }

    private void manageCategoryDropDown(){
        popupMenu = new PopupMenu(getContext(), binding.categories);
        popupMenu.inflate(R.menu.category_list_menu);

        popupMenu.setOnMenuItemClickListener(item ->{
            binding.categories.setText(item.getTitle());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                productRecycleViewAdapter.setItems(new ArrayList<>(productList.stream().filter(product -> product.category_id == Product.find(Category.class, "name = ?", binding.categories.getText().toString()).get(0).getId()).collect(Collectors.toList())));
                //productRecycleViewAdapter.setItems(new ArrayList<>(productList.stream().filter(product -> product.category_id == categories.stream().filter(category -> category.getName().equals(binding.categories.getText().toString())).collect(Collectors.toList()).get(0).getId()).collect(Collectors.toList())));
            }

            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categories.forEach(category -> popupMenu.getMenu().add(category.getName()));
        }

        binding.categories.setCursorVisible(false);
        binding.categories.setFocusable(false);
        binding.categories.setInputType(InputType.TYPE_NULL);

        binding.categories.setOnClickListener(l -> popupMenu.show());
    }
}