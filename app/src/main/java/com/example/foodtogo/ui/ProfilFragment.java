package com.example.foodtogo.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.MessageRecycleViewAdapter;
import com.example.foodtogo.adapters.ProductRecycleViewAdapter;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Message;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.FragmentFavoriteBinding;
import com.example.foodtogo.databinding.FragmentOrderCardEmptyBinding;
import com.example.foodtogo.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

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

        TextView name,email,number,adress,points;
        EditText textCode;
        Button disconnect,reclame_code;

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        adress = view.findViewById(R.id.adress);
        disconnect = view.findViewById(R.id.disconnect);
        points = view.findViewById(R.id.points);
        textCode = view.findViewById(R.id.textCode);
        reclame_code = view.findViewById(R.id.get_coins);

        User user = getService().user_authenticated;

        name.setText(user.firstName +" " + user.lastName);
        email.setText(user.email);
        //number.setText("");
        adress.setText("");
        points.setText("Points : " + user.points);

        reclame_code.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String code = textCode.getText().toString();
                String user_id = getService().user_authenticated.getId().toString();
                List<Product> product_creator = Product.find(Product.class,"userid = ? AND reclamecodecreator = 0 AND instr(code, ?) > 0",user_id,code);
                if(product_creator.size() == 0){
                    List<Product> product_buy = Product.find(Product.class,"buyby = ? AND reclamecodebuy = 0 AND instr(code, ?) > 0",user_id,code);
                    if(product_buy.size() > 0){
                        Product prod = product_buy.get(0);
                        prod.reclame_code_buy = true;
                        user.points = user.points + 10;
                        if (prod.reclame_code_creator == true) {
                            prod.status = "DONE";
                        }
                        prod.save();
                        user.save();
                        Toast.makeText(getContext(), "Ajout des points", Toast.LENGTH_SHORT).show();
                        points.setText("Points : " + user.points);
                    }else {
                        Toast.makeText(getContext(), "Code mauvais", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Product prod = product_creator.get(0);
                    prod.reclame_code_creator = true;
                    user.points = user.points + 10;
                    if (prod.reclame_code_buy == true) {
                        prod.status = "DONE";
                    }
                    prod.save();
                    user.save();
                    points.setText("Points : " + user.points);
                    Toast.makeText(getContext(), "Ajout des points", Toast.LENGTH_SHORT).show();

                }
            }
        });

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
