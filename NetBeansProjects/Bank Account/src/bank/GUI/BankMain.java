/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import bank.account.Transaction;

/**
 *
 * @author noaha
 */
public class BankMain {

    public static void main(String[] args) {
        BankAccount b = new BankAccount("Chase", 23232, 232323, 10000);
        System.out.println(b);
        b.addTransaction(new Transaction("Deposit", 1000, b.getBalance()));
        b.addTransaction(new Transaction("Withdraw", 2654, b.getBalance()));
        b.addTransaction(new Transaction("Deposit", 536, b.getBalance()));
        b.getTransactions();
    }
}
