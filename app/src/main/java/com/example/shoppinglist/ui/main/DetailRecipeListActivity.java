package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.*;

import java.util.ArrayList;
import java.util.List;

public class DetailRecipeListActivity extends Activity {

    private DetailRecipeListAdapter detailRecipeListAdapter;
    private RecyclerView recyclerView;
    private RecipeWithProducts recipe;
    private List<RecipeProduct> products;
    private List<RecipeProduct> productsToDelete = new ArrayList<>();

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

        recyclerView  = findViewById(R.id.recycler_view);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(false);

                AppDatabase.getDbInstance(v.getContext()).recipeProductDao().insertRecipeProduct(products.stream().toArray(RecipeProduct[]::new));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(false);
                recipe = AppDatabase.getDbInstance(v.getContext()).recipeWithProductsDao().findRecipeWithProductsByRecipeId(recipe.getRecipes().getRecipeId());
                products = recipe.getProducts();
                detailRecipeListAdapter = new DetailRecipeListAdapter(getApplicationContext(), products);
                recyclerView.setAdapter(detailRecipeListAdapter);
            }
        });

        addNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(
                        new Intent(v.getContext(), AddProductToRecipeActivity.class)
                                .putExtra("recipeList", recipe));
            }
        });

        switchMode(getIntent().getBooleanExtra("editMode", false));
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

        detailRecipeListAdapter = new DetailRecipeListAdapter(getApplicationContext(), products);
        detailRecipeListAdapter.switchMode(editMode);
        recyclerView.setAdapter(detailRecipeListAdapter);
    }

    public class DetailRecipeListAdapter extends RecyclerView.Adapter<DetailRecipeListAdapter.MyViewHolder> {

        private Context context;
        private List<RecipeProduct> recipeProducts;
        private boolean editMode = false;

        public DetailRecipeListAdapter(Context context, List<RecipeProduct> recipeProducts) {
            this.context = context;
            this.recipeProducts = recipeProducts;
        }

        public void setProducts(List<RecipeProduct> recipeProducts) {
            this.recipeProducts = recipeProducts;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public DetailRecipeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recipe_list_row, parent, false);

            return new DetailRecipeListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailRecipeListAdapter.MyViewHolder holder, int position) {
            final RecipeProduct product = recipeProducts.get(position);
            holder.textViewName.setText(product.getProduct().getName() + " " + product.getQuantity() + " " + product.getProduct().getQuantityUnit());

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productsToDelete.add(product);
                    products.remove(product);
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return recipeProducts.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageEdit;
            ImageView addNew;
            TextView textViewName;
            ImageView imageDelete;

            public MyViewHolder(View view) {
                super(view);
                textViewName = view.findViewById(R.id.text_view_name);
                imageEdit = view.findViewById(R.id.image_delete);
                addNew = view.findViewById(R.id.add_new_image);
                imageDelete = view.findViewById(R.id.image_delete);

                if (editMode) {
                    imageDelete.setVisibility(View.VISIBLE);
                } else {
                    imageDelete.setVisibility(View.INVISIBLE);
                }
            }
        }

        public void switchMode(boolean editMode) {
            this.editMode = editMode;
            notifyDataSetChanged();
        }
    }

}
