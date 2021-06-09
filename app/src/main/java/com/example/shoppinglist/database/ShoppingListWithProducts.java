package com.example.shoppinglist.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ShoppingListWithProducts {
    @Embedded public ShoppingList shoppingList;
    @Relation(
            parentColumn = "shoppingListId",
            entityColumn = "productId",
            associateBy = @Junction(ShoppingListProduct.class)
    )
    public List<Product> products;
}
