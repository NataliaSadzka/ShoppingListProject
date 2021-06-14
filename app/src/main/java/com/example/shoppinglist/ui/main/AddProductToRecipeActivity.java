package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.*;

import java.util.Arrays;
import java.util.List;

public class AddProductToRecipeActivity extends Activity {

    private AutoCompleteTextView productAutoCompleteTextView;
    private EditText quantityEditText;
    private Spinner quantityUnitSpinner;
    private ImageView backImageView;
    private Button addProductButton;

    boolean existingProduct = false;
    Product product;
    RecipeWithProducts recipeWithProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_recipe);

        productAutoCompleteTextView = findViewById(R.id.product_auto_complete_text_view);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        quantityUnitSpinner = findViewById(R.id.quantity_unit_spinner);
        backImageView = findViewById(R.id.image_back);
        addProductButton = findViewById(R.id.add_product_to_recipe_button);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recipeWithProducts = (RecipeWithProducts) getIntent().getSerializableExtra("recipeList");

        List<Product> products = AppDatabase.getDbInstance(getApplicationContext()).productDao().getAllProducts();

        ArrayAdapter<Product> productAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_dropdown_item_1line, products);
        productAutoCompleteTextView.setThreshold(1);
        productAutoCompleteTextView.setAdapter(productAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityUnitSpinner.setAdapter(adapter);

        productAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product = (Product) parent.getAdapter().getItem(position);
                quantityUnitSpinner.setEnabled(false);
                quantityUnitSpinner.setClickable(false);
                int quantityUnitPosition = Arrays.asList(adapter.getAutofillOptions()).indexOf(product.getQuantityUnit());
                quantityUnitSpinner.setSelection(quantityUnitPosition);
                existingProduct = true;
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existingProduct) {
                    addProductToRecipe(product);
                } else {
                    Product product = new Product();
                    product.setName(productAutoCompleteTextView.getText().toString());
                    product.setQuantityUnit(quantityUnitSpinner.getSelectedItem().toString());
                    product.setProductId((int) AppDatabase.getDbInstance(v.getContext()).productDao().insertProduct(product)[0]);

                    addProductToRecipe(product);
                }
            }
        });
    }

    private void addProductToRecipe(Product product) {
        RecipeProduct recipeProduct = new RecipeProduct();
        recipeProduct.setProduct(product);
        recipeProduct.setRecipe(recipeWithProducts.getRecipes());
        recipeProduct.setQuantity(Double.parseDouble(quantityEditText.getText().toString()));
        recipeProduct.setAdded(false);

        recipeWithProducts.getProducts().add(recipeProduct);
        Intent intent = new Intent(getApplicationContext(), DetailRecipeListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("editMode", true);
        intent.putExtra("recipeList", recipeWithProducts);
        startActivity(intent);
        finish();
    }
}
