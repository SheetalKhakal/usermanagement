package com.example.usermanagement;

import com.example.usermanagement.ModelResponse.DeleteAccountResponse;
import com.example.usermanagement.ModelResponse.FetchUserResponse;
import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.ModelResponse.UpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api
{
    @FormUrlEncoded
    @POST("register.php")
    //If we get error:use Jsonreader-setLenient(true) to accept malformed JSON.
        // Solution- Call<ResponseBody> change everywhere we use <RegisterResponse>.
    Call<RegisterResponse> register(
            @Field("username") String username,//if we want to paste same line into next line then ctrl+d
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchallusers.php")
    Call<FetchUserResponse> fetchAllUsers();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAccount(
            @Field("id") int userid,
            @Field("username") String username,
            @Field("email") String email
     );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePasswordResponse> updateUserPassword(
             @Field("email") String email,
            @Field("currentPassword") String current_Password,
            @Field("newPassword") String new_Password
    );

    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteAccountResponse> deleteUserAccount(
            @Field("id") int userId
    );

}
