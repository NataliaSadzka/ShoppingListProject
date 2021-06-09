package com.example.shoppinglist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int shoppingListId;

    @ColumnInfo(name = "name")
    private String name;

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
}
