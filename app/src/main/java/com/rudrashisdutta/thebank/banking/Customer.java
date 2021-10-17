package com.rudrashisdutta.thebank.banking;

import android.annotation.SuppressLint;
import android.content.Context;

import com.rudrashisdutta.thebank.database.Customers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private long customerID = -1;
    private String customerName = null;
    private String DOB = null;
    private String email = null;
    private String mobileNumber = null;
    private String PAN = null;
    private String aadhaar = null;
    private double balance = -1;
    private String address = null;

    private Customers customers;

    private static final List<Customer> defaultCustomers = new ArrayList<Customer>(){
        {
            add(Customer.build(100291, "Rudrashis Kumar Dutta", getDate("2001-08-25"), "1906354@kiit.ac.in", "1234239078", "JHV2819Q123", "516273839483", 50000.10, "BK Road, Cuttack, Odisha"));
            add(Customer.build(100292, "Akira", getDate("2001-11-28"), "1905330@kiit.ac.in", "8374628472", "ABH4318J909", "516221232345", 50000.10, "Kargil Road, Bhubaneswar, Odisha"));
            add(Customer.build(100293, "Rajesh Kumar", getDate("1995-12-05"), "rjk.95@gmail.com", "7878635472", "QER4334D455", "516726350918", 50000.10, "Buxi Bazar, Cuttack, Odisha"));
            add(Customer.build(100294, "Suresh Kumar", getDate("1959-05-12"), "sur.k@gmail.com", "2785477863", "ERQ4345534D", "516721982647", 50000.10, "Badambadi, Cuttack, Odisha"));
            add(Customer.build(100295, "R. Ashok", getDate("1992-09-30"), "ashok.r@gmail.com", "1247727863", "WEE55RQ434D", "578147262619", 50000.10, "Dolmundai, Cuttack, Odisha"));
            add(Customer.build(100296, "Subhas Agarwal", getDate("1991-12-13"), "subhas.ag@yahoo.com", "7867234363", "WSQE5434E5D", "419725781626", 50000.10, "Dolmundai, Cuttack, Odisha"));
            add(Customer.build(100297, "Vinay Agarwal", getDate("1980-01-01"), "vinay.ad@yahoo.com", "7333472686", "W5DSQ434E5E", "425719781626", 50000.10, "Dolmundai, Cuttack, Odisha"));
            add(Customer.build(100298, "Dev Nanda", getDate("1985-04-21"), "nanda.d@yahoo.com", "3386477326", "SQ434E5E15D", "514262671978", 50000.10, "Ranihat Colony, Cuttack, Odisha"));
            add(Customer.build(100299, "Gaurav Nanda", getDate("1985-04-21"), "nanda.g@yahoo.com", "3386264773", "SQ434E5B01A", "514262671676", 50000.10, "Ranihat Colony, Cuttack, Odisha"));
            add(Customer.build(100300, "S. Raghav", getDate("1989-08-26"), "raghav.2@hotmail.com", "3862634773", "A43SQB4E51A", "262675141676", 50000.10, "Manglabag, Cuttack, Odisha"));
        }
    };
    @SuppressLint("SimpleDateFormat")
    private static String getDate(String source) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return String.valueOf(Objects.requireNonNull(dateFormat.parse(source)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(0);
    }
    public static List<Customer> getDefaultCustomers() {
        return defaultCustomers;
    }

    public long getCustomerID() {
        return customerID;
    }

    private void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    private void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDOB() {
        return DOB;
    }

    private void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    private void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPAN() {
        return PAN;
    }

    private void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    private void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public static Customer build(long customerID, String customerName, String DOB, String email, String mobileNumber, String PAN, String aadhaar, double balance, String address){
        Customer customer = new Customer();
        customer.setCustomerID(customerID);
        customer.setCustomerName(customerName);
        customer.setDOB(DOB);
        customer.setEmail(email);
        customer.setMobileNumber(mobileNumber);
        customer.setPAN(PAN);
        customer.setAadhaar(aadhaar);
        customer.setBalance(balance);
        customer.setAddress(address);
        return customer;
    }
    public static Customer build(Customer customer, double newBalance){
        customer.setBalance(newBalance);
        return customer;
    }

    public static Customer get(Context context, long customerID){
        return Customers.get(context, customerID);
    }
}