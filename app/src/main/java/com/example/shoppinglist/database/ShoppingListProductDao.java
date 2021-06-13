package com.example.shoppinglist.database;

import androidx.room.*;

import java.util.List;

@Dao
public interface ShoppingListProductDao {
    @Query("SELECT * FROM ShoppingListProduct")
    List<ShoppingListProduct> getAllShoppingListProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShoppingListProduct(ShoppingListProduct... shoppingListProducts);

    @Delete
    void delete(ShoppingListProduct shoppingListProduct);

    @Query("SELECT * FROM ShoppingListProduct WHERE shoppingListId = :shoppingListId")
    ShoppingListProduct findShoppingListByShoppingListId(int shoppingListId);
}
