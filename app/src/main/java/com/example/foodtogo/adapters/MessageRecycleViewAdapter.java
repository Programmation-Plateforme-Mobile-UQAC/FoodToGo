package com.example.foodtogo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Message;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.ui.message.ChatMessageFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageRecycleViewAdapter extends RecyclerView.Adapter<MessageRecycleViewAdapter.MessageHolder> {
    Context context;
    List<Message> messages;
    Authenticated service;
    boolean contributor = true;

    public MessageRecycleViewAdapter(Context context, List<Message> messages, Authenticated service){
        this.context = context;
        this.messages = messages;
        this.service = service;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //R.layout.fragment_my_message_in_chat
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_one_msg_in_chat,parent,false);
        return new MessageHolder(view);
    }

    public void setItems( List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = messages.get(position);
        holder.setDetails(message,this.service.user_authenticated);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {
        TextView content,date,user;

        public MessageHolder(@NonNull View itemView) {

            super(itemView);
            content = itemView.findViewById(R.id.content_msg);
            date = itemView.findViewById(R.id.date);
            user = itemView.findViewById(R.id.user);
        }

        public void setDetails(Message message,User current_user){

            User send = User.findById(User.class,message.send_by);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
            Date resultdate = new Date(message.created_at);
            content.setText(message.message);
            date.setText(sdf.format(resultdate));
            if(current_user.getId() == message.send_by){
                user.setText("Moi");
                content.setBackgroundColor(Color.parseColor("#7cb442"));

            }else{
                user.setText(send.firstName);
            }
        }

    }
}
