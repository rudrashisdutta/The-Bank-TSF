package com.rudrashisdutta.thebank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rudrashisdutta.thebank.PaymentActivity;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Transactions;
import com.rudrashisdutta.thebank.databinding.ActivityTransactionBinding;
import com.rudrashisdutta.thebank.logic.MakeTransaction;
import com.rudrashisdutta.thebank.ui.fragments.TransactionsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private Button repay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    private void initialize(){
        transactionActivity = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(transactionActivity.getRoot());
        getValidTransaction();
        setupView();
        setupViewsWithData();
        repay.setOnClickListener(view -> {
            //TODO: Balance Check
            Intent paymentActivity = new Intent(this, PaymentActivity.class);
            paymentActivity.putExtra(MakeTransaction.PAYMENT_MODE, MakeTransaction.REPAY);
            paymentActivity.putExtra(MakeTransaction.ID, transaction.getTransactionID());
            startActivity(paymentActivity);
        });
    }
    private void setupView(){
        ID = transactionActivity.transactionActivityID;
        senderID = transactionActivity.transactionActivitySenderID;
        senderName = transactionActivity.transactionActivitySenderName;
        receiverID = transactionActivity.transactionActivityReceiverID;
        receiverName = transactionActivity.transactionActivityReceiverName;
        time = transactionActivity.transactionActivityTime;
        amount = transactionActivity.transactionActivityAmount;

        repay = transactionActivity.customerActivityRepayBtn;
    }
    private void setupViewsWithData(){
        ID.setText(transaction.getTransactionID());
        senderID.setText(String.valueOf(transaction.getCustomerID()));
        senderName.setText(transaction.getCustomer().getCustomerName());
        receiverID.setText(String.valueOf(transaction.getReceiverID()));
        receiverName.setText(transaction.getReceiver().getCustomerName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss.SSS");
        String date = dateFormat.format(new Date(transaction.getTransactionTime()));
        time.setText(date);
        amount.setText(String.valueOf(transaction.getAmount()));
    }
    private void getValidTransaction(){
        Intent intent = getIntent();
        String transactionID = intent.getStringExtra(TransactionsFragment.EXTRA);
        if(transactionID != null){
            transaction = Transactions.get(this, transactionID);
            Log.e("qwerty", transaction.getCustomer().getCustomerName());
        }
    }
}