package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingListProduct;
import com.example.shoppinglist.database.ShoppingListWithProducts;
import com.example.shoppinglist.ui.main.AddProductToShoppingListActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailShoppingListActivity extends Activity {

    private DetailShoppingListAdapter detailShoppingListAdapter;
    private RecyclerView recyclerView;
    private ShoppingListWithProducts shoppingList;
    private List<ShoppingListProduct> products;
    private List<ShoppingListProduct> productsToDelete = new ArrayList<>();

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

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        shoppingList = (ShoppingListWithProducts) getIntent().getSerializableExtra("shoppingList");

        shoppingListTitleTextView = findViewById(R.id.shopping_list_title);

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

        shoppingListTitleTextView.setText(shoppingList.getShoppingList().getName());

        products = shoppingList.getProducts();
        detailShoppingListAdapter = new DetailShoppingListAdapter(getApplicationContext(), products);
        recyclerView.setAdapter(detailShoppingListAdapter);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                productsToDelete.forEach(product -> AppDatabase.getDbInstance(v.getContext()).shoppingListProductDao().delete(product));
                productsToDelete.clear();

                AppDatabase.getDbInstance(v.getContext()).shoppingListProductDao().insertShoppingListProduct(products.stream().toArray(ShoppingListProduct[]::new));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(false);
                products = AppDatabase.getDbInstance(v.getContext()).shoppingListProductDao().findShoppingListProductsByShoppingListId(shoppingList.getShoppingList().getShoppingListId());
                detailShoppingListAdapter.setProducts(products);
                recyclerView.setAdapter(detailShoppingListAdapter);
            }
        });

        addNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(
                        new Intent(v.getContext(), AddProductToShoppingListActivity.class)
                                .putExtra("shoppingList", shoppingList));
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

        //detailShoppingListAdapter = new DetailShoppingListAdapter(getApplicationContext(), products);
        detailShoppingListAdapter.switchMode(editMode);
        recyclerView.setAdapter(detailShoppingListAdapter);
    }

    public class DetailShoppingListAdapter extends RecyclerView.Adapter<DetailShoppingListAdapter.MyViewHolder> {

        private Context context;
        private List<ShoppingListProduct> products;
        private boolean editMode = false;

        public DetailShoppingListAdapter(Context context, List<ShoppingListProduct> products) {
            this.context = context;
            this.products = products;
        }

        public void setProducts(List<ShoppingListProduct> products) {
            this.products = products;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public DetailShoppingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_shopping_list_row, parent, false);

            return new DetailShoppingListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final DetailShoppingListAdapter.MyViewHolder holder, int position) {
            final ShoppingListProduct product = products.get(position);
            holder.checkBox.setChecked(product.isAdded());
            holder.checkBox.setText(product.getProduct().getName() + " " + product.getQuantity() + " " + product.getProduct().getQuantityUnit());

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    product.setAdded(isChecked);
                    setPaintFlags(holder.checkBox, isChecked);

                    AppDatabase.getDbInstance(context).shoppingListProductDao().insertShoppingListProduct(product);
                }
            });

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productsToDelete.add(product);
                    products.remove(product);
                    notifyDataSetChanged();
                }
            });

            setPaintFlags(holder.checkBox, product.isAdded());
        }

        private void setPaintFlags(CheckBox checkBox, boolean isChecked) {
            if (isChecked) {
                checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                checkBox.setPaintFlags(0);
            }
        }


        @Override
        public int getItemCount() {
            return this.products.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageDelete;
            ImageView addNew;
            CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                checkBox = view.findViewById(R.id.text_view_name);
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