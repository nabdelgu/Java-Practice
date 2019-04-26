/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import java.awt.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class TransactionsGUI{
    
    public static void getBankAccount(BankAccount bank) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Transactions");
        window.setMinWidth(250);
        
        Label bankName = new Label();
        bankName.setText("Bank Name: " + bank.getBankName());
        Label routingNumber = new Label();
        routingNumber.setText("Routing Number: " + Integer.toString(bank.getRoutingNumber()));
        Label accountNumber = new Label();
        accountNumber.setText("Account Number: " + Integer.toString(bank.getAccountNo()));
        Label balance = new Label();
        balance.setText("Balance: " + Double.toString(bank.getBalance()));
        
        

        HBox layout = new HBox(30);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.getChildren().addAll(bankName,routingNumber,accountNumber,balance);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }   
}
