package com.example.shoppinglist.database;

import androidx.room.Entity;

@Entity(primaryKeys = {"recipeId", "productId"})
public class RecipeProduct {

    private long recipeId;
    private long productId;
    private double quantity;

    public RecipeProduct() {

    }

    public RecipeProduct(long recipeId, long productId, double quantity) {
        this.recipeId = recipeId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
