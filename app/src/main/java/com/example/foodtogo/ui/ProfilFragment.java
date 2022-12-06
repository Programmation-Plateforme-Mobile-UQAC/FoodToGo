package com.example.foodtogo.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.ProductRecycleViewAdapter;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.FragmentFavoriteBinding;
import com.example.foodtogo.databinding.FragmentOrderCardEmptyBinding;
import com.example.foodtogo.databinding.FragmentProfileBinding;

import java.util.ArrayList;

public class ProfilFragment extends MyFragment {
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TextView name,email,number,adress;
        Button disconnect;

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        adress = view.findViewById(R.id.adress);
        disconnect = view.findViewById(R.id.disconnect);

        User user = getService().user_authenticated;

        name.setText(user.firstName +" " + user.lastName);
        email.setText(user.email);
        number.setText("");
        adress.setText("");

        disconnect.setOnClickListener(l ->{
            HomeFragment frag = new HomeFragment();
            Authenticated service = getService();
            service.user_authenticated = null;
            frag.setService(service);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
        });


    }
}
