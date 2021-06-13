package com.example.shoppinglist.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class ShoppingListWithProducts implements Serializable {
    @Embedded
    private ShoppingList shoppingList;
    @Relation(
            parentColumn = "shoppingListId",
            entityColumn = "productId",
            associateBy = @Junction(ShoppingListProduct.class)
    )
    private List<ShoppingListProduct> products;

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<ShoppingListProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ShoppingListProduct> products) {
        this.products = products;
    }
}
