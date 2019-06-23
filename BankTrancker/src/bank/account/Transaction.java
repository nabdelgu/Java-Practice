package bank.account;

import bank.GUI.BankAccountMain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noaha
 */
public final class Transaction {

    private int transactionID;
    private String transactionType;
    private double transactionAmount;
    private double balance;

    
    private static final String GET_MAX_TRANSACTION_ID_SQL = "SELECT MAX(TransactionID) as TransactionID FROM TransactionDetails";
    public Transaction(String transactionType, double transactionAmount, double balance, Connection connectToDb) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        setTransactionID(getNextTransactionID(connectToDb));
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        if (transactionType.equals("Withdraw")) {
            this.balance = balance - transactionAmount;
        }

        if (transactionType.equals("Deposit")) {
            this.balance = balance + transactionAmount;
        }
    }
    
        public Transaction(int transactionID,String transactionType, double transactionAmount, double balance) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        if (transactionType.equals("Withdraw")) {
            this.balance = balance - transactionAmount;
        }

        if (transactionType.equals("Deposit")) {
            this.balance = balance + transactionAmount;
        }
    }
    
    

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
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

    public int getNextTransactionID(Connection c) {
        int nextTransactionID = 0;
        try {

 
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(GET_MAX_TRANSACTION_ID_SQL);

            return rs.getInt("TransactionID") + 1;

        } catch (SQLException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextTransactionID;

    }

    @Override
    public String toString() {
        return "TransactionID: " + getTransactionID() + " TransactionType: " + getTranscationType() + " Transaction Amount: " + getTransactionAmount() + " Balance: " + getBalance();
    }

}