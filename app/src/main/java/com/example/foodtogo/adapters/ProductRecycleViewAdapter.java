package com.example.foodtogo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Order;
import com.example.foodtogo.ui.product.OrderDetailFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ProductHolder> {
    Context context;
    ArrayList<Order> products;

    public ProductRecycleViewAdapter(Context context, ArrayList<Order> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_card,parent,false);
        final ProductHolder holder = new ProductHolder(view);

        holder.itemView.setOnClickListener(l -> {
            Order product = products.get(holder.getAdapterPosition() + 1);

            AppCompatActivity activity = (AppCompatActivity) l.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new OrderDetailFragment(product)).commit();
        });
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Order product = products.get(position);
        holder.setDetails(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        Button orderButton;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            productName = itemView.findViewById(R.id.productNameView);
            orderButton = itemView.findViewById(R.id.productReserverButton);
        }

        public void setDetails(Order product){
            productName.setText(product.getTitle());
            if (product.getImage() != null || !Objects.equals(product.getImage(), ""))
                productImage.setImageBitmap(decode64BitImage(product.getImage()));
        }

        private Bitmap decode64BitImage(String encodedString){
            byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }
}
