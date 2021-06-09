package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * FROM ShoppingList")
    List<ShoppingList> getAllShoppingLists();

    @Insert
    void insertShoppingList(ShoppingList... shoppingLists);

    @Delete
    void delete(ShoppingList shoppingList);
}
