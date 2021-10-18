package com.rudrashisdutta.thebank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rudrashisdutta.thebank.PaymentActivity;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.databinding.ActivityCustomerBinding;
import com.rudrashisdutta.thebank.logic.MakeTransaction;
import com.rudrashisdutta.thebank.ui.fragments.CustomersFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private Button pay;

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
        pay.setOnClickListener(view -> {
            Intent paymentActivity = new Intent(this, PaymentActivity.class);
            paymentActivity.putExtra(MakeTransaction.PAYMENT_MODE, MakeTransaction.PAY);
            paymentActivity.putExtra(MakeTransaction.ID, customer.getCustomerID());
            startActivity(paymentActivity);
        });
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

        pay = customerActivity.customerActivityPayBtn;
    }
    private void setupViewsWithData(){
        ID.setText(String.valueOf(customer.getCustomerID()));
        name.setText(customer.getCustomerName());
        balance.setText(String.valueOf(customer.getBalance()));
        email.setText(customer.getEmail());
        mob.setText(customer.getMobileNumber());
        long dobInLong = Long.parseLong(customer.getDOB());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date(dobInLong));
        dob.setText(date);
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