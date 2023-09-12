package com.example.usermanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView loginlink;
    EditText name,email,password;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.register_account_activity_main);


        loginlink=findViewById(R.id.loginLink);
        name=(EditText) findViewById(R.id.etName);
        email=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
        register=findViewById(R.id.btnRegister);

        register.setOnClickListener(this);
        loginlink.setOnClickListener(this);

        //hide ActionBar
        getSupportActionBar().hide();

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btnRegister){
            registerUser();
         } else if(view.getId()==R.id.loginLink) {
            switchOnLogin();
            }

    }

    private void registerUser()
    {
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(userName.isEmpty()){
            name.requestFocus();
            name.setError("Please enter name.");
            return;
        }

        if(userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please enter correct email.");
            return;
        }

        if(userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter password.");
            return;
        }
        if(userPassword.length()<8){
            password.requestFocus();
            password.setError("Please enter password more than 8 characters.");
            return;
        }
        //Get the Api
        Call<RegisterResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .register(userName,userEmail,userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse=response.body();
                if(response.isSuccessful())
                {
                    Intent intent=new Intent(RegisterAccountActivity.this,LoginActivity.class);
                    //To close 1st activity after we going to next activity when we click back.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(RegisterAccountActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterAccountActivity.this,registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterAccountActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchOnLogin()
    {
        Intent i =new Intent(RegisterAccountActivity.this,LoginActivity.class);
        startActivity(i);
    }
}