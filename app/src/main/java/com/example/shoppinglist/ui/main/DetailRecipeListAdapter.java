package com.example.shoppinglist.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.RecipeProduct;

import java.util.List;

public class DetailRecipeListAdapter extends RecyclerView.Adapter<DetailRecipeListAdapter.MyViewHolder> {

    private Context context;
    private List<RecipeProduct> recipeProducts;

    public DetailRecipeListAdapter(Context context, List<RecipeProduct> recipeProducts) {
        this.context = context;
        this.recipeProducts = recipeProducts;
    }

    public void setProducts(List<RecipeProduct> recipeProducts) {
        this.recipeProducts = recipeProducts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recipe_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailRecipeListAdapter.MyViewHolder holder, int position) {
        final RecipeProduct recipeProduct = recipeProducts.get(position);
        //holder.textViewName.setText(recipeProduct.toString());
        holder.textViewName.setText(recipeProduct.getProduct().getName() + " " + recipeProduct.getQuantity() + " " + recipeProduct.getProduct().getQuantityUnit());

        /*holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeProduct.toString();

                AppDatabase.getDbInstance(context).recipeProductDao().insertRecipeProduct(recipeProduct);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return recipeProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageEdit;
        ImageView addNew;
        TextView textViewName;

        public MyViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.text_view_name);
            imageEdit = view.findViewById(R.id.image_delete);
            addNew = view.findViewById(R.id.add_new_image);
        }
    }
}
