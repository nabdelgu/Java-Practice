package bank.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Concrete class that extends the abstract class Bank
 *
 * @author noaha
 * @since 005/12/2019
 */
public class BankAccount extends Bank {

    private int accountNo;
    private double balance;
    //Holds the BankAccounts transactions
    private ObservableList<Transaction> transactions;

    /**
     * SQL to handle the updates,insertions and deletions to the database for
     * transactions and bank accounts
     */
    private static final String UPDATE_BANK_ACCOUNT_BALANCE_SQL = "UPDATE BankAccounts SET Balance = ? WHERE BankName = ? and AccountNumber = ?";
    private static final String DELETE_TRANSACTION_DETAILS_SQL = "DELETE FROM TransactionDetails WHERE TransactionID = ?";
    private static final String DELETE_BANK_TRANSACTION_DETAILS_SQL = "DELETE FROM BankTransaction WHERE BankName = ? and AccountNumber = ? and TransactionID = ?";
    private static final String INSERT_BANK_ACCOUNT_SQL = "INSERT INTO BankAccounts(BankName,RoutingNumber,AccountNumber,Balance) VALUES(?,?,?,?)";
    private static final String INSERT_BANK_TRANSACTION_SQL = "INSERT INTO BankTransaction(BankName,AccountNumber,TransactionID) VALUES(?,?,?)";
    private static final String INSERT_TRANSACTION_DETAILS_SQL = "INSERT INTO TransactionDetails(TransactionID,TransactionType,TransactionAmount,Balance) VALUES(?,?,?,?)";

    /**
     *
     * Constructor called when a new value is being added into the database
     *
     * @param bankName
     * @param routingNumber
     * @param accountNo
     * @param balance
     * @param connectionToDB
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public BankAccount(String bankName, int routingNumber, int accountNo, double balance, Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(bankName, routingNumber);
        this.accountNo = accountNo;
        this.balance = balance;
        transactions = FXCollections.observableArrayList();

        PreparedStatement addBank = connectionToDB.prepareStatement(INSERT_BANK_ACCOUNT_SQL);
        addBank.setString(1, bankName);
        addBank.setInt(2, routingNumber);
        addBank.setInt(3, accountNo);
        addBank.setDouble(4, balance);
        addBank.executeUpdate();
    }

    /**
     * Constructor called when an existing value is being loaded in upon
     * application start
     *
     * @param bankName
     * @param routingNumber
     * @param accountNo
     * @param balance
     */
    public BankAccount(String bankName, int routingNumber, int accountNo, double balance) {
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

    /**
     * '
     *
     * Updates the bank accounts balance every time a new transaction occurs
     *
     * @param transactionType
     * @param transactionAmount
     * @param connectionToDB
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setBalance(String transactionType, double transactionAmount, Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
        return transactions;
    }

    /**
     *
     * Called when a transaction is removed from a bank account
     *
     * @param transaction
     * @param connectionToDB
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void removeTransaction(Transaction transaction, Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //delete from BankTransction table
        final PreparedStatement deleteBankTransaction = connectionToDB.prepareStatement(DELETE_BANK_TRANSACTION_DETAILS_SQL);
        deleteBankTransaction.setString(1, getBankName());
        deleteBankTransaction.setInt(2, getAccountNo());
        deleteBankTransaction.setInt(3, transaction.getTransactionID());
        deleteBankTransaction.executeUpdate();

        //delete from TransactionDetails table
        final PreparedStatement deleteBankTransactionDetails = connectionToDB.prepareStatement(DELETE_TRANSACTION_DETAILS_SQL);
        deleteBankTransactionDetails.setInt(1, transaction.getTransactionID());
        deleteBankTransactionDetails.executeUpdate();

        //set the new balance after transaction is removed
        if (transaction.getTransactionType().equals(TransactionType.Withdraw.getDescription())) {
            this.balance = this.balance + transaction.getTransactionAmount();
        } else if (transaction.getTransactionType().equals(TransactionType.Deposit.getDescription())) {
            this.balance = this.balance - transaction.getTransactionAmount();
        }

        //update the banks balance on the BankAccounts table
        final PreparedStatement updateBankAccountBalance = connectionToDB.prepareStatement(UPDATE_BANK_ACCOUNT_BALANCE_SQL);
        updateBankAccountBalance.setDouble(1, getBalance());
        updateBankAccountBalance.setString(2, getBankName());
        updateBankAccountBalance.setInt(3, getAccountNo());
        updateBankAccountBalance.executeUpdate();

        // remove the transaction from the list
        transactions.remove(transaction);

    }

    public void setTransactions(ObservableList<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * 
     * Called when a new transaction is added it handles writing the the database
     * 
     * @param transaction
     * @param connectionToDB
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void addTransaction(Transaction transaction, Connection connectionToDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //write transcation to BankTransaction table
        final PreparedStatement addBankTransaction = connectionToDB.prepareStatement(INSERT_BANK_TRANSACTION_SQL);
        addBankTransaction.setString(1, getBankName());
        addBankTransaction.setInt(2, getAccountNo());
        addBankTransaction.setInt(3, transaction.getTransactionID());
        addBankTransaction.executeUpdate();

        //write Transaction to TransactionDetails table
        final PreparedStatement addTransactionDetails = connectionToDB.prepareStatement(INSERT_TRANSACTION_DETAILS_SQL);
        addTransactionDetails.setInt(1, transaction.getTransactionID());
        addTransactionDetails.setString(2, transaction.getTransactionType());
        addTransactionDetails.setDouble(3, transaction.getTransactionAmount());
        addTransactionDetails.setDouble(4, transaction.getBalance());
        addTransactionDetails.executeUpdate();
        transactions.add(transaction);
    }

    
    /**
     * Called when application start to load the transactions
     * @param t 
     */
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    @Override
    public String toString() {
        return super.toString() + " Account Number: " + getAccountNo() + " Balance " + getBalance();
    }

}
