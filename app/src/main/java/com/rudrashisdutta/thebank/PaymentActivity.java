package com.rudrashisdutta.thebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.database.Transactions;
import com.rudrashisdutta.thebank.databinding.ActivityPaymentBinding;
import com.rudrashisdutta.thebank.logic.MakeTransaction;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding paymentActivity;
    private TextView customerName;

//    Customer selection screen
    private SwipeRefreshLayout refreshCustomers;
    private ListView listOfCustomers;

//    Amount entering screen
    private TextView receiverNameAmountScreen;
    private TextView amount;
    private List<Button> amountButtons;
    private Button returnButton;
    private Button continueButton;

//    Payment confirmation screen
    private TextView receiverNamePaymentConfirmationScreen;


    private Customer sender;
    private Customer receiver;
    private Transaction transaction;
    private Transaction repayTransaction;
    private MakeTransaction makeTransaction;

    private boolean pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    private void initialize(){
        paymentActivity = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(paymentActivity.getRoot());
        Intent intent = getIntent();
        String mode = intent.getStringExtra(MakeTransaction.PAYMENT_MODE);
        pay = mode.equals(MakeTransaction.PAY);
        Log.e("PAYMENT MODE", mode);
        if(pay){
            long customerID = intent.getLongExtra(MakeTransaction.ID, -1);
            if(customerID != -1){
                sender = Customers.get(this, customerID);
            }
        } else{
            String transactionID = intent.getStringExtra(MakeTransaction.ID);
            if(transactionID != null){
                repayTransaction = Transactions.get(this, transactionID);
                sender = repayTransaction.getReceiver();
                receiver = repayTransaction.getCustomer();
            }
        }
        initializeViews();
        setupUI();
        setupViews();
    }
    private void initializeViews(){
        customerName = paymentActivity.paymentActivityCustomerName;
        refreshCustomers = paymentActivity.paymentActivityRefreshCustomerList;
        listOfCustomers = paymentActivity.paymentActivityCustomerList;
        receiverNameAmountScreen = paymentActivity.paymentActivityAmountReceiver;
        amount = paymentActivity.paymentActivityAmount;
        amountButtons = new ArrayList<Button>(){
            {
                add(paymentActivity.paymentActivityAmount0);
                add(paymentActivity.paymentActivityAmount1);
                add(paymentActivity.paymentActivityAmount2);
                add(paymentActivity.paymentActivityAmount3);
                add(paymentActivity.paymentActivityAmount4);
                add(paymentActivity.paymentActivityAmount5);
                add(paymentActivity.paymentActivityAmount6);
                add(paymentActivity.paymentActivityAmount7);
                add(paymentActivity.paymentActivityAmount8);
                add(paymentActivity.paymentActivityAmount9);
                add(paymentActivity.paymentActivityAmountBackspace);
                add(paymentActivity.paymentActivityAmountDot);
            }
        };
        returnButton = paymentActivity.paymentActivityAmountReturn;
        continueButton = paymentActivity.paymentActivityAmountContinue;
        //TODO: complete initializing
    }
    private void setupUI(){
        if(pay){
            //TODO: UI in case of pay
        } else{
            //TODO: UI in case of re-pay
        }
    }
    private void setupViews(){
        customerName.setText(sender.getCustomerName());
    }
}