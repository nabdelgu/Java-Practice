/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    private static final String UPDATE_BANK_ACCOUNT_BALANCE_SQL = "UPDATE BankAccounts SET Balance = ? WHERE BankName = ? and AccountNumber = ?";
    private static final String DELETE_TRANSACTION_DETAILS_SQL = "DELETE FROM TransactionDetails WHERE TransactionID = ?";
    private static final String DELETE_BANK_TRANSACTION_DETAILS_SQL = "DELETE FROM BankTransaction WHERE BankName = ? and AccountNumber = ? and TransactionID = ?";
    private static final String INSERT_BANK_ACCOUNT_SQL = "INSERT INTO BankAccounts(BankName,RoutingNumber,AccountNumber,Balance) VALUES(?,?,?,?)";
    private static final String INSERT_BANK_TRANSACTION_SQL = "INSERT INTO BankTransaction(BankName,AccountNumber,TransactionID) VALUES(?,?,?)";
    private static final String INSERT_TRANSACTION_DETAILS_SQL = "INSERT INTO TransactionDetails(TransactionID,TransactionType,TransactionAmount,Balance) VALUES(?,?,?,?)";

    public BankAccount(String bankName, int routingNumber, int accountNo, double balance, boolean newValue, Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(bankName, routingNumber);
        this.accountNo = accountNo;
        this.balance = balance;
        transactions = FXCollections.observableArrayList();
        if (newValue) {
                PreparedStatement addBank = connectionToDB.prepareStatement(INSERT_BANK_ACCOUNT_SQL);
                addBank.setString(1, bankName);
                addBank.setInt(2, routingNumber);
                addBank.setInt(3, accountNo);
                addBank.setDouble(4, balance);
                addBank.executeUpdate();
        }

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

    public void setBalance(String transactionType, double transactionAmount,Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (transactionType.equals(TransactionType.Withdraw.getDescription())) {
            this.balance = balance - transactionAmount;
        } else if (transactionType.equals(TransactionType.Deposit.getDescription())) {
            this.balance = balance + transactionAmount;
        }
        final PreparedStatement updateBankAccountBalance = connectionToDB.prepareStatement(UPDATE_BANK_ACCOUNT_BALANCE_SQL);
        updateBankAccountBalance.setDouble(1, getBalance());
        updateBankAccountBalance.setString(2, getBankName());
        updateBankAccountBalance.setInt(3, getAccountNo());
        updateBankAccountBalance.executeUpdate();

    }

    public ObservableList getTransactions() {
        /*for (Transaction t : transactions) {
            System.out.println(t);
        }*/
        return transactions;
    }

    public void removeTransaction(Transaction t,Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //delete from BankTransction table
        final PreparedStatement deleteBankTransaction = connectionToDB.prepareStatement(DELETE_BANK_TRANSACTION_DETAILS_SQL);
        deleteBankTransaction.setString(1, getBankName());
        deleteBankTransaction.setInt(2, getAccountNo());
        deleteBankTransaction.setInt(3, t.getTransactionID());
        deleteBankTransaction.executeUpdate();

        //delete from BankTransction table
        final PreparedStatement deleteBankTransactionDetails = connectionToDB.prepareStatement(DELETE_TRANSACTION_DETAILS_SQL);
        deleteBankTransactionDetails.setInt(1, t.getTransactionID());
        deleteBankTransactionDetails.executeUpdate();

        //set the new balance after transaction is removed
        if (t.getTransactionType().equals(TransactionType.Withdraw.getDescription())) {
            this.balance = this.balance + t.getTransactionAmount();
        } else if (t.getTransactionType().equals(TransactionType.Deposit.getDescription())) {
            this.balance = this.balance - t.getTransactionAmount();
        }
        //update the banks balance

        final PreparedStatement updateBankAccountBalance = connectionToDB.prepareStatement(UPDATE_BANK_ACCOUNT_BALANCE_SQL);
        updateBankAccountBalance.setDouble(1, getBalance());
        updateBankAccountBalance.setString(2, getBankName());
        updateBankAccountBalance.setInt(3, getAccountNo());
        updateBankAccountBalance.executeUpdate();

        transactions.remove(t);

    }

    public void setTransactions(ObservableList<Transaction> transactions) {
        this.transactions = transactions;
    }
// add a new transaction write to database
    public void addTransaction(Transaction t,Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //write transcation to BankTransaction table
        final PreparedStatement addBankTransaction = connectionToDB.prepareStatement(INSERT_BANK_TRANSACTION_SQL);
        addBankTransaction.setString(1, getBankName());
        addBankTransaction.setInt(2, getAccountNo());
        addBankTransaction.setInt(3, t.getTransactionID());
        addBankTransaction.executeUpdate();

        //write Transaction to TransactionDetails table
        final PreparedStatement addTransactionDetails = connectionToDB.prepareStatement(INSERT_TRANSACTION_DETAILS_SQL);
        addTransactionDetails.setInt(1, t.getTransactionID());
        addTransactionDetails.setString(2, t.getTransactionType());
        addTransactionDetails.setDouble(3, t.getTransactionAmount());
        addTransactionDetails.setDouble(4, t.getBalance());
        addTransactionDetails.executeUpdate();
        transactions.add(t);
    }
    //load existing transaction from database
    public void addTransaction(Transaction t){
         transactions.add(t);
    }

    @Override
    public String toString() {
        return super.toString() + " Account Number: " + getAccountNo() + " Balance " + getBalance();
    }

}
