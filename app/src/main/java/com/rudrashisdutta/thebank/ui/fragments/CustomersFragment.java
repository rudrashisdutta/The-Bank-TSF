package com.rudrashisdutta.thebank.ui.fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudrashisdutta.thebank.R;

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

    public CustomersFragment(TextView supportActionBar) {
        // Required empty public constructor
        this.supportActionBar = supportActionBar;
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
    public static CustomersFragment newInstance(String param1, String param2, TextView supportActionBar) {
        CustomersFragment fragment = new CustomersFragment(supportActionBar);
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
    public void onResume() {
        super.onResume();
        supportActionBar.setText("CUSTOMERS");
        supportActionBar.setGravity(Gravity.CENTER_HORIZONTAL);
    }

}