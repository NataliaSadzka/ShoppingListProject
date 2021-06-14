package com.example.shoppinglist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ShoppingList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int shoppingListId;

    @ColumnInfo(name = "title")
    private String name;

    public ShoppingList(int shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public ShoppingList() {

    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
