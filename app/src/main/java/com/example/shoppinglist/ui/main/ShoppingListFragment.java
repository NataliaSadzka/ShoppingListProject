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
import com.example.shoppinglist.database.ShoppingList;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ShoppingListFragment extends Fragment {

    private ShoppingListAdapter shoppingListAdapter;
    private List<ShoppingList> shoppingLists;
    private ImageView addNewShoppingListImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);

        return inflater.inflate(R.layout.shopping_list_activity, viewGroup, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        shoppingLists = db.shoppingListDao().getAllShoppingLists();
        shoppingListAdapter = new ShoppingListAdapter(this.getContext(), shoppingLists);
        recyclerView.setAdapter(shoppingListAdapter);

        addNewShoppingListImage = view.findViewById(R.id.addNewImage);

        addNewShoppingListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
