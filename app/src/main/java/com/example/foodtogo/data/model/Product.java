package com.example.foodtogo.data.model;

import com.orm.SugarRecord;

import java.util.UUID;

public class Product extends SugarRecord<Product> {
    private UUID id;
    private UUID user_id;
    private UUID category_id;
    private String title;
    private String summary;
    private String image;

    public Product(UUID id, UUID user_id, UUID category_id,
                    String title, String summary, String image) {

        if (Category.findById(Category.class, category_id.node()) != null){
            this.category_id = category_id;
        }

        if (User.findById(User.class, user_id.node()) != null){
            this.user_id = user_id;
        }

        this.title = title;
        this.summary = summary;
        this.image = image;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
}