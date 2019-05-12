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
 * @since 05/12/2019
 */
public final class Transaction {

    private int transactionID;
    private String transactionType;
    private double transactionAmount;
    private double balance;

    /**
     *
     * Constructor when a new transaction is created
     *
     * @param transactionType
     * @param transactionAmount
     * @param balance
     * @param connectionToDb
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Transaction(String transactionType, double transactionAmount, double balance, Connection connectionToDb) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.transactionID = getNextTransactionID(connectionToDb);
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.balance = balance;
    }

    /**
     * Constructor called transactions are loaded in from the database
     * @param transactionID
     * @param transactionType
     * @param transactionAmount
     * @param balance
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public Transaction(int transactionID, String transactionType, double transactionAmount, double balance) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.balance = balance;
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

    /**
     * 
     * Gets the next TransactionID
     * 
     * @param connectionToDb
     * @return int
     */
    public static int getNextTransactionID(Connection connectionToDb) {
        int nextTransactionID = 0;
        try {

            String maxTransactionNumber = "SELECT MAX(TransactionID) AS TransactionID FROM TransactionDetails";
            Statement stmt = connectionToDb.createStatement();
            ResultSet rs = stmt.executeQuery(maxTransactionNumber);

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
