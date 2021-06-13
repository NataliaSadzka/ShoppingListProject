package com.example.shoppinglist.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = ShoppingList.class,
                parentColumns = {"shoppingListId"},
                childColumns = {"shoppingListId"},
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Product.class,
                parentColumns = {"productId"},
                childColumns = {"productId"}
        )
})
public class ShoppingListProduct implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private ShoppingList shoppingList;

    @Embedded
    private Product product;

    private double quantity;
    private boolean added;

    public ShoppingListProduct() {

    }

    public ShoppingListProduct(ShoppingList shoppingList, Product product, double quantity, boolean added) {
        this.shoppingList = shoppingList;
        this.product = product;
        this.quantity = quantity;
        this.added = added;
    }

    public int getId() {
        return id;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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