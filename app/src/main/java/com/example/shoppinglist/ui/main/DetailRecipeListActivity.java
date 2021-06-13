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
import com.example.shoppinglist.database.*;

import java.util.List;

public class DetailRecipeListActivity extends Activity {

    private DetailRecipeListAdapter detailRecipeListAdapter;
    private RecipeWithProducts recipe;
    private List<RecipeProduct> products;

    private TextView recipeTitleTextView;
    private Button saveButton;
    private Button cancelButton;
    private ImageView editImage;
    private ImageView addNewImage;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe_list_activity);

        recipe = (RecipeWithProducts) getIntent().getSerializableExtra("recipeList");

        recipeTitleTextView = findViewById(R.id.title);

        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        editImage = findViewById(R.id.image_edit);
        addNewImage = findViewById(R.id.add_new_image);
        backImage = findViewById(R.id.image_back);

        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        addNewImage.setVisibility(View.INVISIBLE);

        final RecyclerView recyclerView  = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recipeTitleTextView.setText(recipe.getRecipes().getName());

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        products = recipe.getProducts();
        detailRecipeListAdapter = new DetailRecipeListAdapter(getApplicationContext(), products);
        recyclerView.setAdapter(detailRecipeListAdapter);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRecipeListActivity.this, RecipeListAdapter.class);
                finish();
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
