/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;

import java.util.ArrayList;

/**
 *
 * @author noaha
 */
public class BankAccount extends Bank {

    private int accountNo;
    private double balance;
    private ArrayList<Transaction> transactions;

    public BankAccount(String bankName, int routingNumber, int accountNo, double balance) {
        super(bankName, routingNumber);
        this.accountNo = accountNo;
        this.balance = balance;
        transactions = new ArrayList<Transaction>();
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int AccountNo) {
        this.accountNo = AccountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(int Balance) {
        this.balance = balance;
    }

    public void getTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    @Override
    public String toString() {
        return super.toString() + " Account Number: " + getAccountNo() + " Balance " + getBalance();
    }

}
