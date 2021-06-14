package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingList;

public class AddNewShoppingListActivity extends Activity {

    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shopping_list);

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
                saveNewShoppingList(textViewName.getText().toString());
            }
        });
    }

    private void saveNewShoppingList(String name) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);

        db.shoppingListDao().insertShoppingList(shoppingList);
        finish();
    }
}
