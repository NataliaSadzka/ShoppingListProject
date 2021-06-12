package com.example.shoppinglist.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Recipe.class,
                parentColumns = {"recipeId"},
                childColumns = {"recipeId"}
        ),
        @ForeignKey(
                entity = Product.class,
                parentColumns = {"productId"},
                childColumns = {"productId"}
        )
})
public class RecipeProduct {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Recipe recipe;

    @Embedded
    private Product product;

    private double quantity;

    public RecipeProduct() {

    }

    public RecipeProduct(Recipe recipe, Product product, double quantity) {
        this.recipe = recipe;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
