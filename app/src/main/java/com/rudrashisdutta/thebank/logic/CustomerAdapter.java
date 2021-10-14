package com.rudrashisdutta.thebank.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rudrashisdutta.thebank.R;
import com.rudrashisdutta.thebank.banking.Customer;

import java.util.List;
import java.util.Objects;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private final List<Customer> customers;
    private final Context context;

    public CustomerAdapter(@NonNull Context context, int resource, List<Customer> customers) {
        super(context, resource);
        this.customers = customers;
        this.context = context;
    }

    static class CustomerListViewHolder{
        TextView customerName;
        TextView accountId;
        TextView balance;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Nullable
    @Override
    public Customer getItem(int position) {
        return customers.get(position);
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CustomerListViewHolder customerListViewHolder = new CustomerListViewHolder();
        View list_item = inflater.inflate(R.layout.activity_customer_list, parent, false);
        customerListViewHolder.customerName = Objects.requireNonNull(list_item).findViewById(R.id.customer_name);
        customerListViewHolder.accountId = Objects.requireNonNull(list_item).findViewById(R.id.account_id);
        customerListViewHolder.balance = Objects.requireNonNull(list_item).findViewById(R.id.balance);

        list_item.setTag(customerListViewHolder);
        customerListViewHolder.customerName.setText(customers.get(position).getCustomerName());
        customerListViewHolder.accountId.setText(String.format(context.getString(R.string.account_id_textview_format), customers.get(position).getCustomerID()));
        customerListViewHolder.balance.setText(String.format(context.getString(R.string.balance_textview_format), customers.get(position).getBalance()));
        return list_item;
    }
}