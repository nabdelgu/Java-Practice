package bank.GUI;

import bank.account.BankAccount;
import bank.account.Transaction;
import com.db.ConnectToDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class BankAccountMain extends Application {

    private static Label bankNameLbl, routingNumberLbl, accoutNumberLbl, balanceLbl;
    private static TextField bankName, routingNumber, accountNumber, balance, deleteBankAccountById;
    private static Button enterBankAccount, btnAddBankAccount, btnViewBankAccount, deleteBankAccount, viewBankAccountBtn;
    private static GridPane grid;
    private static Scene addBankAccount, homeScene, bankAccountListing;
    private static VBox homePageBox, bankAccountBox;
    private static HBox deleteBankAccountHBox;
    private static Stage homeWindow, bankAccountsStage, enterBankInfoStage;
    private static TableView<BankAccount> bankAccountsTable;
    private static ObservableList<BankAccount> bankAccountObsList;
    private static BankAccount bankAccount;
    private static Group root;
    private static Connection connectionToDB = null;

    private static final String DELETE_BANK_ACCOUNT_SQL = "DELETE FROM BankAccounts WHERE BankName = ? and AccountNumber = ?";
    private static final String SELECT_BANKS_SQL = "SELECT BankName, RoutingNumber, AccountNumber,Balance FROM BankAccounts";
    private static final String SELECT_BANK_TRANSACTIONS_SQL = "SELECT BT.BankName,BT.AccountNumber,BT.TransactionID,TD.TransactionType,TD.TransactionAmount,TD.Balance FROM BankTransaction BT JOIN TransactionDetails TD on BT.TransactionID = TD.TransactionID WHERE BankName = ? and AccountNumber = ?";

    @Override
    public void start(Stage primaryStage) throws NumberFormatException, NullPointerException {
        try {
            //connect to database
            connectionToDB = ConnectToDb.getConnection();
            // bank account home page            
            bankAccountObsList = loadBankAccountFromDb(connectionToDB);
            System.out.print(bankAccountObsList.get(0).getBankName());
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
        } catch (InstantiationException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        // bank account home page
        homeWindow = primaryStage;
        // enter bank info stage
        enterBankInfoStage = new Stage();

        //close database when the home window is closer
        homeWindow.setOnCloseRequest(e -> {
            try {
                connectionToDB.close();
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bankAccountsStage = new Stage();
        bankAccountsStage.setTitle("Bank Account List");
        btnAddBankAccount = new Button("Add Bank Account");
        btnViewBankAccount = new Button("View Bank Accounts");
        homePageBox = new VBox();
        homePageBox.setAlignment(Pos.CENTER);
        homePageBox.setSpacing(50);
        homePageBox.getChildren().addAll(btnAddBankAccount, btnViewBankAccount);
        homePageBox.setAlignment(Pos.CENTER);
        homeScene = new Scene(homePageBox, 300, 250);
        homeWindow.setTitle("Bank Home");
        homeWindow.setScene(homeScene);
        homeWindow.show();
        //add bank account button
        btnAddBankAccount.setOnAction(e -> {
            enterBankInfoStage.setScene(addBankAccount);
            enterBankInfoStage.show();
        });
        //view bank acccount button
        btnViewBankAccount.setOnAction(e -> {
            bankAccountsStage.setScene(bankAccountListing);
            bankAccountsTable.setItems(bankAccountObsList);
            bankAccountsStage.show();
        });
        // add bank account scene
        addBankAccount = new Scene(new Group(), 450, 250);

        bankName = new TextField();
        routingNumber = new TextField();
        accountNumber = new TextField();
        balance = new TextField();

        grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        // define labels
        bankNameLbl = new Label("Bank Name");
        routingNumberLbl = new Label("Routing Number");
        accoutNumberLbl = new Label("Account Number");
        balanceLbl = new Label("Balance");

        enterBankAccount = new Button("Enter");
        // add items to the grid
        grid.add(bankNameLbl, 0, 0);
        grid.add(bankName, 1, 0);
        grid.add(routingNumber, 1, 1);
        grid.add(routingNumberLbl, 0, 1);
        grid.add(accountNumber, 1, 2);
        grid.add(accoutNumberLbl, 0, 2);
        grid.add(balance, 1, 3);
        grid.add(balanceLbl, 0, 3);
        grid.add(enterBankAccount, 1, 8);

        //delete bank account box
        deleteBankAccountHBox = new HBox();
        deleteBankAccountHBox.setPadding(new Insets(10, 10, 10, 10));
        deleteBankAccountHBox.setSpacing(10);

        //define field to input account number to delete
        deleteBankAccountById = new TextField();
        deleteBankAccountById.setPromptText("Account Number");

        // delete account button
        deleteBankAccount = new Button("Delete");
        //view bank account button
        viewBankAccountBtn = new Button("View Bank Account");
        // delete bank account button
        deleteBankAccount.setOnAction(e -> {
            try {
                deleteBankAccountSelected(connectionToDB);
            } catch (SQLException ex) {
                System.out.println("SQLException");
            }
        });

        deleteBankAccountHBox.getChildren().addAll(viewBankAccountBtn, deleteBankAccount);

        root = (Group) addBankAccount.getRoot();
        root.getChildren().add(grid);
        homeWindow.setTitle("Enter Bank Information");

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
        //Add bank accounts   table to the VBox
        bankAccountBox.getChildren().addAll(bankAccountsTable, deleteBankAccountHBox);
        //Bank Account Listing scene
        bankAccountListing = new Scene(bankAccountBox);
        // bankAccountObsList = FXCollections.observableArrayList();

        // enter bank account information
        enterBankAccount.setOnAction(e -> {

            try {
                bankAccount = new BankAccount(bankName.getText(), Integer.parseInt(routingNumber.getText()), Integer.parseInt(accountNumber.getText()), Double.parseDouble(balance.getText()), true, connectionToDB);
                bankAccountObsList.add(bankAccount);
                bankName.clear();
                routingNumber.clear();
                accountNumber.clear();
                balance.clear();
                // bankAccounts.add(b);
                enterBankInfoStage.close();
            } catch (NumberFormatException ex) {
                if (bankName.getText().equals("") || routingNumber.getText().equals("") || accountNumber.getText().equals("") || balance.getText().equals("")) {
                    AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
                } else {
                    AlertError.displayError("Error", "Text entered where a number was expected", Alert.AlertType.ERROR);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // view bank account invoke TransactionGUI class method
        viewBankAccountBtn.setOnMouseClicked(e -> {
            try {
                TransactionsGUI.getBankAccount(bankAccountsTable.getSelectionModel().getSelectedItem(), connectionToDB);
            } catch (NullPointerException ex) {
                //nothing selected
            }

        });
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void deleteBankAccountSelected(Connection c) throws SQLException {
        //get list of items selected
        // ObservableList<BankAccount> bankAccountsSelected;
        //get the bank accounts that are selected
        //remove bank accounts from the list
        BankAccount b = bankAccountsTable.getSelectionModel().getSelectedItems().get(0);

        PreparedStatement deleteBank = c.prepareStatement(DELETE_BANK_ACCOUNT_SQL);
        deleteBank.setString(1, b.getBankName());
        deleteBank.setInt(2, b.getAccountNo());
        deleteBank.executeUpdate();
        bankAccountObsList.remove(b);
    }

    public static ObservableList<BankAccount> loadBankAccountFromDb(Connection c) {
        ObservableList<BankAccount> bankAccounts = FXCollections.observableArrayList();
        try {
            // insert BankAccount object from database        
            Statement selectBanks = c.createStatement();
            ResultSet rsSelectBanks = selectBanks.executeQuery(SELECT_BANKS_SQL);

            while (rsSelectBanks.next()) {
                // add bank object
                 System.out.println(rsSelectBanks.getString("BankName"));
                System.out.println(rsSelectBanks.getInt("RoutingNumber"));
                System.out.println(rsSelectBanks.getInt("AccountNumber"));
                System.out.println(rsSelectBanks.getDouble("Balance"));/**/
                BankAccount b = new BankAccount(rsSelectBanks.getString("BankName"), rsSelectBanks.getInt("RoutingNumber"), rsSelectBanks.getInt("AccountNumber"), rsSelectBanks.getDouble("Balance"), false, connectionToDB);
                bankAccounts.add(b);

                PreparedStatement selectBankTransactions = c.prepareStatement(SELECT_BANK_TRANSACTIONS_SQL);
                selectBankTransactions.setString(1, rsSelectBanks.getString("BankName"));
                selectBankTransactions.setInt(2, rsSelectBanks.getInt("AccountNumber"));

                // add transactions for that bank
                ResultSet rsSelectBankTransactions = selectBankTransactions.executeQuery();
                while (rsSelectBankTransactions.next()) {
                    System.out.println(rsSelectBankTransactions.getInt("TransactionID"));
                    System.out.println(rsSelectBankTransactions.getString("TransactionType"));
                    System.out.println(rsSelectBankTransactions.getDouble("TransactionAmount"));
                    System.out.println(rsSelectBankTransactions.getDouble("Balance"));
                    b.addTransaction(new Transaction(rsSelectBankTransactions.getInt("TransactionID"), rsSelectBankTransactions.getString("TransactionType"), rsSelectBankTransactions.getDouble("TransactionAmount"), rsSelectBankTransactions.getDouble("Balance")));
                }

            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bankAccounts;
    }

}
