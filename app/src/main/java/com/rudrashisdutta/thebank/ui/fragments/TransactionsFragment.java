package com.rudrashisdutta.thebank.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Application;
import com.rudrashisdutta.thebank.database.Transactions;
import com.rudrashisdutta.thebank.logic.TransactionAdapter;
import com.rudrashisdutta.thebank.logic.ViewPagerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView supportActionBar;
    private ListView transactionListView;
    private SwipeRefreshLayout refresh;
    private ToggleButton order;
    private Context context;

    private static TransactionsFragment transactionsFragment;
    private List<Transaction> transactions;

    public TransactionsFragment(){

    }
    public TransactionsFragment(TextView supportActionBar, Context context) {
        // Required empty public constructor
        this.supportActionBar = supportActionBar;
        this.context = context;
        transactionsFragment = this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsFragment.
     */
    public static TransactionsFragment newInstance(String param1, String param2, TextView supportActionBar, Context context) {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
    }
    private void initialize(){
        supportActionBar = (TextView) ViewPagerAdapter.getFragmentActivity().findViewById(R.id.toolBarActivityName);
        context = ViewPagerAdapter.getFragmentActivity().getApplicationContext();
        supportActionBar.setText(R.string.transactions);
        supportActionBar.setGravity(Gravity.CENTER_HORIZONTAL);
        transactionListView = this.requireView().findViewById(R.id.list_of_transactions);
        refresh = this.requireView().findViewById(R.id.refresh_transactions);
        order = this.requireView().findViewById(R.id.order_of_transactions);
        update();
        refresh.setOnRefreshListener(() -> {
            order.setChecked(getOrderButtonState());
            update();
            refresh.setRefreshing(false);
        });
        order.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Application application = new Application(context);
            application.updateTransactionsOrder(isChecked);
            update();
        });
        order.setChecked(getOrderButtonState());
    }


    public void update(){
        transactions = Transactions.getTransactions(context);
        TransactionAdapter listAdapter = new TransactionAdapter(context, R.layout.activity_transaction_list_item, transactions);
        transactionListView.setAdapter(listAdapter);
    }
    private boolean getOrderButtonState(){
        Application application = new Application(context);
        return application.getTransactionsOrder().equals(Application.ASCENDING);
    }
}