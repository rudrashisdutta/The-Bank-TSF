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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private final List<Transaction> transactions;
    private final Context context;
    private final int resource;

    public TransactionAdapter(@NonNull Context context, int resource, List<Transaction> transactions) {
        super(context, resource);
        this.transactions = transactions;
        this.context = context;
        this.resource = resource;
    }

    public static class TransactionListViewHolder{
        TextView transactionID;
        TextView senderName_And_ReceiverName;
        TextView amount;
        TextView date;

        public TextView getTransactionID() {
            return transactionID;
        }
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
        View list_item = inflater.inflate(resource, parent, false);
        transactionListViewHolder.transactionID = Objects.requireNonNull(list_item).findViewById(R.id.transactionID);
        transactionListViewHolder.senderName_And_ReceiverName = Objects.requireNonNull(list_item).findViewById(R.id.transactionInfo);
        transactionListViewHolder.amount = Objects.requireNonNull(list_item).findViewById(R.id.amount);
        transactionListViewHolder.date = Objects.requireNonNull(list_item).findViewById(R.id.date);

        list_item.setTag(transactionListViewHolder);
        transactionListViewHolder.transactionID.setText(transactions.get(position).getTransactionID());
        transactionListViewHolder.senderName_And_ReceiverName.setText(String.format(context.getString(R.string.transaction_info), transactions.get(position).getCustomer().getCustomerName(), transactions.get(position).getReceiver().getCustomerName()));
        transactionListViewHolder.amount.setText(String.format(context.getString(R.string.balance_textview_format), transactions.get(position).getAmount()));
        Date date = new Date(transactions.get(position).getTransactionTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss.SSS");
        String dateAsString = dateFormat.format(date);
        transactionListViewHolder.date.setText(String.format(context.getString(R.string.date), dateAsString));
        return list_item;
    }

}
