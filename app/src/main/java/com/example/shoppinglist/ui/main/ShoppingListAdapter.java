package com.example.shoppinglist.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingList;

import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    public void setShoppingList(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.MyViewHolder holder, final int position) {
        holder.textViewName.setText(this.shoppingLists.get(position).getName());
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(v.getContext());
                db.shoppingListDao().delete(shoppingLists.get(position));
            }
        });

        holder.imageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.shoppingLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageDetail;
        ImageView imageDelete;

        public MyViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            imageDetail = view.findViewById(R.id.image_detail);
            imageDelete = view.findViewById(R.id.image_delete);
        }
    }
}