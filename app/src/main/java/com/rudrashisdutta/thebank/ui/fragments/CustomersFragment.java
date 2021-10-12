package com.rudrashisdutta.thebank.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rudrashisdutta.thebank.CustomerAdapter;
import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.database.Customers;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView supportActionBar;
    private ListView customerListView;
    private Context context;

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
    // TODO: Rename and change types and number of parameters
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
        supportActionBar.setText("CUSTOMERS");
        supportActionBar.setGravity(Gravity.CENTER_HORIZONTAL);
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
        supportActionBar.setText("CUSTOMERS");
        supportActionBar.setGravity(Gravity.CENTER_HORIZONTAL);
        customerListView = (ListView) this.requireView().findViewById(R.id.list_of_customers);
        update();
    }

    public void update(){
        customers = Customers.getCustomers(context);
        List<String> names = new ArrayList<>();
        for(Customer customer : customers){
            names.add(customer.getCustomerName());
        }
        Log.e("AAAAA", names.toString());
        CustomerAdapter listAdapter = new CustomerAdapter(context, R.layout.activity_customer_list, customers);
        Log.e("AAAAA", listAdapter.getCount()+"");
        customerListView.setAdapter(listAdapter);
        Log.e("AAAAA", listAdapter.getCount()+"");
    }

}