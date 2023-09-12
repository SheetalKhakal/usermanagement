package com.example.usermanagement.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usermanagement.R;
import com.example.usermanagement.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment
{
    TextView textName,textEmail;
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        textName=view.findViewById(R.id.textName);
        textEmail=view.findViewById(R.id.textEmail);

        sharedPrefManager =new SharedPrefManager(getActivity());
        String userName="Hey! "+sharedPrefManager.getUser().getUsername();
        textName.setText(userName);
        textEmail.setText(sharedPrefManager.getUser().getEmail());
        return view;
    }
}