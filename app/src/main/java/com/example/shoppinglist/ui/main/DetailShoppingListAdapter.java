package com.example.shoppinglist.ui.main;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingListProduct;

import java.util.List;

public class DetailShoppingListAdapter extends RecyclerView.Adapter<DetailShoppingListAdapter.MyViewHolder> {

    private Context context;
    private List<ShoppingListProduct> products;

    public DetailShoppingListAdapter(Context context, List<ShoppingListProduct> products) {
        this.context = context;
        this.products = products;
    }

    public void setProducts(List<ShoppingListProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_shopping_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ShoppingListProduct product = products.get(position);
        holder.checkBox.setChecked(product.isAdded());
        holder.checkBox.setText(product.getProduct().getName() + " " + product.getQuantity() + " " + product.getProduct().getQuantityUnit());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                product.setAdded(isChecked);
                setPaintFlags(holder.checkBox, isChecked);

                AppDatabase.getDbInstance(context).shoppingListProductDao().insertShoppingListProduct(product);
            }
        });

        setPaintFlags(holder.checkBox, product.isAdded());
    }

    private void setPaintFlags(CheckBox checkBox, boolean isChecked) {
        if (isChecked) {
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            checkBox.setPaintFlags(0);
        }
    }


    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageEdit;
        ImageView addNew;
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            imageEdit = view.findViewById(R.id.image_delete);
            addNew = view.findViewById(R.id.add_new_image);
        }
    }
}