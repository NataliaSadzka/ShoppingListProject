package com.example.shoppinglist.database;

import androidx.room.Entity;

@Entity(primaryKeys = {"shoppingListId", "productId"})
public class ShoppingListProduct {

    private long shoppingListId;
    private long productId;
    private double quantity;
    private boolean added;

    public ShoppingListProduct() {

    }

    public ShoppingListProduct(long shoppingListId, long productId, double quantity, boolean added) {
        this.shoppingListId = shoppingListId;
        this.productId = productId;
        this.quantity = quantity;
        this.added = added;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public double getQuantity() {
        return quantity;
    }

    public boolean isAdded() {
        return added;
    }
}
