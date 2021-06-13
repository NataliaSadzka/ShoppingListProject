package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    List<Recipe> getAllRecipes();

    @Insert
    void insertRecipe(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM RECIPE where recipeId = :recipeId")
    Recipe findRecipeByRecipeId(int recipeId);
}
