package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingList;

public class AddNewShoppingListActivity extends Activity {

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);

        return inflater.inflate(R.layout.activity_add_new_shopping_list, viewGroup, false);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shopping_list);

        final EditText textViewName = findViewById(R.id.textViewName);
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
