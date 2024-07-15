package com.example.shoestoreproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoestoreproject.Entity.Product;
import com.example.shoestoreproject.MProductDetailActivity;
import com.example.shoestoreproject.ProductAdapter;
import com.example.shoestoreproject.ProductDetailActivity;
import com.example.shoestoreproject.R;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder> {
    private List<Product> productList;
    private Context context;

    public ManagerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ManagerAdapter.ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_item, parent, false);
        return new ManagerAdapter.ManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter.ManagerViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvMId.setText(String.valueOf(product.getProductID()));
        holder.tvMName.setText(product.getProductName());
        holder.tvMPrice.setText(String.valueOf(product.getPrice()));
        holder.tvMDescription.setText(product.getDescription());
        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MProductDetailActivity.class);
                intent.putExtra("ProductName", product.getProductName());
                intent.putExtra("Price", String.valueOf(product.getPrice()));
                intent.putExtra("Description", product.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ManagerViewHolder extends RecyclerView.ViewHolder {

        TextView tvMId,tvMName, tvMPrice, tvMDescription;

        public ManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMId = itemView.findViewById(R.id.tvMID);
            tvMName = itemView.findViewById(R.id.tvMName);
            tvMPrice = itemView.findViewById(R.id.tvMPrice);
            tvMDescription = itemView.findViewById(R.id.tvMDescription);
        }
    }
}

