package com.example.foodtogo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.ui.message.ChatMessageFragment;
import com.example.foodtogo.ui.order.OrderDetailFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatRecycleViewAdapter extends RecyclerView.Adapter<ChatRecycleViewAdapter.ChatHolder> {
    Context context;
    List<Chat> chats;
    Long userId;
    boolean get;

    public ChatRecycleViewAdapter(Context context, List<Chat> chats, long userId,boolean get){
        this.context = context;
        this.chats = chats;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.userId = userId;
        }
        this.get = get;
    }

    public ChatRecycleViewAdapter(Context context, List<Chat> chats,boolean get){
        this.context = context;
        this.chats = chats;
        this.userId = null;
        this.get = get;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_send_message_to_chat,parent,false);
        return new ChatHolder(view);
    }

    public void setItems( List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.setDetails(chat, userId,this.get);

        holder.itemView.setOnClickListener(l -> {
            AppCompatActivity activity = (AppCompatActivity) l.getContext();
            ChatMessageFragment frag = new ChatMessageFragment(chat);
            //orderDetailFragment.setService(holder.ite);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        TextView productName,send_by,date;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            date = itemView.findViewById(R.id.date);
        }

        public void setDetails(Chat chat, Long userId,boolean gift){
            User send = User.findById(User.class,chat.send_by);
            User send_to = User.findById(User.class,chat.send_to);
            Product produit = Product.findById(Product.class,chat.product_id);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(chat.created_at);
            String text = "";

            if(!gift){
                text = send.lastName + " " + send.firstName +" vous contacte pour le produit " + produit.title;
            }else{
                text = "Vous avez contacter " +send_to.lastName + " " + send_to.firstName +" pour le produit suivant " + produit.title;
            }

            productName.setText(text);
            date.setText(sdf.format(resultdate));
        }

    }
}
