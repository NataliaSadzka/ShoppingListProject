package com.example.shoppinglist.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class RecipeWithProducts implements Serializable {
    @Embedded
    public Recipe recipes;
    @Relation(
            parentColumn = "recipeId",
            entityColumn = "productId",
            associateBy = @Junction(RecipeProduct.class)
    )
    public List<RecipeProduct> products;

    public Recipe getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipe recipes) {
        this.recipes = recipes;
    }

    public List<RecipeProduct> getProducts() {
        return products;
    }

    public void setProducts(List<RecipeProduct> products) {
        this.products = products;
    }
}
