/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.GUI;

import bank.account.BankAccount;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class BankAccountMain extends Application {

    private static Label bankNameLbl, routingNumberLbl, accoutNumberLbl, balanceLbl;
    private static TextField bankName, routingNumber, accountNumber, balance;
    private static Button btn, btnAddBankAccount, btnViewBankAccount;
    private static GridPane grid;
    private static Scene addBankAccount, homeScene, bankAccountListing;
    private static VBox layout, bankAccountBox;
    private static List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
    private static TableView<BankAccount> bankAccountsTable;
    private ObservableList<BankAccount> bankAccountObsList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        Stage bankAccountsStage = new Stage();
        bankAccountsStage.setTitle("Bank Account List");
        btnAddBankAccount = new Button("Add Bank Account");
        btnViewBankAccount = new Button("View Bank Accounts");
        layout = new VBox();
        layout.setSpacing(50);
        layout.getChildren().addAll(btnAddBankAccount, btnViewBankAccount);
        homeScene = new Scene(layout, 300, 250);
        window.setTitle("Bank Home");
        window.setScene(homeScene);
        window.show();

        btnAddBankAccount.setOnAction(e -> {
            window.setScene(addBankAccount);
            window.show();
        });

        btnViewBankAccount.setOnAction(e -> {
            bankAccountsStage.setScene(bankAccountListing);
            bankAccountsTable.setItems(bankAccountObsList);
            bankAccountsStage.show();
        });

        addBankAccount = new Scene(new Group(), 450, 250);

        bankName = new TextField();
        routingNumber = new TextField();
        accountNumber = new TextField();
        balance = new TextField();

        grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        bankNameLbl = new Label("Bank Name");
        routingNumberLbl = new Label("Routing Number");
        accoutNumberLbl = new Label("Account Number");
        balanceLbl = new Label("Balance");

        btn = new Button("Enter");

        grid.add(bankNameLbl, 0, 0);
        grid.add(bankName, 1, 0);
        grid.add(routingNumber, 1, 1);
        grid.add(routingNumberLbl, 0, 1);
        grid.add(accountNumber, 1, 2);
        grid.add(accoutNumberLbl, 0, 2);
        grid.add(balance, 1, 3);
        grid.add(balanceLbl, 0, 3);
        grid.add(btn, 1, 8);

        Group root = (Group) addBankAccount.getRoot();
        root.getChildren().add(grid);
        window.setTitle("Enter Bank Information");

        //Bank Name column
        TableColumn<BankAccount, String> bankNameClm = new TableColumn<>("Bank Name");
        bankNameClm.setMinWidth(200);
        bankNameClm.setCellValueFactory(new PropertyValueFactory<>("bankName"));

        // Routing Number Column
        TableColumn<BankAccount, Integer> routingNumberClm = new TableColumn<>("Routing Number");
        routingNumberClm.setMinWidth(200);
        routingNumberClm.setCellValueFactory(new PropertyValueFactory<>("routingNumber"));

        //Account Number Column
        TableColumn<BankAccount, Integer> accountNumberClm = new TableColumn<>("Account Number");
        accountNumberClm.setMinWidth(200);
        accountNumberClm.setCellValueFactory(new PropertyValueFactory<>("accountNo"));

        //Balance Number Column
        TableColumn<BankAccount, Double> balanceClm = new TableColumn<>("Balance");
        balanceClm.setMinWidth(200);
        balanceClm.setCellValueFactory(new PropertyValueFactory<>("balance"));

        bankAccountsTable = new TableView();
        //add items to grid
        //bankAccountsTable.setItems(bankAccountList);
        //add columns to table
        bankAccountsTable.getColumns().addAll(bankNameClm, routingNumberClm, accountNumberClm, balanceClm);

        bankAccountBox = new VBox();
        //Add bank accounts table to the VBox
        bankAccountBox.getChildren().addAll(bankAccountsTable);
        //Bank Account Listing scene
        bankAccountListing = new Scene(bankAccountBox);
        bankAccountObsList = FXCollections.observableArrayList();

        // enter bank account information
        btn.setOnAction(e -> {
            BankAccount b = new BankAccount(bankName.getText(), Integer.parseInt(routingNumber.getText()), Integer.parseInt(accountNumber.getText()), Double.parseDouble(balance.getText()));
            bankAccountObsList.add(b);
            bankName.clear();
            routingNumber.clear();
            accountNumber.clear();
            balance.clear();
            bankAccounts.add(b);
            window.setScene(homeScene);
        });
        
        bankAccountsTable.setOnMouseClicked(e -> {
            System.out.println("Here");
           // System.out.println(bankAccountsTable.getSelectionModel().getSelectedItem().getBankName());
            //System.out.println(bankAccountsTable.getSelectionModel().getSelectedItem().getRoutingNumber());
            //System.out.println(bankAccountsTable.getSelectionModel().getSelectedItem().getAccountNo());
           // System.out.println(bankAccountsTable.getSelectionModel().getSelectedItem().getBalance());
            TransactionsGUI.getBankAccount(bankAccountsTable.getSelectionModel().getSelectedItem());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<BankAccount> getBankAccounts() {
        ObservableList<BankAccount> bankAccountsObsList = FXCollections.observableArrayList();
       // bankAccounts.add(new BankAccount("Chase", 12121, 54545, 100000));
       System.out.println("here");
        for (BankAccount b : bankAccounts) {
            System.out.println(b);
            bankAccountsObsList.add(b);
        }
        return bankAccountsObsList;
    }
}
