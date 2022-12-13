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
import android.widget.RelativeLayout;
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
import com.example.foodtogo.data.service.Mail;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityOrderDetailBinding;
import com.example.foodtogo.databinding.FragmentConvBinding;
import com.example.foodtogo.databinding.FragmentListChatBinding;
import com.example.foodtogo.ui.HomeFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

public class ChatMessageFragment extends MyFragment {
    FragmentConvBinding binding;
    Chat chat;
    List<Message> messages = new ArrayList();
    MessageRecycleViewAdapter RecycleViewAdapter;
    Mail service_mail;



    public ChatMessageFragment(){
        this.service_mail = new Mail();
    }

    public ChatMessageFragment(Chat chat){
        this.chat = chat;
        this.service_mail = new Mail();
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
        Mail service = this.service_mail;

        ImageButton button_send;
        EditText content;
        Button accept;
        RelativeLayout layout_gchat_chatbox;
        TextView nameUser;

        button_send = view.findViewById(R.id.button_send);
        content = view.findViewById(R.id.content);
        accept = view.findViewById(R.id.accept);
        layout_gchat_chatbox = view.findViewById(R.id.layout_gchat_chatbox);
        nameUser = view.findViewById(R.id.nameUser);


        Product product = Product.findById(Product.class,this.chat.product_id);
        long id_send = getService().user_authenticated.getId() == chat.send_to ? chat.send_by : chat.send_to;
        User user = User.findById(User.class,id_send);
        nameUser.setText(user.firstName + " " + user.lastName);

        if(getService().user_authenticated.getId().equals(product.user_id)){
            accept.setVisibility(View.VISIBLE);
            if(product.status.equals("PENDING")) {
                accept.setText("ANNULER");
                accept.setBackgroundColor(Color.parseColor("#983b45"));
            }else if(product.status.equals("DONE")){
                layout_gchat_chatbox.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
            }
        }else{
            accept.setVisibility(View.GONE);
            if(product.status.equals("DONE")){
                layout_gchat_chatbox.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
            }
        }



        button_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Message new_message = new Message(
                        content.getText().toString(),
                        getService().user_authenticated.getId(),
                        chat_id
                );
                new_message.save();
                content.setText("");
                messages = Message.find(Message.class, "chatid = ?", chat.getId().toString());
                RecycleViewAdapter = new MessageRecycleViewAdapter(getContext(), messages,getService());
                setup();
            }
        });

        accept.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(accept.getText().toString().equals("ANNULER")){
                    product.status = "CREATED";
                    product.code = "";
                    accept.setText("ACCEPTER");
                    accept.setBackgroundColor(Color.parseColor("#7CB342"));
                    product.save();
                }else {
                    product.status = "PENDING";
                    long id_buy = getService().user_authenticated.getId() == chat.send_to ? chat.send_by : chat.send_to;
                    String randomCode = UUID.randomUUID().toString().substring(0,10);
                    product.code = randomCode;
                    product.buy_by = id_buy;
                    product.save();
                    User send_to_creator = User.findById(User.class,product.user_id);
                    User send_to_buy = User.findById(User.class,id_buy);
                    try {
                        service.sendEmail(send_to_creator.email,randomCode.substring(0,5));
                        service.sendEmail(send_to_buy.email,randomCode.substring(5,10));
                    }
                    catch (MessagingException e){
                        e.printStackTrace();
                    }
                    accept.setText("ANNULER");
                    accept.setBackgroundColor(Color.parseColor("#983b45"));
                }

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
