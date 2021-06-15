package com.example.shoppinglist.database;

import androidx.room.*;

import java.util.List;

@Dao
public interface RecipeProductDao {
    @Query("SELECT * FROM RecipeProduct")
    List<RecipeProduct> getAllRecipeProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipeProduct(RecipeProduct... recipeProducts);

    @Delete
    void delete(RecipeProduct recipeProduct);

    @Query("SELECT * FROM RECIPEPRODUCT WHERE recipeId = :recipeId")
    List<RecipeProduct> findRecipeProductsByRecipeId(int recipeId);
}
