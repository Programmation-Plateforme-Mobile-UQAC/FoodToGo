package com.example.foodtogo.ui.order;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityOrderDetailBinding;
import com.example.foodtogo.ui.ChatFragment;
import com.example.foodtogo.ui.HomeFragment;
import com.example.foodtogo.ui.authenticated.LoginFragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class OrderDetailFragment extends MyFragment {
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
            HomeFragment home = new HomeFragment();
            home.setService(getService());
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, home).commit();
        });

        if(getService().user_authenticated == null){
            binding.productDetailOrderButton.setVisibility(view.GONE);
        }else{
            binding.productDetailOrderButton.setVisibility(view.VISIBLE);
        }
        binding.productDetailDescriptionText.setText(product.getSummary());
        if (User.findById(User.class, product.user_id) != null)
            binding.productdetailByText.setText(User.findById(User.class, product.user_id).firstName);
        binding.productdetailExpiresOnText.setText(product.getExpirationDate());
        binding.productdetailPublishedText.setText(DateFormat.getDateInstance().format(new Date(product.getCreated_at())));

        byte[] decodedString = Base64.decode(product.getImage(), Base64.DEFAULT);
        binding.productImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        binding.productDetailOrderButton.setOnClickListener(l -> {

            String send_by = getService().user_authenticated.getId().toString();
            String send_to = Long.toString(product.getUser_id());
            List<Chat> chat_exist = Chat.find(Chat.class, "sendto = ? & sendby = ?", send_to,send_by);
            if(send_by.equals(send_to)){
                Toast.makeText(getContext(), "Vous etre le donneur vous pouvez pas reserver votre propre annonce", Toast.LENGTH_SHORT).show();

            }
            else if(chat_exist.size() > 0){
                Toast.makeText(getContext(), "Vous avez déja démarrer une conversation avec le donneur", Toast.LENGTH_SHORT).show();
            }
            else{
                Chat new_chat = new Chat(
                        getService().user_authenticated.getId(),
                        product.getUser_id(),
                        product.getId()
                );
                new_chat.save();
                Toast.makeText(getContext(), "Vous aller démarrer une conversation avec le donneur", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) l.getContext();
                ChatFragment frag = new ChatFragment();
                frag.setService(getService());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
            }
        });
    }
}
