package com.example.usermanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.DeleteAccountResponse;
import com.example.usermanagement.NavFragments.DashboardFragment;
import com.example.usermanagement.NavFragments.ProfileFragment;
import com.example.usermanagement.NavFragments.UsersFragment;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView=findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());

        sharedPrefManager =new SharedPrefManager(getApplicationContext());

     }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        Fragment fragment=null;
        if(menuItem.getItemId()==R.id.dashboard) {
            fragment = new DashboardFragment();
         }else if(menuItem.getItemId()==R.id.users) {
            fragment = new UsersFragment();
         } else if (menuItem.getItemId()==R.id.profile) {
            fragment=new ProfileFragment();
        }

        if(fragment!=null){
            loadFragment(fragment);
        }
            //it will change the fragment icon color(true)
            return true;
    }


    //load all the fragment into relative layout by using this method loadFragment.
    void loadFragment(Fragment fragment)
    {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

     @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==R.id.logout) {
            logoutUser();
        } else if(item.getItemId()==R.id.deleteAccount){
            deleteAccount();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount()
    {
        Call<DeleteAccountResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteUserAccount(sharedPrefManager.getUser().getId());
        call.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response)
            {
                DeleteAccountResponse deleteAccountResponse=response.body();
                if(response.isSuccessful())
                {
                    if(deleteAccountResponse.getError().equals("200"))
                    {
                        logoutUser();
                        Toast.makeText(HomeActivity.this,deleteAccountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(HomeActivity.this,deleteAccountResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t)
            {
                Toast.makeText(HomeActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser()
    {
        sharedPrefManager.logout();
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show();
    }
}