/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import bank.account.Transaction;
import bank.account.TransactionType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Created to handle BankTransaction
 *
 * @author Noah
 * @since 05/12/2019
 *
 */
public class TransactionsGUI {

    private static Stage bankTransctionStage;
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

    /**
     *
     * Handles the adding and deletion of new bank transactions
     *
     * @param bank
     * @param connectionToDB
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    public static void getBankAccount(BankAccount bank, Connection connectionToDB) throws NumberFormatException, NullPointerException {
        //create the stage and set its properties
        bankTransctionStage = new Stage();
        bankTransctionStage.initModality(Modality.APPLICATION_MODAL);
        bankTransctionStage.setTitle("Transactions");
        bankTransctionStage.setMinWidth(250);

        // fill labels with the currect bank information
        bankName = new Label();
        bankName.setText("Bank Name: " + bank.getBankName());
        routingNumber = new Label();
        routingNumber.setText("Routing Number: " + Integer.toString(bank.getRoutingNumber()));
        accountNumber = new Label();
        accountNumber.setText("Account Number: " + Integer.toString(bank.getAccountNo()));
        balance = new Label();
        balance.setText("Balance: " + Double.toString(bank.getBalance()));

        //create a HBOX to store the bank information labels
        accountInfoHBox = new HBox(30);
        accountInfoHBox.setAlignment(Pos.TOP_CENTER);
        accountInfoHBox.setPadding(new Insets(15, 12, 15, 12));
        accountInfoHBox.getChildren().addAll(bankName, routingNumber, accountNumber, balance);

        // create a TableView to store the bank transactions
        bankTransactions = new TableView();
        bankTransactions.setItems(bank.getTransactions());

        // define table columns     
        transactionType = new TableColumn<>("Transaction Type");
        transactionType.setMinWidth(400);
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        transactionAmount = new TableColumn<>("Transaction Amount");
        transactionAmount.setMinWidth(200);
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));

        balanceClm = new TableColumn<>("Balance");
        balanceClm.setMinWidth(200);
        balanceClm.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //add columns to table
        bankTransactions.getColumns().addAll(transactionType, transactionAmount, balanceClm);

        //Add bank accounts table to the VBox
        bankAccountBox = new VBox();
        transactionTypeDropdown = new ComboBox(FXCollections.observableArrayList(TransactionType.getArrayTransaction()));

        // Create text fields to input the transaction info
        amountField = new TextField();
        amountField.setPromptText("Amount");
        balanceField = new TextField();
        balanceField.setPromptText("Balance");
        btnAddTransaction = new Button("Add Transaction");
        deleteTransaction = new Button("Delete");
        addDeleteTransactionHBox = new HBox();
        addDeleteTransactionHBox.getChildren().addAll(transactionTypeDropdown, amountField, btnAddTransaction, deleteTransaction);

        // Handles the addition of a new transaction
        btnAddTransaction.setOnAction(e -> {
            try {
                // set the new bank balance
                bank.setBalance(transactionTypeDropdown.getValue().toString(), Double.parseDouble(amountField.getText()), connectionToDB);

                //add the new transaction to the bank
                bank.addTransaction(new Transaction(transactionTypeDropdown.getValue().toString(), Double.parseDouble(amountField.getText()), bank.getBalance(), connectionToDB), connectionToDB);
                //set the new bank balance to the label
                balance.setText("Balance: " + bank.getBalance());
            } catch (NumberFormatException ex) {
                if (transactionTypeDropdown.getValue().toString().equals("") || amountField.getText().equals("")) {
                    AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
                } else {
                    AlertError.displayError("Error", "Text entered where a number was expected", Alert.AlertType.ERROR);
                }

            } catch (NullPointerException ex) {
                AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
            } catch (SQLException ex) {
                System.out.println("SQL Exception");
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found exception");
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(TransactionsGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // Handles the deletion of transctions
        deleteTransaction.setOnAction(e -> {
            try {
                //get the bank accounts that are selected
                transaction = bankTransactions.getSelectionModel().getSelectedItems().get(0);
                //remove bank accounts from the list
                bank.removeTransaction(transaction, connectionToDB);
                // set the new bank balance to the label
                balance.setText("Balance: " + bank.getBalance());
            } catch (NullPointerException ex) {
                // catch null pointer if nothing is selected
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(TransactionsGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // All items are added to a VBox
        bankAccountBox.getChildren().addAll(accountInfoHBox, bankTransactions, addDeleteTransactionHBox);
        transactionScene = new Scene(bankAccountBox);
        bankTransctionStage.setScene(transactionScene);
        bankTransctionStage.showAndWait();
    }
}
