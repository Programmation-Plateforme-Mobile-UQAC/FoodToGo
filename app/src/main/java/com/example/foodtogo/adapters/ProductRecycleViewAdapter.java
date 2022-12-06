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
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.ui.order.OrderDetailFragment;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ProductHolder> {
    Context context;
    ArrayList<Product> products;
    Long userId;

    public ProductRecycleViewAdapter(Context context, ArrayList<Product> products, long userId){
        this.context = context;
        this.products = products;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.userId = userId;
        }
    }

    public ProductRecycleViewAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
        this.userId = null;
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
        holder.setDetails(product, userId);

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

        public void setDetails(Product product, Long userId){
            productName.setText(product.getTitle());

            if (product.getImage() != null || !Objects.equals(product.getImage(), ""))
                productImage.setImageBitmap(decode64BitImage(product.getImage()));

            Favorite productIsFavorite = null;

            try {
                if (userId != null){
                    productIsFavorite = Favorite.find(Favorite.class, "PRODUCTID = ?", product.getId().toString()).get(0);
                    favoriteButton.setBackgroundResource(R.drawable.isfavorite);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            Favorite finalProductIsFavorite = productIsFavorite;
            favoriteButton.setOnClickListener(l -> {
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
                           }
                       }
                   } catch (Exception e){
                       Toast.makeText(l.getContext(), "Erreur d'ajout de favoris", Toast.LENGTH_SHORT).show();
                       e.printStackTrace();
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
