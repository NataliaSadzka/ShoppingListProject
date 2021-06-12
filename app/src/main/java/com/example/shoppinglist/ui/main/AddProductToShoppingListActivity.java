package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Product;
import com.example.shoppinglist.database.ShoppingList;
import com.example.shoppinglist.database.ShoppingListProduct;

import java.util.List;

public class AddProductToShoppingListActivity extends Activity {

    private AutoCompleteTextView productAutoCompleteTextView;
    private EditText quantityEditText;
    private Spinner quantityUnitSpinner;
    private ImageView backImageView;
    private Button addProductButton;

    boolean existingProduct = false;
    Product product;
    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_shopping_list);

        productAutoCompleteTextView = findViewById(R.id.product_auto_complete_text_view);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        quantityUnitSpinner = findViewById(R.id.quantity_unit_spinner);
        backImageView = findViewById(R.id.image_back);
        addProductButton = findViewById(R.id.add_product_to_shopping_list_button);

        shoppingList = (ShoppingList) getIntent().getSerializableExtra("shoppingList");

        List<Product> products = AppDatabase.getDbInstance(getApplicationContext()).productDao().getAllProducts();

        ArrayAdapter<Product> productsAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_dropdown_item_1line, products);
        productAutoCompleteTextView.setThreshold(1);
        productAutoCompleteTextView.setAdapter(productsAdapter);

        productAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product = (Product) parent.getAdapter().getItem(position);
                existingProduct = true;
                Log.i("Item clicked", "Item selected, id: " + id + ", position: " + position);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityUnitSpinner.setAdapter(adapter);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existingProduct) {
                    ShoppingListProduct shoppingListProduct = new ShoppingListProduct();
                    shoppingListProduct.setProduct(product);
                    shoppingListProduct.setShoppingList(shoppingList);
                    shoppingListProduct.setQuantity(Double.parseDouble(quantityEditText.getText().toString()));
                    shoppingListProduct.setAdded(false);

                    AppDatabase.getDbInstance(getApplicationContext()).shoppingListProductDao().insertShoppingListProduct(shoppingListProduct);
                    finish();
                } else {

                }
            }
        });
    }
}
