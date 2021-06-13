package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ShoppingListWithProductsDao {
    @Transaction
    @Query("SELECT * FROM ShoppingList")
    List<ShoppingListWithProducts> getShoppingListWithProducts();

    @Transaction
    @Query("SELECt * FROM ShoppingList sl left join ShoppingListProduct slp on sl.shoppingListId = slp.shoppingListId where sl.shoppingListId = :shoppingListId")
    ShoppingListWithProducts findShoppingListWithProductsByShoppingListId(int shoppingListId);
}
