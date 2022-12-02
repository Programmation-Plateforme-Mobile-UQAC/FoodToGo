package com.example.foodtogo.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.CategoryDAO;
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.FavoriteDAO;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.ProductDAO;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.model.UserDAO;

@Database(entities = {Favorite.class, Product.class, Category.class, User.class}, version = 2, exportSchema = true)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract FavoriteDAO favoriteDAO();
    public abstract ProductDAO productDAO();
    public abstract UserDAO userDAO();
    public abstract CategoryDAO categoryDAO();
}
