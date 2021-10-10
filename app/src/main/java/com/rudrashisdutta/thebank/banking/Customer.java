package com.rudrashisdutta.thebank.banking;

import android.content.Context;

import com.rudrashisdutta.thebank.database.Customers;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private long customerID = -1;
    private String customerName = null;
    private long DOB = -1;
    private String email = null;
    private long mobileNumber = -1;
    private String PAN = null;
    private String aadhaar = null;
    private double balance = -1;
    private String address = null;

    private Customers customers;

    private static final List<Customer> defaultCustomers = new ArrayList<Customer>(){
        {
//            add(Customer.build());
        }
    };
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

    public long getDOB() {
        return DOB;
    }

    private void setDOB(long DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    private void setMobileNumber(long mobileNumber) {
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

    public static Customer build(long customerID, String customerName, long DOB, String email, long mobileNumber, String PAN, String aadhaar, double balance, String address){
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