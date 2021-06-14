package com.example.shoppinglist.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppinglist.R;
import com.example.shoppinglist.database.AppDatabase;
import com.example.shoppinglist.database.ShoppingList;
import com.example.shoppinglist.database.ShoppingListProduct;
import com.example.shoppinglist.database.ShoppingListWithProducts;

import java.util.List;

public class ShoppingListFragment extends Fragment {

    private ShoppingListAdapter shoppingListAdapter;
    private List<ShoppingList> shoppingLists;
    private RecyclerView recyclerView;
    private ImageView addNewShoppingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);

        return inflater.inflate(R.layout.shopping_list_activity, viewGroup, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        shoppingLists = db.shoppingListDao().getAllShoppingLists();
        shoppingListAdapter = new ShoppingListAdapter(this.getContext(), shoppingLists);
        recyclerView.setAdapter(shoppingListAdapter);

        shoppingListAdapter.notifyDataSetChanged();

        addNewShoppingList = view.findViewById(R.id.add_new_image);

        addNewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNewShoppingListActivity.class);
                startActivity(intent);
            }
        });
    }

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

            return new ShoppingListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListAdapter.MyViewHolder holder, final int position) {
            holder.textViewName.setText(this.shoppingLists.get(position).getName());

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingList shoppingList = shoppingLists.get(position);
                    shoppingLists.remove(shoppingList);
                    AppDatabase db = AppDatabase.getDbInstance(v.getContext());
                    db.shoppingListDao().delete(shoppingList);

                    notifyDataSetChanged();
                }
            });

            holder.imageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingListWithProducts shoppingListWithProducts = AppDatabase.getDbInstance(v.getContext()).shoppingListWithProductsDAO().findShoppingListWithProductsByShoppingListId(shoppingLists.get(position).getShoppingListId());

                    if (shoppingListWithProducts.getShoppingList() == null) {
                        ShoppingList shoppingList = AppDatabase.getDbInstance(v.getContext()).shoppingListDao().findShoppingListByShoppingListId(shoppingLists.get(position).getShoppingListId());
                        shoppingListWithProducts.setShoppingList(shoppingList);
                    }

                    List<ShoppingListProduct> shoppingListProducts = AppDatabase.getDbInstance(v.getContext()).shoppingListProductDao().findShoppingListProductsByShoppingListId(shoppingListWithProducts.getShoppingList().getShoppingListId());
                    shoppingListWithProducts.setProducts(shoppingListProducts);

                    v.getContext().startActivity(
                            new Intent(v.getContext(), DetailShoppingListActivity.class)
                                    .putExtra("editMode", false)
                                    .putExtra("shoppingList", shoppingListWithProducts));
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
}
