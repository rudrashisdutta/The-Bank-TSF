package com.rudrashisdutta.thebank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.databinding.ActivityTransactionBinding;

public class TransactionActivity extends AppCompatActivity {

    ActivityTransactionBinding transactionActivity;
    private Transaction transaction;

    private TextView ID;
    private TextView senderID;
    private TextView senderName;
    private TextView receiverID;
    private TextView receiverName;
    private TextView time;
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    private void initialize(){
        transactionActivity = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(transactionActivity.getRoot());
    }
}