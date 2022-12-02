package com.example.foodtogo.data.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :product_id")
    Product findById(long product_id);

    @Query("SELECT * FROM product WHERE title = :product_name LIMIT 1")
    Product findProductByName(String product_name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllProduct(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Product product);

    @Delete
    void delete(Product product);
}
