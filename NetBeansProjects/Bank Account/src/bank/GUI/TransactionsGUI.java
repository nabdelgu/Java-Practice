/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import bank.account.Transaction;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        // Transaction types 
        final String transactionTypeArray[]
                = {"Withdraw", "Deposit"};

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

        HBox accountInfoHBox = new HBox(30);
        accountInfoHBox.setAlignment(Pos.TOP_CENTER);
        accountInfoHBox.setPadding(new Insets(15, 12, 15, 12));
        accountInfoHBox.getChildren().addAll(bankName, routingNumber, accountNumber, balance);

        TableView<Transaction> bankTransactions;
        bankTransactions = new TableView();
        bankTransactions.setItems(bank.getTransactions());
        //Transaction type
        TableColumn<Transaction, String> transactionType = new TableColumn<>("Transaction Type");
        transactionType.setMinWidth(400);
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // Transaction Amount
        TableColumn<Transaction, Double> transactionAmount = new TableColumn<>("Transaction Amount");
        transactionAmount.setMinWidth(200);
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));

        //Account Number Column
        TableColumn<Transaction, Double> balanceClm = new TableColumn<>("Balance");
        balanceClm.setMinWidth(200);
        balanceClm.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //add columns to table
        bankTransactions.getColumns().addAll(transactionType, transactionAmount, balanceClm);

        VBox bankAccountBox = new VBox();
        //Add bank accounts   table to the VBox

        TextField amountField, balanceField;

        // transactionTypeField = new TextField();
        // transactionTypeField.setPromptText("Transaction Type");     
        // Create a combo box for transaction types
        ComboBox transactionTypeDropdown
                = new ComboBox(FXCollections
                        .observableArrayList(transactionTypeArray));

        amountField = new TextField();
        amountField.setPromptText("Amount");
        balanceField = new TextField();
        balanceField.setPromptText("Balance");
        Button btnAddTransaction = new Button("Add Transaction");
        Button deleteTransaction = new Button("Delete");
        HBox addDeleteTransactionHBox = new HBox();
        addDeleteTransactionHBox.getChildren().addAll(transactionTypeDropdown, amountField, btnAddTransaction, deleteTransaction);

        btnAddTransaction.setOnAction(e -> {

            //get the object selected
            //Transaction t = bankTransactions.getSelectionModel().getSelectedItem();
            //add a transaction to the bank account
            // System.out.println(transactionTypeField.getText());
            // System.out.println(Double.parseDouble(amountField.getText()));
           // System.out.println(transactionTypeDropdown.getValue().toString());
         //   System.out.println(bank.getBalance());
            Transaction t = new Transaction(transactionTypeDropdown.getValue().toString(), Double.parseDouble(amountField.getText()), (bank.getBalance() - Double.parseDouble(amountField.getText())));
            bank.addTransaction(t);
            //set the items on the table
            //  bankTransactions.setItems(bank.getTransactions());
            bank.setBalance(t.getTranscationType(), t.getTransactionAmount());
            balance.setText("Balance: " + bank.getBalance());
            //bankTransactions.setItems(bank.getTransactions());
            e.consume();
        });

        deleteTransaction.setOnAction(e -> {
            //get the bank accounts that are selected
            Transaction t = bankTransactions.getSelectionModel().getSelectedItems().get(0);
            bank.removeTransaction(t);
            //remove bank accounts from the list
            //transactionsSelected.forEach(bank.getTransactions()::remove);
            balance.setText("Balance: " + bank.getBalance());

            e.consume();
        });

        bankAccountBox.getChildren().addAll(accountInfoHBox, bankTransactions, addDeleteTransactionHBox);
        Scene scene = new Scene(bankAccountBox);
        window.setScene(scene);
        window.showAndWait();

    }
}
