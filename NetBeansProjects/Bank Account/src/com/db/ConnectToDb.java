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
    public static void main(String [] args){
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BankDatabse.db");
            System.out.println("Connect successfully");
        }catch(Exception ex){
            System.out.println("Connection failed");
            System.exit(0);
        }
    }
}
