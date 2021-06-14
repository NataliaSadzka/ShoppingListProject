package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Recipe;

public class AddNewRecipe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        final EditText textViewName = findViewById(R.id.textViewName);
        ImageView backImage = findViewById(R.id.image_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("selectedTab", "recipes");
        startActivity(intent);
    }
}
