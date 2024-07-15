package com.example.shoestoreproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoestoreproject.API.APIService;
import com.example.shoestoreproject.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MProductDetailActivity extends AppCompatActivity {

    private TextView tvProductName, tvProductPrice, tvProductDescription;
    private Button btnDelete, btnEdit;
    private String productId; // Add this line to store the product ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mproduct_detail);

        tvProductName = findViewById(R.id.tvProductNameDetail);
        tvProductPrice = findViewById(R.id.tvProductPriceDetail);
        tvProductDescription = findViewById(R.id.tvProductDescriptionDetail);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> deleteProduct());

        // Retrieve the product data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("ProductId"); // Retrieve the product ID from intent
            String productName = intent.getStringExtra("ProductName");
            String productPrice = intent.getStringExtra("Price");
            String productDescription = intent.getStringExtra("Description");

            tvProductName.setText(productName);
            tvProductPrice.setText(productPrice);
            tvProductDescription.setText(productDescription);
        }
    }

    private void deleteProduct() {
        Log.d("MProductDetailActivity", "deleteProduct called with productId: " + productId);
        APIService apiService = RetrofitClient.getClient("http://192.168.0.105/").create(APIService.class);
        Call<Void> call = apiService.deleteProduct(productId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("MProductDetailActivity", "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(MProductDetailActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MProductDetailActivity.this, ProductActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Close the current activity
                } else {
                    Toast.makeText(MProductDetailActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MProductDetailActivity", "onFailure: " + t.getMessage());
                Toast.makeText(MProductDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
