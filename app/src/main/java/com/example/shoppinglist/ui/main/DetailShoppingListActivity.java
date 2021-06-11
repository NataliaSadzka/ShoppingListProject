package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.Product;

import java.util.List;

public class DetailShoppingListActivity extends Activity {

    //private DetailShoppingListAdapter detailShoppingListAdapter;
    //private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_shopping_list_activity);

        final ImageView addNewImage = findViewById(R.id.addNewImage);
        addNewImage.setVisibility(View.VISIBLE);

        final ImageView imageEdit = findViewById(R.id.image_edit);
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addNewImage.setVisibility(View.VISIBLE);
                imageEdit.setVisibility(View.INVISIBLE);
            }
        });


    }
}
