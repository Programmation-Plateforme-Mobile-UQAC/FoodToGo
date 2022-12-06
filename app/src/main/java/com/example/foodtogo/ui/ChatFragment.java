package com.example.foodtogo.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.ChatRecycleViewAdapter;
import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.FragmentHomeBinding;
import com.example.foodtogo.databinding.FragmentListChatBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatFragment extends MyFragment {
    List<Chat> chatList;
    FragmentListChatBinding binding;
    ChatRecycleViewAdapter RecycleViewAdapter;
    Boolean gift = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String user_id = getService().user_authenticated.getId().toString();
        try {
            chatList = Chat.find(Chat.class, "sendto = ?", user_id);
            if (getService().user_authenticated != null)
                RecycleViewAdapter = new ChatRecycleViewAdapter(getContext(), chatList, getService().user_authenticated.getId(),this.gift);
            else
                RecycleViewAdapter = new ChatRecycleViewAdapter(getContext(), chatList,this.gift);

        }catch (Exception exception){
            exception.printStackTrace();
        }

        binding = FragmentListChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();
        String user_id = getService().user_authenticated.getId().toString();
        Button foodtoget,foodtogo;

        foodtoget = view.findViewById(R.id.btn_my_foodtoget);
        foodtogo = view.findViewById(R.id.btn_my_foodtogo);

        foodtoget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                foodtogo.setBackgroundColor(Color.parseColor("#9C9C9C"));
                foodtoget.setBackgroundColor(Color.parseColor("#7cb442"));
                chatList = Chat.find(Chat.class, "sendby = ?", user_id);
                RecycleViewAdapter = new ChatRecycleViewAdapter(getContext(), chatList,true);
                setup();
            }
        });

        foodtogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                foodtoget.setBackgroundColor(Color.parseColor("#9C9C9C"));
                foodtogo.setBackgroundColor(Color.parseColor("#7cb442"));
                chatList = Chat.find(Chat.class, "sendto = ?", user_id);
                RecycleViewAdapter = new ChatRecycleViewAdapter(getContext(), chatList,false);
                setup();
            }
        });

    }

    public void setup(){
        RecyclerView recyclerView = binding.chatRecycleView;
        recyclerView.setAdapter(RecycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
    }
}