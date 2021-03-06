package com.example.shoppinglist.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;

    @ColumnInfo(name = "title")
    private String name;

    @ColumnInfo(name = "photo")
    private byte[] photo;

    public Recipe() {

    }

    public Recipe(int recipeId, String name, byte[] photo) {
        this.recipeId = recipeId;
        this.name = name;
        this.photo = photo;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return name;
    }
}
