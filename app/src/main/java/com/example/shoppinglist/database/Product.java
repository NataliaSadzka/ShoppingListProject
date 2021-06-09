package com.example.shoppinglist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "quantity-unit")
    private String quantityUnit;

    public Product() {

    }

    public Product(int productId, String name, String quantityUnit) {
        this.productId = productId;
        this.name = name;
        this.quantityUnit = quantityUnit;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }
}

