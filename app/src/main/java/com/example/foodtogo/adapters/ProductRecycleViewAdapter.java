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

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Chat;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.ui.ChatFragment;
import com.example.foodtogo.ui.authenticated.LoginFragment;
import com.example.foodtogo.ui.order.OrderDetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ProductHolder> {
    Context context;
    ArrayList<Product> products;
    Long userId;
    Authenticated service;
    public ProductRecycleViewAdapter(Context context, ArrayList<Product> products, long userId,Authenticated service){
        this.context = context;
        this.products = products;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.userId = userId;
        }
        this.service = service;
    }

    public ProductRecycleViewAdapter(Context context, ArrayList<Product> products,Authenticated service){
        this.context = context;
        this.products = products;
        this.userId = service.user_authenticated != null ? service.user_authenticated.getId() : null;
        this.service = service;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_card_new,parent,false);
        return new ProductHolder(view);
    }

    public void setItems( ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.setDetails(product, userId,service);

        holder.itemView.setOnClickListener(l -> {
            AppCompatActivity activity = (AppCompatActivity) l.getContext();
            OrderDetailFragment orderDetailFragment = new OrderDetailFragment(product);
            //orderDetailFragment.setService(holder.ite);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, orderDetailFragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        Button orderButton;
        ImageButton favoriteButton;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            productName = itemView.findViewById(R.id.productNameView);
            orderButton = itemView.findViewById(R.id.productReserverButton);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        public void setDetails(Product product, Long userId,Authenticated service){
            productName.setText(product.getTitle());

            if (product.getImage() != null || !Objects.equals(product.getImage(), ""))
                productImage.setImageBitmap(decode64BitImage(product.getImage()));

            Favorite productIsFavorite = null;

            try {
                if (userId != null){
                    productIsFavorite = Favorite.find(Favorite.class, "PRODUCTID = ?", product.getId().toString()).get(0);
                    favoriteButton.setImageResource(R.drawable.favorite_active);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            Favorite finalProductIsFavorite = productIsFavorite;
            favoriteButton.setOnClickListener(l -> {
                if(service.user_authenticated == null){
                    LoginFragment frag = new LoginFragment();
                    frag.setService(service);
                    AppCompatActivity activity = (AppCompatActivity) l.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
                }
                if (finalProductIsFavorite != null){
                    Toast.makeText(l.getContext(), "Existe deja dans la liste des favoris", Toast.LENGTH_SHORT).show();
                } else {
                   try{
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                           if(userId != null){
                               Favorite favorite = new Favorite(product.getId(), userId);
                               favorite.save();

                               favoriteButton.setColorFilter(Color.RED);
                               Toast.makeText(l.getContext(), "Ajouté à la liste des favoris", Toast.LENGTH_SHORT).show();
                           }else{

                           }
                       }
                   } catch (Exception e){
                       Toast.makeText(l.getContext(), "Erreur d'ajout de favoris", Toast.LENGTH_SHORT).show();
                       e.printStackTrace();
                   }
                }
            });

            orderButton.setOnClickListener(l -> {
                if(service.user_authenticated == null){
                    LoginFragment frag = new LoginFragment();
                    frag.setService(service);
                    AppCompatActivity activity = (AppCompatActivity) l.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
                }else{
                    String send_by = service.user_authenticated.getId().toString();
                    String send_to = Long.toString(product.getUser_id());
                    List<Chat> chat_exist = Chat.find(Chat.class, "sendto = ? & sendby = ?", send_to,send_by);
                    if(send_by.equals(send_to)){
                        Toast.makeText(l.getContext(), "Vous etre le donneur vous pouvez pas reserver votre propre annonce", Toast.LENGTH_SHORT).show();

                    }
                    else if(chat_exist.size() > 0){
                        Toast.makeText(l.getContext(), "Vous avez déja démarrer une conversation avec le donneur", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Chat new_chat = new Chat(
                                service.user_authenticated.getId(),
                                product.getUser_id(),
                                product.getId()
                        );
                        new_chat.save();
                        Toast.makeText(l.getContext(), "Vous aller démarrer une conversation avec le donneur", Toast.LENGTH_SHORT).show();
                        AppCompatActivity activity = (AppCompatActivity) l.getContext();
                        ChatFragment frag = new ChatFragment();
                        frag.setService(service);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, frag).commit();
                    }
                }
            });

        }

        private Bitmap decode64BitImage(String encodedString){
            byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }
}
