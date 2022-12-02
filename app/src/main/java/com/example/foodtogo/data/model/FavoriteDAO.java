package com.example.foodtogo.data.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM favorite")
    List<Favorite> getAll();

    @Query("SELECT * FROM favorite WHERE id = :favoriteId")
    Favorite findById(long favoriteId);

    @Query("SELECT * FROM product WHERE productRoomId = :product_id LIMIT 1")
    Product findProductByName(long product_id);

    @Insert
    void insertAll(Favorite... favorites);

    @Insert
    void insertOne(Favorite favorite);

    @Delete
    void delete(Favorite favorite);
}
