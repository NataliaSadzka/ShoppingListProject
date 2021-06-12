package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingList;
import com.example.shoppinglist.database.ShoppingListProduct;

import java.util.List;

public class DetailShoppingListActivity extends Activity {

    private DetailShoppingListAdapter detailShoppingListAdapter;
    private List<ShoppingListProduct> products;

    private TextView shoppingListTitleTextView;
    private Button saveButton;
    private Button cancelButton;
    private ImageView editImage;
    private ImageView addNewImage;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_shopping_list_activity);

        ShoppingList shoppingList = (ShoppingList) getIntent().getSerializableExtra("shoppingList");

        shoppingListTitleTextView = findViewById(R.id.shopping_list_title);

        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        editImage = findViewById(R.id.image_edit);
        addNewImage = findViewById(R.id.add_new_image);
        backImage = findViewById(R.id.image_back);

        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        addNewImage.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView  = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        shoppingListTitleTextView.setText(shoppingList.getName());

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        products = db.shoppingListProductDao().getAllShoppingListProducts();
        detailShoppingListAdapter = new DetailShoppingListAdapter(getApplicationContext(), products);
        recyclerView.setAdapter(detailShoppingListAdapter);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailShoppingListActivity.this, ShoppingListFragment.class);
                startActivity(intent);
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(true);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(false);
            }
        });
    }

    public void switchMode(boolean editMode) {
        if (editMode) {
            editImage.setVisibility(View.INVISIBLE);
            backImage.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            addNewImage.setVisibility(View.VISIBLE);
        } else {
            editImage.setVisibility(View.VISIBLE);
            backImage.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            addNewImage.setVisibility(View.INVISIBLE);
        }
    }
}