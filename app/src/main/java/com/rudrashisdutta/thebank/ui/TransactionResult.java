package com.rudrashisdutta.thebank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.databinding.ActivityTransactionResultBinding;

public class TransactionResult extends AppCompatActivity {

    private ActivityTransactionResultBinding transactionResult;

    private TextView transactionResultView;

    public static final String EXTRA = "RESULT";
    public static final String SUCCESS = "S";
    public static final String FAILURE = "F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    private void initialize(){
        transactionResult = ActivityTransactionResultBinding.inflate(getLayoutInflater());
        setContentView(transactionResult.getRoot());
        Intent intent = getIntent();
        String result = intent.getStringExtra(EXTRA);
        transactionResultView = transactionResult.transactionResultView;
        if(result.equals(SUCCESS)){
            transactionResultView.setText(R.string.transaction_successfull);
        } else{
            transactionResultView.setText(R.string.transaction_failed);
        }
        transactionResultView.setScaleX(0);
        transactionResultView.setScaleY(0);
        transactionResultView.animate().scaleX(1).scaleY(1).setDuration(200).start();
        setFinish();
    }
    private void setFinish(){
        Handler handler = new Handler();
        Runnable runnable = this::finish;
        handler.postDelayed(runnable, 1000);
    }
}