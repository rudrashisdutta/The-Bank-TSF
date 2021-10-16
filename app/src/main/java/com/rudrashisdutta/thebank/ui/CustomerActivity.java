package com.rudrashisdutta.thebank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.databinding.ActivityCustomerBinding;
import com.rudrashisdutta.thebank.ui.fragments.CustomersFragment;

public class CustomerActivity extends AppCompatActivity {

    private ActivityCustomerBinding customerActivity;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize(){
        customerActivity = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(customerActivity.getRoot());
        Intent intent = getIntent();
        long customerID = intent.getLongExtra(CustomersFragment.EXTRA, -1);
        if(customerID != -1){
            customer = Customers.get(this, customerID);
            Log.e("qwerty", customer.getCustomerName());
        }
    }
}