/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;

/**
 *
 * @author noaha
 */
public class Transaction {
    private String transactionType;
    private double transactionAmount;
    private double balance;

    public Transaction(String transactionType, double transactionAmount,double balance) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        if (transactionType.equals("Withdraw")) {
            this.balance = balance - transactionAmount;
        }
        
        if (transactionType.equals("Deposit")) {
            this.balance = balance + transactionAmount;
        }
    }
    
    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTranscationType() {
        return transactionType;
    }

    public void setTranscationType(String transcationType) {
        this.transactionType = transcationType;
    }

    public double getBalance() {
        return balance;
    }

    public void setFinalBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "TransactionType: " +  getTranscationType() + " Transaction Amount: " + getTransactionAmount() + " Balance: "+ getBalance();
    }  
    
    
}
