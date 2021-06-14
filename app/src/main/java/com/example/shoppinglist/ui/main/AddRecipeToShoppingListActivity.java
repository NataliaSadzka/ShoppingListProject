package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRecipeToShoppingListActivity extends Activity {

    private AutoCompleteTextView shoppingListAutoCompleteTextView;
    private ImageView backImageView;
    private Button addRecipeButton;

    private boolean existingShoppingList = false;
    private ShoppingList shoppingList;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_to_shopping_list);

        shoppingListAutoCompleteTextView = findViewById(R.id.shopping_list_auto_complete_text_view);
        backImageView = findViewById(R.id.image_back);
        addRecipeButton = findViewById(R.id.add_button);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        List<ShoppingList> shoppingLists = AppDatabase.getDbInstance(getApplicationContext()).shoppingListDao().getAllShoppingLists();

        ArrayAdapter<ShoppingList> shoppingListsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, shoppingLists);
        shoppingListAutoCompleteTextView.setThreshold(1);
        shoppingListAutoCompleteTextView.setAdapter(shoppingListsAdapter);

        shoppingListAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shoppingList = (ShoppingList) parent.getAdapter().getItem(position);
                existingShoppingList = true;
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existingShoppingList) {
                    addRecipeToShoppingList(recipe, shoppingList);
                } else {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setName(shoppingListAutoCompleteTextView.getText().toString());
                    shoppingList.setShoppingListId((int) AppDatabase.getDbInstance(v.getContext()).shoppingListDao().insertShoppingList(shoppingList)[0]);

                    addRecipeToShoppingList(recipe, shoppingList);
                }
            }
        });

    }

    private void addRecipeToShoppingList(Recipe recipe, ShoppingList shoppingList) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.shoppingListProductDao().insertShoppingListProduct();

        List<ShoppingListProduct> shoppingListProducts = db.shoppingListProductDao().findShoppingListProductsByShoppingListId(shoppingList.getShoppingListId());
        List<RecipeProduct> recipeProducts = db.recipeProductDao().findRecipeProductsByRecipeId(recipe.getRecipeId());

        Map<Integer, ShoppingListProduct> shoppingListProductMap = new HashMap<>();

        for (ShoppingListProduct shoppingListProduct : shoppingListProducts) {
            shoppingListProductMap.put(shoppingListProduct.getProduct().getProductId(), shoppingListProduct);
        }

        for (RecipeProduct recipeProduct : recipeProducts) {
            if (shoppingListProductMap.containsKey(recipeProduct.getProduct().getProductId())) {
                ShoppingListProduct shoppingListProduct = shoppingListProductMap.get(recipeProduct.getProduct().getProductId());
                shoppingListProduct.setQuantity(shoppingListProduct.getQuantity() + recipeProduct.getQuantity());
                shoppingListProductMap.put(shoppingListProduct.getProduct().getProductId(), shoppingListProduct);
            } else {
                ShoppingListProduct shoppingListProduct = new ShoppingListProduct();
                shoppingListProduct.setShoppingList(shoppingList);
                shoppingListProduct.setProduct(recipeProduct.getProduct());
                shoppingListProduct.setQuantity(recipeProduct.getQuantity());
                shoppingListProductMap.put(recipeProduct.getProduct().getProductId(), shoppingListProduct);
            }
        }

        ShoppingListProduct[] productsToSave = shoppingListProductMap.values().toArray(new ShoppingListProduct[shoppingListProductMap.values().size()]);
        db.shoppingListProductDao().insertShoppingListProduct(productsToSave);
        finish();
    }
}

