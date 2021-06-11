package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListProductDao {
    @Query("SELECT * FROM ShoppingListProduct")
    List<ShoppingListProduct> getAllShoppingListProducts();

    @Insert
    void insertShoppingListProduct(ShoppingListProduct... shoppingListProducts);

    @Delete
    void delete(ShoppingListProduct shoppingListProduct);
}
