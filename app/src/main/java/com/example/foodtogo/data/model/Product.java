package com.example.foodtogo.data.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class Product {
    private UUID id;
    private UUID user_id;
    private UUID category_id;
    private String title;
    private String summary;
    private String image;
    private Date created_at;
    private Date updated_at;

    public Product(@NotNull UUID id, @NotNull UUID user_id, @NotNull UUID category_id,
                   @NotNull String title, @NotNull String summary, @NotNull String image,
                   @NotNull Date created_at, @NotNull Date updated_at) {
        this.category_id = category_id;
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.summary = summary;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
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

    public UUID getId() {
        return id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
}