package com.rudrashisdutta.thebank.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Application;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.logic.CustomerAdapter;
import com.rudrashisdutta.thebank.ui.CustomerActivity;
import com.rudrashisdutta.thebank.ui.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomersFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static String EXTRA = "Customer-ID";

    private final TextView supportActionBar;
    private ListView customerListView;
    private SwipeRefreshLayout refresh;
    private ToggleButton order;
    private final Context context;

    private List<Customer> customers;

    public CustomersFragment(TextView supportActionBar, Context context) {
        // Required empty public constructor
        this.supportActionBar = supportActionBar;
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomersFragment.
     */
    public static CustomersFragment newInstance(String param1, String param2, TextView supportActionBar, Context context) {
        CustomersFragment fragment = new CustomersFragment(supportActionBar, context);
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
        return inflater.inflate(R.layout.fragment_customers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
    }
    private void initialize(){
        supportActionBar.setText(R.string.customers);
        supportActionBar.setGravity(Gravity.CENTER_HORIZONTAL);
        customerListView = this.requireView().findViewById(R.id.list_of_customers);
        refresh = this.requireView().findViewById(R.id.refresh_customers);
        order = this.requireView().findViewById(R.id.order_of_customers);
        customerListView.setOnItemClickListener((adapterView, view, i, l) -> {
            try{
                Intent customerActivity = new Intent(context, CustomerActivity.class);
                CustomerAdapter.CustomerListViewHolder viewHolder = (CustomerAdapter.CustomerListViewHolder) view.getTag();
                long customerID;
                String[] unformattedString = viewHolder.getAccountId().getText().toString().split(" ");
                String IDAsString = unformattedString[unformattedString.length - 1];
                customerID = Long.parseLong(IDAsString);
                customerActivity.putExtra(EXTRA, customerID);
                startActivity(customerActivity);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        update();
        refresh.setOnRefreshListener(() -> {
            order.setChecked(getOrderButtonState());
            update();
            refresh.setRefreshing(false);
        });
        order.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Application application = new Application(context);
            application.updateCustomersOrder(isChecked);
            update();
        });
        order.setChecked(getOrderButtonState());
    }

    public void update(){
        customers = Customers.getCustomers(context);
        CustomerAdapter listAdapter = new CustomerAdapter(context, R.layout.activity_customer_list_item, customers);
        customerListView.setAdapter(listAdapter);
    }
    private boolean getOrderButtonState(){
        Application application = new Application(context);
        return application.getCustomerOrder().equals(Application.ASCENDING);
    }

}