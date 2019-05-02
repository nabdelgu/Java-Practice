/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author noaha
 */
public class BankAccount extends Bank {

    private int accountNo;
    private double balance;
    //private ArrayList<Transaction> transactions;
    private ObservableList<Transaction> transactions;

    public BankAccount(String bankName, int routingNumber, int accountNo, double balance) throws NumberFormatException {
        super(bankName, routingNumber);
        this.accountNo = accountNo;
        this.balance = balance;
        transactions = FXCollections.observableArrayList();
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

    public void setBalance(String transactionType,double transactionAmount) {
        if (transactionType.equals(TransactionType.Withdraw.getDescription())) {
            this.balance = balance - transactionAmount;
        } else if (transactionType.equals(TransactionType.Deposit.getDescription())) {
            this.balance = balance + transactionAmount;
        }
    }

    public ObservableList getTransactions() {
        /*for (Transaction t : transactions) {
            System.out.println(t);
        }*/
        return transactions;
    }
    
    public void removeTransaction(Transaction t){
        if(t.getTransactionType().equals(TransactionType.Withdraw.getDescription())){
            this.balance = this.balance + t.getTransactionAmount();
        }else if(t.getTransactionType().equals(TransactionType.Deposit.getDescription())){
            this.balance = this.balance - t.getTransactionAmount();
        }
        transactions.remove(t);
        
    }
    

     public void setTransactions(ObservableList<Transaction> transactions) {
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
