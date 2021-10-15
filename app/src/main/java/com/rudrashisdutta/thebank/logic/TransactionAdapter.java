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
import com.rudrashisdutta.thebank.banking.Transaction;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private final List<Transaction> transactions;
    private final Context context;

    public TransactionAdapter(@NonNull Context context, int resource, List<Transaction> transactions) {
        super(context, resource);
        this.transactions = transactions;
        this.context = context;
    }

    static class TransactionListViewHolder{
        TextView transactionID;
        TextView senderID_And_ReceiverID;
        TextView amount;
        TextView date;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Nullable
    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TransactionListViewHolder transactionListViewHolder = new TransactionListViewHolder();
        View list_item = inflater.inflate(R.layout.activity_customer_list_item, parent, false);
//        transactionListViewHolder.transactionID = Objects.requireNonNull(list_item).findViewById(R.id.balance);
//        transactionListViewHolder.senderID_And_ReceiverID = Objects.requireNonNull(list_item).findViewById(R.id.customer_name);
//        transactionListViewHolder.amount = Objects.requireNonNull(list_item).findViewById(R.id.balance);
//        transactionListViewHolder.date = Objects.requireNonNull(list_item).findViewById(R.id.balance);
//
//        list_item.setTag(transactionListViewHolder);
//        transactionListViewHolder.transactionID.setText(transactions.get(position).getTransactionID());
//        transactionListViewHolder.senderID_And_ReceiverID.setText(String.format(context.getString(R.string.account_id_textview_format), customers.get(position).getCustomerID()));
//        transactionListViewHolder.amount.setText(String.format(context.getString(R.string.balance_textview_format), customers.get(position).getBalance()));
//        transactionListViewHolder.date.setText(String.format(context.getString(R.string.balance_textview_format), customers.get(position).getBalance()));
        return list_item;
    }
    //TODO Create the 'activity_transaction_list_item' and everything needed to set up line 57 to 66

}
