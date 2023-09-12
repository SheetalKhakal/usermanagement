package com.example.usermanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kotlin.jvm.Synchronized;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    /*
    BASE_URL link is copied from POSTMAN app.
    For running this on mobile the make some changes in this link:BASE_URL="https://localhost/UserApi/register.php";
    Remove 's' from https, remove 'register.php' and remove 'localhost'.
    For localhost use IPAddress of ur PC- Goto command prompt write- ipconfig enter
    Copy Ipv4 Address-192.168.0.127 and paste behalf of localhost.
    */

    private static String BASE_URL="http://192.168.0.127/UserApi/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

        //(We can see response and request in Logcat)
    private OkHttpClient.Builder builder=new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
    private RetrofitClient()
    {
        //(We can see response and request in Logcat)
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())        //(We can see response and request in Logcat)
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient==null){
            retrofitClient=new RetrofitClient();
        }
        return retrofitClient;

    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

}
