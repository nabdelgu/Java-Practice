/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import bank.account.Transaction;
import bank.account.TransactionType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private static Stage window;
    private static Scene transactionScene;
    private static Label bankName, routingNumber, accountNumber, balance;
    private static HBox accountInfoHBox, addDeleteTransactionHBox;
    private static VBox bankAccountBox;
    private static TableColumn<Transaction, String> transactionType, transactionAmount, balanceClm;
    private static TableView<Transaction> bankTransactions;
    private static TextField amountField, balanceField;
    private static ComboBox transactionTypeDropdown;
    private static Button btnAddTransaction, deleteTransaction;
    private static Transaction transaction;

    public static void getBankAccount(BankAccount bank) throws NumberFormatException,NullPointerException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Transactions");
        window.setMinWidth(250);

        // fill in bank information
        bankName = new Label();
        bankName.setText("Bank Name: " + bank.getBankName());
        routingNumber = new Label();
        routingNumber.setText("Routing Number: " + Integer.toString(bank.getRoutingNumber()));
        accountNumber = new Label();
        accountNumber.setText("Account Number: " + Integer.toString(bank.getAccountNo()));
        balance = new Label();
        balance.setText("Balance: " + Double.toString(bank.getBalance()));

        accountInfoHBox = new HBox(30);
        accountInfoHBox.setAlignment(Pos.TOP_CENTER);
        accountInfoHBox.setPadding(new Insets(15, 12, 15, 12));
        accountInfoHBox.getChildren().addAll(bankName, routingNumber, accountNumber, balance);

        bankTransactions = new TableView();
        bankTransactions.setItems(bank.getTransactions());
        // define table columns
        //Transaction type
        transactionType = new TableColumn<>("Transaction Type");
        transactionType.setMinWidth(400);
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // Transaction Amount
        transactionAmount = new TableColumn<>("Transaction Amount");
        transactionAmount.setMinWidth(200);
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));

        //Account Number Column
        balanceClm = new TableColumn<>("Balance");
        balanceClm.setMinWidth(200);
        balanceClm.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //add columns to table
        bankTransactions.getColumns().addAll(transactionType, transactionAmount, balanceClm);

        bankAccountBox = new VBox();
        //Add bank accounts table to the VBox
        transactionTypeDropdown = new ComboBox(FXCollections.observableArrayList(TransactionType.getArrayTransaction()));

        amountField = new TextField();
        amountField.setPromptText("Amount");
        balanceField = new TextField();
        balanceField.setPromptText("Balance");
        btnAddTransaction = new Button("Add Transaction");
        deleteTransaction = new Button("Delete");
        addDeleteTransactionHBox = new HBox();
        addDeleteTransactionHBox.getChildren().addAll(transactionTypeDropdown, amountField, btnAddTransaction, deleteTransaction);
        // add transaction button
        btnAddTransaction.setOnAction(e -> {

            try {
                transaction = new Transaction(transactionTypeDropdown.getValue().toString(), Double.parseDouble(amountField.getText()), (bank.getBalance() - Double.parseDouble(amountField.getText())));
                bank.addTransaction(transaction);
                //set the items on the table
                bank.setBalance(transaction.getTransactionType(), transaction.getTransactionAmount());
                balance.setText("Balance: " + bank.getBalance());
            } catch (NumberFormatException ex) {
                if (transactionTypeDropdown.getValue().toString().equals("") || amountField.getText().equals("")) {
                    AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
                } else {
                    AlertError.displayError("Error", "Text entered where a number was expected", Alert.AlertType.ERROR);
                }

            }catch(NullPointerException ex){
                 AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
            }

        });
        //delete transaction button
        deleteTransaction.setOnAction(e -> {
            try{
                            //get the bank accounts that are selected
            transaction = bankTransactions.getSelectionModel().getSelectedItems().get(0);
            bank.removeTransaction(transaction);
            //remove bank accounts from the list
            balance.setText("Balance: " + bank.getBalance());
            }catch(NullPointerException ex){
                // catch null pointer if nothing is selected
            }
        });
        // add all item to a VBOX
        bankAccountBox.getChildren().addAll(accountInfoHBox, bankTransactions, addDeleteTransactionHBox);
        transactionScene = new Scene(bankAccountBox);
        window.setScene(transactionScene);
        window.showAndWait();
    }
}
