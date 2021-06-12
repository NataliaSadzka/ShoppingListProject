package com.example.shoppinglist.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Product.class, Recipe.class, ShoppingList.class, RecipeProduct.class, ShoppingListProduct.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract RecipeDao recipeDao();
    public abstract RecipeProductDao recipeProductDao();
    public abstract RecipeWithProductsDao recipeWithProductsDAO();
    public abstract ShoppingListWithProductsDao shoppingListWithProductsDAO();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListProductDao shoppingListProductDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "BAZA")
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    INSTANCE.productDao().insertProduct(INSTANCE.products);
                                    INSTANCE.recipeDao().insertRecipe(INSTANCE.recipes);
                                    INSTANCE.shoppingListDao().insertShoppingList(INSTANCE.shoppingLists);
                                    INSTANCE.recipeProductDao().insertRecipeProduct(INSTANCE.chiliConCarneRecipe);
                                    INSTANCE.shoppingListProductDao().insertShoppingListProduct(INSTANCE.shoppingListProducts);
                                }
                            });
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

    Product[] products = new Product[] {
            new Product(1, "Mleko", "l"),
            new Product(2, "Cukier", "kg"),
            new Product(3, "Mąka", "kg"),
            new Product(4, "Mięso mielone wołowe", "kg"),
            new Product(5, "Pomidory w puszce", "szt"),
            new Product(6, "Fasola w puszce", "szt"),
            new Product(7, "Cebula", "kg"),
            new Product(8, "Czerwona papryka", "kg"),
            new Product(9, "Śmietana 18%", "l")
    };

    Recipe[] recipes = new Recipe[] {
            new Recipe(1, "Chili con carne", null),
            new Recipe(2, "Kurczak Tikka masala", null),
            new Recipe(3, "Spaghetti", null)
    };

    ShoppingList[] shoppingLists = new ShoppingList[] {
            new ShoppingList(1, "Lidl"),
            new ShoppingList(2, "Grill")
    };

    ShoppingListProduct[] shoppingListProducts = new ShoppingListProduct[] {
            new ShoppingListProduct(shoppingLists[0], products[0], 12, false),
            new ShoppingListProduct(shoppingLists[0], products[1], 4, false),
            new ShoppingListProduct(shoppingLists[0], products[2], 1, false)
    };

    RecipeProduct[] chiliConCarneRecipe = new RecipeProduct[] {
            new RecipeProduct(recipes[0], products[3], 0.4),
            new RecipeProduct(recipes[0], products[4], 1),
            new RecipeProduct(recipes[0], products[5], 1),
            new RecipeProduct(recipes[0], products[6], 0.1),
            new RecipeProduct(recipes[0], products[7], 0.1),
            new RecipeProduct(recipes[0], products[8], 1)
    };
}