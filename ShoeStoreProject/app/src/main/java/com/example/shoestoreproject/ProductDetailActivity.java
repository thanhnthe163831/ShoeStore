package com.example.shoestoreproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvProductName, tvProductPrice, tvProductDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvProductName = findViewById(R.id.tvProductNameDetail);
        tvProductPrice = findViewById(R.id.tvProductPriceDetail);
        tvProductDescription = findViewById(R.id.tvProductDescriptionDetail);

        // Retrieve the product data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("ProductName");
            String productPrice = intent.getStringExtra("Price");
            String productDescription = intent.getStringExtra("Description");

            tvProductName.setText(productName);
            tvProductPrice.setText(productPrice);
            tvProductDescription.setText(productDescription);
        }
    }
}
