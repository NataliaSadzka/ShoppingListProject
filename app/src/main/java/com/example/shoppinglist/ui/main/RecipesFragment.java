package com.example.shoppinglist.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Recipe;
import com.example.shoppinglist.database.RecipeProduct;
import com.example.shoppinglist.database.RecipeWithProducts;

import java.util.List;

public class RecipesFragment extends Fragment {

    private RecipeListAdapter recipeListAdapter;
    private List<Recipe> recipes;
    private RecyclerView recyclerView;
    private ImageView addNewRecipeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);

        return inflater.inflate(R.layout.recipes_activity, viewGroup, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        recipes = db.recipeDao().getAllRecipes();
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipes);
        recyclerView.setAdapter(recipeListAdapter);

        addNewRecipeImage = view.findViewById(R.id.add_new_image);

        addNewRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNewRecipe.class);
                startActivity(intent);
            }
        });
    }

    public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.MyViewHolder> {

        private Context context;
        private List<Recipe> recipeList;

        public RecipeListAdapter(Context context, List<Recipe> recipes) {
            this.context = context;
            this.recipeList = recipes;
        }

        public void setRecipeList(List<Recipe> recipeList) {
            this.recipeList = recipeList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecipeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_row, parent, false);

            return new RecipeListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeListAdapter.MyViewHolder holder, final int position) {
            holder.textViewName.setText(this.recipeList.get(position).getName());
            holder.imageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecipeWithProducts recipeWithProducts = AppDatabase.getDbInstance(v.getContext()).recipeWithProductsDao().findRecipeWithProductsByRecipeId(recipeList.get(position).getRecipeId());

                    if (recipeWithProducts.getRecipe() == null) {
                        Recipe recipe = AppDatabase.getDbInstance(v.getContext()).recipeDao().findRecipeByRecipeId(recipeList.get(position).getRecipeId());
                        recipeWithProducts.setRecipe(recipe);
                    }

                    List<RecipeProduct> recipeProducts = AppDatabase.getDbInstance(v.getContext()).recipeProductDao().findRecipeProductsByRecipeId(recipeWithProducts.getRecipe().getRecipeId());
                    recipeWithProducts.setProducts(recipeProducts);

                    v.getContext().startActivity(
                            new Intent(v.getContext(), DetailRecipeListActivity.class)
                                    .putExtra("editMode", false)
                                    .putExtra("recipeList", recipeWithProducts));
                }
            });

            holder.imageShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(
                            new Intent(v.getContext(), AddRecipeToShoppingListActivity.class)
                                    .putExtra("recipe", recipeList.get(position)));
                }
            });

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Czy na pewno chcesz usunąć?");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Recipe recipe = recipes.get(position);
                            recipes.remove(recipe);
                            AppDatabase db = AppDatabase.getDbInstance(v.getContext());
                            db.recipeDao().delete(recipe);

                            notifyDataSetChanged();
                        }
                    });

                    builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.recipeList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textViewName;
            ImageView imageDetail;
            ImageView imageShopping;
            ImageView imageDelete;

            public MyViewHolder(View view) {
                super(view);
                imageDetail = view.findViewById(R.id.image_detail);
                textViewName = view.findViewById(R.id.textViewName);
                imageDelete = view.findViewById(R.id.image_delete);
                imageShopping = view.findViewById(R.id.image_shopping);
            }
        }
    }
}
