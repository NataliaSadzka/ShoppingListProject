package com.example.shoppinglist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecipesFragment extends Fragment {

    private RecipeListAdapter recipeListAdapter;
    private List<Recipe> recipes;
    private ImageView addNewRecipeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);

        return inflater.inflate(R.layout.recipes_activity, viewGroup, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        recipes = db.recipeDao().getAllRecipes();
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipes);
        recyclerView.setAdapter(recipeListAdapter);

        addNewRecipeImage = view.findViewById(R.id.addNewImage);

        addNewRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
