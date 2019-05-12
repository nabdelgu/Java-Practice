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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 *  Main class for the application handles adding bank accounts and viewing banks
 * 
 * @author Noah
 * @since 05/12/2019
 * 
 */
public class BankAccountMain extends Application {
    
    private static Label bankNameLbl, routingNumberLbl, accoutNumberLbl, balanceLbl;
    private static TextField bankName, routingNumber, accountNumber, balance;
    private static Button enterBankAccount, btnAddBankAccount, btnViewBankAccount, deleteBankAccount, viewBankAccountBtn;
    private static GridPane grid;
    private static Scene addBankAccount, homeScene, bankAccountListing;
    private static VBox homePageBox, bankAccountBox;
    private static HBox bankAccountAction;
    private static Stage homeWindow, bankAccountsStage, enterBankInfoStage;
    private static TableView<BankAccount> bankAccountsTable;
    private static ObservableList<BankAccount> bankAccountObsList;
    private static BankAccount bankAccount;
    private static Group root;
    private static Connection connectionToDB = null;
    
    private static final String DELETE_BANK_ACCOUNT_SQL = "DELETE FROM BankAccounts WHERE BankName = ? and AccountNumber = ?";
    private static final String SELECT_BANKS_SQL = "SELECT BankName, RoutingNumber, AccountNumber,Balance FROM BankAccounts";
    private static final String SELECT_BANK_TRANSACTIONS_SQL = "SELECT BT.BankName,BT.AccountNumber,BT.TransactionID,TD.TransactionType,TD.TransactionAmount,TD.Balance FROM BankTransaction BT JOIN TransactionDetails TD on BT.TransactionID = TD.TransactionID WHERE BankName = ? and AccountNumber = ?";
    private static final String DELETE_BANK_TRANSACTIONS_SQL = "DELETE FROM BankTransaction Where BankName = ? and AccountNumber = ?";
    private static final String DELETE_TRANSACTION_DETAILS_SQL = "DELETE FROM TransactionDetails Where TransactionID in (SELECT TransactionID from BankTransaction Where BankName = ? and AccountNumber = ?)";
    /**
     * 
     * Where the application is launched from allows you to add and view banks
     * 
     * @param primaryStage
     * @throws NumberFormatException
     * @throws NullPointerException 
     */
    @Override
    public void start(Stage primaryStage) throws NumberFormatException, NullPointerException {
        try {
            //connect to database
            connectionToDB = ConnectToDb.getConnection();
            // create database tables if they do not exist
            ConnectToDb.createTablesIfNotExists(connectionToDB);
            // bank account home page            
            bankAccountObsList = loadBankAccountFromDb(connectionToDB);
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        // set the main stage and its properties
        homeWindow = primaryStage;
        enterBankInfoStage = new Stage();
        enterBankInfoStage.initModality(Modality.APPLICATION_MODAL);
        
        //close database connection when the home window is closer
        homeWindow.setOnCloseRequest(e -> {
            try {
                connectionToDB.close();
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // create the bank account stage
        bankAccountsStage = new Stage();
        bankAccountsStage.initModality(Modality.APPLICATION_MODAL);
        
        bankAccountsStage.setTitle("Bank Account List");
        btnAddBankAccount = new Button("Add Bank Account");
        btnViewBankAccount = new Button("View Bank Accounts");
        
        //initialize the home stage add buttons to model to the add bank stage or view bank stage
        homePageBox = new VBox();
        homePageBox.setAlignment(Pos.CENTER);
        homePageBox.setSpacing(50);
        homePageBox.getChildren().addAll(btnAddBankAccount, btnViewBankAccount);
        homePageBox.setAlignment(Pos.CENTER);
        homeScene = new Scene(homePageBox, 300, 250);
        homeWindow.setTitle("Bank Home");
        homeWindow.setScene(homeScene);
        homeWindow.show();
        //add bank account action event
        btnAddBankAccount.setOnAction(e -> {
            enterBankInfoStage.setScene(addBankAccount);
            enterBankInfoStage.show();
        });
        //view bank acccount action even
        btnViewBankAccount.setOnAction(e -> {
            bankAccountsStage.setScene(bankAccountListing);
            bankAccountsTable.setItems(bankAccountObsList);
            bankAccountsStage.show();
        });
        // add bank account scene
        addBankAccount = new Scene(new Group(), 450, 250);
        // text fiels to take bank account input
        bankName = new TextField();
        routingNumber = new TextField();
        accountNumber = new TextField();
        balance = new TextField();
        //grid pane to store textFields and labels
        grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        // define bank labels
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

        //bank account action Hbox
        bankAccountAction = new HBox();
        bankAccountAction.setPadding(new Insets(10, 10, 10, 10));
        bankAccountAction.setSpacing(10);

        // delete account button
        deleteBankAccount = new Button("Delete");
        //view bank account button
        viewBankAccountBtn = new Button("View Bank Account");
        // delete bank account action event
        deleteBankAccount.setOnAction(e -> {
            try {
                deleteBankAccountSelected(connectionToDB);
            } catch (SQLException ex) {
                System.out.println("SQLException");
            }
        });
        // add button to view and delete bank accounts
        bankAccountAction.getChildren().addAll(viewBankAccountBtn, deleteBankAccount);
        
        root = (Group) addBankAccount.getRoot();
        root.getChildren().add(grid);          
        
        //define table column names for bank details

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

        //add columns to table
        bankAccountsTable.getColumns().addAll(bankNameClm, routingNumberClm, accountNumberClm, balanceClm);
        
        bankAccountBox = new VBox();
        //Add bank accounts table to the VBox
        bankAccountBox.getChildren().addAll(bankAccountsTable, bankAccountAction);
        //Bank Account Listing scene
        bankAccountListing = new Scene(bankAccountBox);
        // bankAccountObsList = FXCollections.observableArrayList();

        // action event to handle entering of bank information
        enterBankAccount.setOnAction(e -> {
            
            try {
                bankAccount = new BankAccount(bankName.getText(), Integer.parseInt(routingNumber.getText()), Integer.parseInt(accountNumber.getText()), Double.parseDouble(balance.getText()), connectionToDB);
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
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // Action event to view a bank accounnt models you to the bank account selected
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
    
    /**
     * 
     * Handles deletion of a BankAccount
     * 
     * @param connectionToDB
     * @throws SQLException 
     */
    public static void deleteBankAccountSelected(Connection connectionToDB) throws SQLException {
     
        // get the bank to be deleted
        BankAccount bank = bankAccountsTable.getSelectionModel().getSelectedItems().get(0);

        //Delete from BankAccount table
        PreparedStatement deleteBank = connectionToDB.prepareStatement(DELETE_BANK_ACCOUNT_SQL);
        deleteBank.setString(1, bank.getBankName());
        deleteBank.setInt(2, bank.getAccountNo());
        deleteBank.executeUpdate();

        //Delete from TranscationDetails table
        PreparedStatement selectDeletedTranactions = connectionToDB.prepareStatement(DELETE_TRANSACTION_DETAILS_SQL);
        selectDeletedTranactions.setString(1, bank.getBankName());
        selectDeletedTranactions.setInt(2, bank.getAccountNo());
        selectDeletedTranactions.executeUpdate();
        
        //delete from BankTransactions table        
        PreparedStatement deleteBankTransactions = connectionToDB.prepareStatement(DELETE_BANK_TRANSACTIONS_SQL);
        deleteBankTransactions.setString(1, bank.getBankName());
        deleteBankTransactions.setInt(2, bank.getAccountNo());
        deleteBankTransactions.executeUpdate();
        
        //remove the bank account from the list
        bankAccountObsList.remove(bank);
        
    }
    /**
     * 
     * Used to return a Observable list of Bank objects loaded in from the database
     * 
     * @param connectionToDB
     * @return ObservableList<BankAccount>
     */
    public static ObservableList<BankAccount> loadBankAccountFromDb(Connection connectionToDB) {
        ObservableList<BankAccount> bankAccounts = FXCollections.observableArrayList();
        try {
            // gets the bank accounts to load in        
            final Statement selectBanks = connectionToDB.createStatement();
            final ResultSet rsSelectBanks = selectBanks.executeQuery(SELECT_BANKS_SQL);
            
            //iterate though bank accounts
            while (rsSelectBanks.next()) {
                // add bank object
                BankAccount bank = new BankAccount(rsSelectBanks.getString("BankName"), rsSelectBanks.getInt("RoutingNumber"), rsSelectBanks.getInt("AccountNumber"), rsSelectBanks.getDouble("Balance"));
                // add to bank account list
                bankAccounts.add(bank);
                
                // get bank transactions
                final PreparedStatement selectBankTransactions = connectionToDB.prepareStatement(SELECT_BANK_TRANSACTIONS_SQL);
                selectBankTransactions.setString(1, rsSelectBanks.getString("BankName"));
                selectBankTransactions.setInt(2, rsSelectBanks.getInt("AccountNumber"));

                // add transactions for that bank
                final ResultSet rsSelectBankTransactions = selectBankTransactions.executeQuery();
                while (rsSelectBankTransactions.next()) {
                    //add each bank transactions
                    bank.addTransaction(new Transaction(rsSelectBankTransactions.getInt("TransactionID"), rsSelectBankTransactions.getString("TransactionType"), rsSelectBankTransactions.getDouble("TransactionAmount"), rsSelectBankTransactions.getDouble("Balance")));
                }
                
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BankAccountMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bankAccounts;
    }
    
}
