package com.example.usermanagement.NavFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.UpdatePasswordResponse;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener
{
    EditText etUserName,etUserEmail, currentPass, newPass;
    Button updateUserAccountBtn, updateUserPasswordBtn;
    int userId;
    String userEmailId;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        //For update Account
         etUserName=view.findViewById(R.id.userName);
         etUserEmail=view.findViewById(R.id.userEmail);
         updateUserAccountBtn=view.findViewById(R.id.btnUpdateAccount);

         //For update Password
        currentPass=view.findViewById(R.id.currentPassword);
        newPass=view.findViewById(R.id.newPassword);
        updateUserPasswordBtn=view.findViewById(R.id.btnUpdatePassword);

         sharedPrefManager= new SharedPrefManager(getActivity());
         userId=sharedPrefManager.getUser().getId();
         userEmailId=sharedPrefManager.getUser().getEmail();


         updateUserAccountBtn.setOnClickListener(this);
         updateUserPasswordBtn.setOnClickListener(this);
         return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btnUpdateAccount)
        {
                updateUserAccount();
         }else
             if(view.getId()==R.id.btnUpdatePassword)
        {
            updatePassword();
        }

    }

    private void updatePassword()
    {
        String userCurrentPassword=currentPass.getText().toString().trim();
        String userNewPassword=newPass.getText().toString().trim();

        if(userCurrentPassword.isEmpty())
        {
            currentPass.setError("Please enter current password.");
            currentPass.requestFocus();
            return;
        }
        if(userCurrentPassword.length()<8)
        {
            currentPass.setError("Enter 8 digit password.");
            currentPass.requestFocus();
            return;
        }
        if(userNewPassword.isEmpty())
        {
            newPass.setError("Please enter current password.");
            newPass.requestFocus();
            return;
        }
        if(userNewPassword.length()<8)
        {
            newPass.setError("Enter 8 digit password.");
            newPass.requestFocus();
            return;
        }

        Call<UpdatePasswordResponse> call=RetrofitClient.getInstance().getApi().updateUserPassword(userEmailId,userCurrentPassword,userNewPassword);
        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response)
            {
                UpdatePasswordResponse passwordResponse=response.body();
                 if(response.isSuccessful())
                {
                    if(passwordResponse.getError().equals("200"))
                    {
                         Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserAccount()
    {
        String username=etUserName.getText().toString().trim();
        String email=etUserEmail.getText().toString().trim();

        if(username.isEmpty()){
            etUserName.setError("Please enter user name");
            etUserName.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etUserEmail.requestFocus();
            etUserEmail.setError("Please enter correct email");
            return;
        }

        Call<LoginResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updateUserAccount(userId,username,email);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                LoginResponse updateResponse=response.body();
                if(response.isSuccessful()) {
                    if (updateResponse.getError().equals("200")) {
                        sharedPrefManager.saveUser(updateResponse.getUser());
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                     }
                }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t)
            {
                 Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}