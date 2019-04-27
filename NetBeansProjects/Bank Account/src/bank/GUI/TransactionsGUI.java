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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class TransactionsGUI {

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
        layout.getChildren().addAll(bankName, routingNumber, accountNumber, balance);
        
        TableView<BankAccount> bankTransactions;
        bankTransactions = new TableView();

        //Bank Name column
        TableColumn<BankAccount, String> transactionType = new TableColumn<>("Transaction Type");
        transactionType.setMinWidth(200);
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // Routing Number Column
        TableColumn<BankAccount, Integer> transactionAmount = new TableColumn<>("Transaction Amount");
        transactionAmount.setMinWidth(200);
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));

        //Account Number Column
        TableColumn<BankAccount, Integer> accountNumberClm = new TableColumn<>("Balance");
        accountNumberClm.setMinWidth(200);
        accountNumberClm.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //add columns to table
        bankTransactions.getColumns().addAll(transactionType, transactionAmount, accountNumberClm);
        VBox bankAccountBox = new VBox();
        //Add bank accounts   table to the VBox
        
        
        HBox addTransactionEvent = new HBox();
        TextField transactionTypeField,amountField,balanceField;
        
        transactionTypeField = new TextField();
        transactionTypeField.setPromptText("Transaction Type");
        amountField = new TextField();
        amountField.setPromptText("Amount");
        balanceField = new TextField();
        balanceField.setPromptText("Balance");
        Button btnAddTransaction = new Button("Add Transaction");
        HBox addTransactionHBox = new HBox();
        addTransactionHBox.getChildren().addAll(transactionTypeField,amountField,balanceField,btnAddTransaction);
        
        
        bankAccountBox.getChildren().addAll(layout,bankTransactions,addTransactionHBox);
        Scene scene = new Scene(bankAccountBox);
        window.setScene(scene);
        window.showAndWait();

    }
}
