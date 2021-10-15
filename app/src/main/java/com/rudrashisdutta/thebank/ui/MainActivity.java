package com.rudrashisdutta.thebank.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Application;
import com.rudrashisdutta.thebank.database.Transactions;
import com.rudrashisdutta.thebank.databinding.ActivityMainBinding;
import com.rudrashisdutta.thebank.logic.MakeTransaction;
import com.rudrashisdutta.thebank.logic.ViewPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainActivity;

    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager2 mainScreen;
    private ViewPagerAdapter mainScreenAdapter;
    private TextView activityNameOnSupportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = ActivityMainBinding.inflate(getLayoutInflater());
        initialize();
    }

    public void initialize(){
        setContentView(mainActivity.getRoot());
        tabs = mainActivity.tabs;
        toolbar = mainActivity.toolBar;
        activityNameOnSupportActionBar = mainActivity.toolBarActivityName;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        new Thread(() -> Application.createDataIfNotFound(MainActivity.this)).start();
        mainScreen = mainActivity.mainScreen;
        mainScreenAdapter = new ViewPagerAdapter(this, activityNameOnSupportActionBar, this);
        mainScreen.setAdapter(mainScreenAdapter);
        new TabLayoutMediator(tabs, mainScreen, (tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.ic_customers);
                tab.setText("CUSTOMERS");
            } else{
                tab.setIcon(R.drawable.ic_transactions);
                tab.setText("TRANSACTIONS");
            }
        }).attach();
        Transactions transactions = new Transactions(this);
        transactions.getWritableDatabase();
        MakeTransaction.build(Customer.get(this, 100291), Customer.get(this, 100292), 123.23, this).make();
    }
}