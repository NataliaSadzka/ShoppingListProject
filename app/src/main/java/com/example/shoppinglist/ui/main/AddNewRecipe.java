package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Recipe;
import com.example.shoppinglist.database.ShoppingList;

public class AddNewRecipe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        final EditText textViewName = findViewById(R.id.textViewName);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewRecipe(textViewName.getText().toString());

            }
        });
    }

    private void saveNewRecipe(String name) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        Recipe recipe = new Recipe();
        recipe.setName(name);

        db.recipeDao().insertRecipe(recipe);
        finish();
    }
}
