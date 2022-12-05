package com.example.foodtogo.data.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE id = :user_id")
    Category findById(long user_id);

    @Query("SELECT * FROM category WHERE name = :name LIMIT 1")
    Category findCategoryByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategories(List<Category> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Category category);

    @Delete
    void delete(Category category);
}
