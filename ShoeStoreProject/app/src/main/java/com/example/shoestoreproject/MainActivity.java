package com.example.shoestoreproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shoestoreproject.API.APIService;
import com.example.shoestoreproject.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private CheckBox chkRemember;
    private Button btnLogin, btnRegister;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        apiService = RetrofitClient.getClient("http://192.168.0.105/").create(APIService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Register Activity
            }
        });
    }

    private void login() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        APIService.LoginRequest loginRequest = new APIService.LoginRequest(username, password);
        apiService.login(loginRequest).enqueue(new Callback<APIService.AccountResponse>() {
            @Override
            public void onResponse(Call<APIService.AccountResponse> call, Response<APIService.AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    APIService.AccountResponse accountResponse = response.body();
                    if ("success".equals(accountResponse.status)) {
                        String role = accountResponse.account.getRole();
                        if ("customer".equalsIgnoreCase(role)) {
                            // Navigate to Customer Activity
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else if ("manager".equalsIgnoreCase(role)) {
                            // Navigate to Manager Activity
                            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, accountResponse.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIService.AccountResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
