package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface RecipeWithProductsDao {
    @Transaction
    @Query("SELECT * FROM Recipe")
    List<RecipeWithProducts> getRecipeWithProducts();

}
