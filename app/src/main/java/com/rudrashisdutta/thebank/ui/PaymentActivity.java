package com.rudrashisdutta.thebank.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.database.Transactions;
import com.rudrashisdutta.thebank.databinding.ActivityPaymentBinding;
import com.rudrashisdutta.thebank.logic.CustomerAdapter;
import com.rudrashisdutta.thebank.logic.MakeTransaction;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding paymentActivity;
    private TextView customerName;

    private ConstraintLayout paymentOptions;
//    Customer selection screen
    private ConstraintLayout customerSelectionScreen;
    private SwipeRefreshLayout refreshCustomers;
    private ListView listOfCustomers;

//    Amount entering screen
    private ConstraintLayout amountScreen;
    private TextView receiverNameAmountScreen;
    private TextView amountView;
    private List<Button> amountButtons;
    private Button returnButton;
    private Button continueButton;

//    Payment confirmation screen
    private ConstraintLayout paymentConfirmationScreen;
    private TextView paymentConfirmationReceiverName;
    private TextView paymentConfirmationSenderID;
    private TextView paymentConfirmationReceiverID;
    private TextView paymentConfirmationAmount;
    private Button changeAmount;
    private Button payButton;


    private Customer sender;
    private Customer receiver;
    private Transaction transaction;
    private MakeTransaction makeTransaction;

    private boolean pay;

    private double amount;
    private double bufferAmount;

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
                transaction = Transactions.get(this, transactionID);
                amount = transaction.getAmount();
                bufferAmount = amount;
                sender = transaction.getReceiver();
                receiver = transaction.getCustomer();
            }
        }
        initializeViews();
        setupUI();
        setupViews();
    }
    private void initializeViews(){
        customerName = paymentActivity.paymentActivityCustomerName;

//        PAYMENT OPTIONS SCREEN
        paymentOptions = paymentActivity.paymentActivityPaymentOptions;

//        CUSTOMER SELECTION SCREEN
        customerSelectionScreen = paymentActivity.paymentActivityCustomerSelectionScreen;
        refreshCustomers = paymentActivity.paymentActivityRefreshCustomerList;
        listOfCustomers = paymentActivity.paymentActivityCustomerList;

//        AMOUNT SCREEN
        amountScreen = paymentActivity.paymentActivityAmountSelection;
        receiverNameAmountScreen = paymentActivity.paymentActivityAmountReceiver;
        amountView = paymentActivity.paymentActivityAmount;
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

//        PAYMENT CONFIRMATION SCREEN
        paymentConfirmationScreen = paymentActivity.paymentActivityPaymentConfirmation;
        paymentConfirmationReceiverName = paymentActivity.paymentActivityPaymentConfirmationReceiver;
        paymentConfirmationSenderID = paymentActivity.paymentActivityPaymentConfirmationSenderID;
        paymentConfirmationReceiverID = paymentActivity.paymentActivityPaymentConfirmationReceiverID;
        paymentConfirmationAmount = paymentActivity.paymentActivityPaymentConfirmationAmount;
        changeAmount = paymentActivity.paymentActivityPaymentConfirmationChangeAmount;
        payButton = paymentActivity.paymentActivityPaymentConfirmationPay;
    }
    private void setupUI(){
        if(pay){
            paymentConfirmationScreen.setVisibility(View.GONE);
            paymentOptions.setVisibility(View.VISIBLE);
            customerSelectionScreen.setVisibility(View.VISIBLE);
            amountScreen.setVisibility(View.GONE);
        } else{
            customerSelectionScreen.setVisibility(View.GONE);
            amountScreen.setVisibility(View.VISIBLE);
            proceedToPaymentConfirmation();
        }
    }
    private void setupViews() {
        customerName.setText(sender.getCustomerName());
        setUpListOfCustomers();
        setRefreshCustomers();
        setReturnButton();
        setAmountView();
        setAmountButtons();
        continueButton.setOnClickListener(view -> proceedToPaymentConfirmation());
        continueButton.setEnabled(false);
        setupPaymentConfirmation();
    }
    private void setUpListOfCustomers() {
        List<Customer> customers = new ArrayList<>();
        for(Customer customer : Customers.getCustomers(this)){
            if(customer.getCustomerID() != sender.getCustomerID()){
                customers.add(customer);
            }
        }
        CustomerAdapter listAdapter = new CustomerAdapter(this, R.layout.activity_customer_list_item, customers);
        listOfCustomers.setAdapter(listAdapter);
        listOfCustomers.setOnItemClickListener((adapterView, view, i, l) -> customerSelection(true, view));
    }

    private String getReceiverName(){
        return String.format(getString(R.string.receiver_name_format), receiver.getCustomerName());
    }
    private void setRefreshCustomers(){
        refreshCustomers.setOnRefreshListener(() -> {
            setUpListOfCustomers();
            refreshCustomers.setRefreshing(false);
        });
    }
    private void setReturnButton(){
        if(pay) {
            returnButton.setOnClickListener(view -> customerSelection(false, null));
        } else {
            returnButton.setOnClickListener(view -> proceedToPaymentConfirmation());
        }
    }
    private void setAmountButtons(){
        for(int i = 0; i <= 9; i++){
            amountButtons.get(i).setOnClickListener(view -> {
                String character = view.getTag().toString();
                addToAmountViewText(character);
            });
        }
        amountButtons.get(10).setOnClickListener(view -> {
            String amount = amountView.getText().toString();
            if(amount.isEmpty()) return;

            amountView.setText(amount.substring(0, amount.length() - 1));
        });
        amountButtons.get(10).setOnLongClickListener(view -> {
            amountView.setText("");
            return false;
        });
        amountButtons.get(11).setOnClickListener(view -> {
            String character = ".";
            addToAmountViewText(character);
        });

    }
    private void addToAmountViewText(String character){
        String amount = amountView.getText().toString();
        if(!amount.isEmpty()){
            amount += character;
        }else{
            amount = character;
        }
        amountView.setText(amount);
    }
    private void setAmountView(){
        amountView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                if(charSequence.isEmpty()) {
                    continueButton.setEnabled(false);
                    return;
                }
                String amount = processAmount(charSequence);
                if(!amount.equals(charSequence)){
                    amountView.setText(amount);
                }
                try {
                    bufferAmount = Double.parseDouble(amount);
                    if(bufferAmount <= sender.getBalance()) {
                        continueButton.setEnabled(bufferAmount != 0.0);
                    } else{
                        amountView.setText(String.valueOf(sender.getBalance()));
                        Toast.makeText(getApplicationContext(), "Not enough balance...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void setupPaymentConfirmation(){
        changeAmount.setOnClickListener(view -> setChangeAmount());
        payButton.setOnClickListener(view -> setPayButton());
    }
    private void setChangeAmount(){
        paymentConfirmationScreen.setVisibility(View.GONE);
        paymentOptions.setVisibility(View.VISIBLE);
        amountView.setText(String.valueOf(amount));
    }
    private void setPayButton(){
        makeTransaction = MakeTransaction.build(sender, receiver, transaction.getAmount(), getApplicationContext());
        boolean result = makeTransaction.make();
        Intent transactionResult = new Intent(getApplicationContext(), TransactionResult.class);
        if(result){
            transactionResult.putExtra(TransactionResult.EXTRA, TransactionResult.SUCCESS);
        } else{
            transactionResult.putExtra(TransactionResult.EXTRA, TransactionResult.FAILURE);
        }
        startActivity(transactionResult);
        finish();
    }
    private void proceedToPaymentConfirmation(){
        if(pay){
            amount = bufferAmount;
        } else if (bufferAmount != amount) {
            amount = bufferAmount;
        }
        transaction = Transaction.build(this, null, sender.getCustomerID(), receiver.getCustomerID(), 0, amount);
        paymentConfirmationScreen.setVisibility(View.VISIBLE);
        paymentOptions.setVisibility(View.GONE);
        paymentConfirmationReceiverName.setText(getReceiverName());
        paymentConfirmationSenderID.setText(String.valueOf(sender.getCustomerID()));
        paymentConfirmationReceiverID.setText(String.valueOf(receiver.getCustomerID()));
        paymentConfirmationAmount.setText(String.valueOf(transaction.getAmount()));
    }
    private void customerSelection(boolean selected, @Nullable View view) {
        if(selected){
            assert view != null;
            CustomerAdapter.CustomerListViewHolder viewHolder = (CustomerAdapter.CustomerListViewHolder) view.getTag();
            long receiverID;
            String[] unformattedString = viewHolder.getAccountId().getText().toString().split(" ");
            String IDAsString = unformattedString[unformattedString.length - 1];
            receiverID = Long.parseLong(IDAsString);
            receiver = Customers.get(this, receiverID);
            customerSelectionScreen.setVisibility(View.GONE);
            amountScreen.setVisibility(View.VISIBLE);
            receiverNameAmountScreen.setText(getReceiverName());
        } else{
            receiver = null;
            customerSelectionScreen.setVisibility(View.VISIBLE);
            amountScreen.setVisibility(View.GONE);
            receiverNameAmountScreen.setText("");
        }
    }

    private String processAmount(String amount){
        if(!amount.startsWith(".")){
            int limitBefore = 8;
            int limitAfter = 2;
            String[] partsOfAmount = amount.split("\\.", 2);
            if(partsOfAmount[0].length() >= limitBefore){
                amount = partsOfAmount[0].substring(0, limitBefore);
            } else {
                amount = partsOfAmount[0];
            }
            try{
                if(partsOfAmount[1].length() >= limitAfter){
                    amount += "." + partsOfAmount[1].substring(0, limitAfter);
                } else {
                    amount += "." + partsOfAmount[1];
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return amount;
    }
}