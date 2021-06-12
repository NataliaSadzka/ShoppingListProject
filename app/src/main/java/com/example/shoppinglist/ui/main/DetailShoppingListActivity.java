package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;

public class DetailShoppingListActivity extends Activity implements View.OnClickListener {

    //private DetailShoppingListAdapter detailShoppingListAdapter;
    //private List<Product> products;

    private Button saveButton;
    private Button cancelButton;
    private ImageView editImage;
    private ImageView addNewImage;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_shopping_list_activity);

        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        editImage = findViewById(R.id.image_edit);
        addNewImage = findViewById(R.id.add_new_image);
        backImage = findViewById(R.id.image_back);

        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        addNewImage.setVisibility(View.INVISIBLE);


        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        /*final ImageView addNewImage = findViewById(R.id.add_new_image);
        addNewImage.setVisibility(View.VISIBLE);

        final  ImageView imageBack = findViewById(R.id.image_back);

        final Button saveButton = findViewById(R.id.save_button);

        final Button cancelButton = findViewById(R.id.cancel_button);

        final ImageView imageEdit = findViewById(R.id.image_edit);
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addNewImage.setVisibility(View.VISIBLE);
                imageEdit.setVisibility(View.INVISIBLE);
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == editImage.getId()) {
            editImage.setVisibility(View.INVISIBLE);
            backImage.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            addNewImage.setVisibility(View.VISIBLE);
        }
    }
}
