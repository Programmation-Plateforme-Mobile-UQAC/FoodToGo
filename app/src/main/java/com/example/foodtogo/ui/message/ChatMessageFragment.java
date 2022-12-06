package com.example.foodtogo.ui.message;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.ChatRecycleViewAdapter;
import com.example.foodtogo.adapters.MessageRecycleViewAdapter;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Message;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityOrderDetailBinding;
import com.example.foodtogo.databinding.FragmentConvBinding;
import com.example.foodtogo.databinding.FragmentListChatBinding;
import com.example.foodtogo.ui.HomeFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMessageFragment extends MyFragment {
    FragmentConvBinding binding;
    Chat chat;
    List<Message> messages = new ArrayList();
    MessageRecycleViewAdapter RecycleViewAdapter;


    public ChatMessageFragment(){

    }

    public ChatMessageFragment(Chat chat){
        this.chat = chat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        messages = Message.find(Message.class, "chatid = ?", this.chat.getId().toString());
        RecycleViewAdapter = new MessageRecycleViewAdapter(getContext(), messages,getService());

        binding = FragmentConvBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();
        long chat_id = this.chat.getId();

        ImageButton button_send;
        EditText content;

        button_send = view.findViewById(R.id.button_send);
        content = view.findViewById(R.id.content);


        button_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Message new_message = new Message(
                        content.getText().toString(),
                        getService().user_authenticated.getId(),
                        chat_id
                );
                new_message.save();
                messages = Message.find(Message.class, "chatid = ?", chat.getId().toString());
                RecycleViewAdapter = new MessageRecycleViewAdapter(getContext(), messages,getService());
                setup();
            }
        });
    }

    public void setup(){
        RecyclerView recyclerView = binding.messageRecycleView;
        recyclerView.setAdapter(RecycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
    }
}
