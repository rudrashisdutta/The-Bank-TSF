package com.rudrashisdutta.thebank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.databinding.ActivityCustomerBinding;
import com.rudrashisdutta.thebank.ui.fragments.CustomersFragment;

import java.time.Instant;

public class CustomerActivity extends AppCompatActivity {

    private ActivityCustomerBinding customerActivity;
    private Customer customer;

    private TextView ID;
    private TextView name;
    private TextView balance;
    private TextView email;
    private TextView mob;
    private TextView dob;
    private TextView pan;
    private TextView aadhaar;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize(){
        customerActivity = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(customerActivity.getRoot());
        getValidCustomer();
        setupView();
        setupViewsWithData();
    }
    private void setupView(){
        ID = customerActivity.customerActivityID;
        name = customerActivity.customerActivityName;
        balance = customerActivity.customerActivityBalance;
        email = customerActivity.customerActivityEmail;
        mob = customerActivity.customerActivityMob;
        dob = customerActivity.customerActivityDob;
        pan = customerActivity.customerActivityPan;
        aadhaar = customerActivity.customerActivityAadhaar;
        address = customerActivity.customerActivityAddress;
    }
    private void setupViewsWithData(){
        ID.setText(String.valueOf(customer.getCustomerID()));
        name.setText(customer.getCustomerName());
        balance.setText(String.valueOf(customer.getBalance()));
        email.setText(customer.getEmail());
        mob.setText(customer.getMobileNumber());
        long dobInLong = Long.parseLong(customer.getDOB());
        dob.setText(Instant.ofEpochMilli(dobInLong).toString());
        pan.setText(customer.getPAN());
        aadhaar.setText(customer.getAadhaar());
        address.setText(customer.getAddress());
    }
    private void getValidCustomer(){
        Intent intent = getIntent();
        long customerID = intent.getLongExtra(CustomersFragment.EXTRA, -1);
        if(customerID != -1){
            customer = Customers.get(this, customerID);
            Log.e("qwerty", customer.getCustomerName());
        }
    }
}