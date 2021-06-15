package com.example.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product order by name")
    List<Product> getAllProducts();

    @Insert
    long[] insertProduct(Product... products);

    @Delete
    void delete(Product product);
}
