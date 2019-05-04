/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

import java.sql.*;

/**
 *
 * @author Noah
 */
public class ConnectToDb {

    private static Connection connection = null;
    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_NAME = "jdbc:sqlite:BankDatabase.db";

    public static void main(String[] args) {

        try {

            connection = ConnectToDb.getConnection();
            connection.close();
            System.out.println("Connection sucessful");
            /*String SQLAdd;
            SQLAdd = "insert into"
                    + " BankAccounts(BankName,RoutingNumber,AccountNumber,Balance) "
                    + "values('TestBank','546565','234324','1523')";
            Statement stmt = connection.createStatement();*/

            //stmt.executeUpdate(SQLAdd);
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
            System.exit(0);
        }

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DATABASE_DRIVER);
        connection = DriverManager.getConnection(DATABASE_NAME);

        return connection;
    }

}
