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

    public static void main(String[] args) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BankDatabase.db");
            System.out.println("Connect successfully");
            String SQLAdd;
            SQLAdd = "insert into"
                    + " BankAccounts(BankName,RoutingNumber,AccountNumber,Balance) "
                    + "values('TestBank','546565','234324','1523')";
            Statement stmt = c.createStatement();

            //stmt.executeUpdate(SQLAdd);
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
            System.exit(0);
        }

    }
}
