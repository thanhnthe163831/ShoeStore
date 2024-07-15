package com.example.shoestoreproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoestoreproject.API.APIService;
import com.example.shoestoreproject.Entity.Product;
import com.example.shoestoreproject.Retrofit.RetrofitClient;
import com.example.shoestoreproject.Adapter.ManagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    EditText editTextId, editTextName, editTextPrice, editTextDesc;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextId = findViewById(R.id.txtAddID);
        editTextName = findViewById(R.id.txtAddName);
        editTextPrice = findViewById(R.id.txtAddPrice);
        editTextDesc = findViewById(R.id.txtAddDes);
        btnAdd = findViewById(R.id.btnAddProduct);

        btnAdd.setOnClickListener(view -> {
            String pId = editTextId.getText().toString().trim();
            String pName = editTextName.getText().toString().trim();
            String pPrice = editTextPrice.getText().toString().trim();
            String description = editTextDesc.getText().toString().trim();

            addProduct(pId, pName, pPrice, description);
        });
    }

    // Xử lý khi người dùng nhấn nút Back trong Toolbar
    public void onBackPressed(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void addProduct(String pId, String pName, String pPrice, String description) {
        APIService apiService = RetrofitClient.getClient("http://192.168.0.105/").create(APIService.class);
        Call<Product> call = apiService.addProduct(pId, pName, pPrice, description);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    // Quay trở lại DepartmentActivity và cập nhật danh sách mới
                    Intent intent = new Intent(AddProductActivity.this, ProductActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Failed to add department", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}