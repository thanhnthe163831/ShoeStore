package com.example.shoestoreproject.API;

import com.example.shoestoreproject.Entity.Account;
import com.example.shoestoreproject.Entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("ShoeStore/login.php")
    Call<AccountResponse> login(@Body LoginRequest loginRequest);

    class LoginRequest {
        String USERNAME;
        String PASSWORD;

        public LoginRequest(String USERNAME, String PASSWORD) {
            this.USERNAME = USERNAME;
            this.PASSWORD = PASSWORD;
        }
    }

    class AccountResponse {
        public String status;
        public Account account;
        public String message;
    }

    @GET("ShoeStore/get_all_shoe.php")
    Call<List<Product>> getProducts();
    @FormUrlEncoded
    @POST("ShoeStore/add_shoe.php") // Đường dẫn đến API PHP trên server của bạn
    Call<Product> addProduct(
            @Field("ProductID") String productId,
            @Field("ProductName") String productName,
            @Field("Price") String productPrice,
            @Field("Description") String description
    );
    @FormUrlEncoded
    @POST("ShoeStore/delete_shoe.php")
    Call<Void> deleteProduct(@Field("ProductId") String productId);
}
