package com.db;

import java.sql.*;

/**
 * Creates a connection to the database and handles table creation
 * @author Noah
 * @since 05/12/2019
 * 
 */
public class ConnectToDb {

    private static Connection connection = null;
    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_NAME = "jdbc:sqlite:BankDatabase.db";

    // DDL to create the database tables if they do not exist;
    private static final String CREATE_BANK_ACCOUNT_DDL = "CREATE TABLE IF NOT EXISTS BankAccounts( \n"
            + "    BankName TEXT NOT NULL,\n"
            + "    RoutingNumber INTEGER NOT NULL, \n"
            + "    AccountNumber INTEGER NOT NULL, \n"
            + "    Balance INTEGER NOT NULL,\n"
            + "    PRIMARY KEY(BankName,AccountNumber) \n"
            + ")";
    private static final String CREATE_BANK_TRANSACTION_DDL = "CREATE TABLE IF NOT EXISTS BankTransaction (\n"
            + "    BankName	TEXT NOT NULL,\n"
            + "    AccountNumber	INTEGER NOT NULL,\n"
            + "    TransactionID	INTEGER NOT NULL,\n"
            + "    PRIMARY KEY(BankName,AccountNumber,TransactionID)\n"
            + ")";
    private static final String CREATE_TRANSACTION_DETAILS_DDL = "CREATE TABLE IF NOT EXISTS TransactionDetails (\n"
            + "	TransactionID	INTEGER NOT NULL UNIQUE,\n"
            + "	TransactionType	TEXT NOT NULL,\n"
            + "	TransactionAmount	INTEGER NOT NULL,\n"
            + "	Balance	INTEGER NOT NULL,\n"
            + "	PRIMARY KEY(TransactionID)\n"
            + ")";

    /*public static void main(String[] args) {
        
        try {
            
            connection = ConnectToDb.getConnection();
            System.out.println("Connection sucessful");
            String SQLAdd = "insert into"
                    + " BankAccounts(BankName,RoutingNumber,AccountNumber,Balance) "
                    + "values('TestBank',546566,234324,1523)";
            Statement stmt = connection.createStatement();
            
            stmt.executeUpdate(SQLAdd);
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
            System.exit(0);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConnectToDb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnectToDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
    /**
     * 
     * Creates and returns a database connection
     * 
     * @return Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName(DATABASE_DRIVER);
        connection = DriverManager.getConnection(DATABASE_NAME);
        connection.setAutoCommit(true);
        return connection;
    }

    /**
     * 
     * Creates the database tables if they do not already exist
     * 
     * @param c
     * @throws SQLException 
     */
    public static void createTablesIfNotExists(Connection c) throws SQLException {
        Statement stmt = connection.createStatement();
        //Create BankAccount table
        stmt.executeUpdate(CREATE_BANK_ACCOUNT_DDL);
        //Create BankTransactions table
        stmt.executeUpdate(CREATE_BANK_TRANSACTION_DDL);
        //Create TranscationDetails table
        stmt.executeUpdate(CREATE_TRANSACTION_DETAILS_DDL);
    }

}
