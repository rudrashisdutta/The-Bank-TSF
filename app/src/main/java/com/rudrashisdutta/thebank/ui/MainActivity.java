package com.rudrashisdutta.thebank.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainActivity;

    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainActivity.getRoot());


    }

    public void initialize(){

    }
}