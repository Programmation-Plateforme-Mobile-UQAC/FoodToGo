package com.example.foodtogo.data.model;

import com.orm.SugarRecord;

import java.util.UUID;

public class Category extends SugarRecord<Category> {
    private UUID id;
    private String username;
    private String summary;

    public Category(UUID id, String username, String summary){
        this.id = id;
        this.username = username;
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public String getUsername() {
        return username;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}