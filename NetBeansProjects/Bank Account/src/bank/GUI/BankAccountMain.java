package bank.GUI;

import bank.account.BankAccount;
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
    private static Stage homeWindow, bankAccountsStage;
    private static TableView<BankAccount> bankAccountsTable;
    private static ObservableList<BankAccount> bankAccountObsList;
    private static BankAccount bankAccount;
    private static Group root;

    @Override
    public void start(Stage primaryStage) throws NumberFormatException,NullPointerException {
        // bank account home page
        homeWindow = primaryStage;
        bankAccountsStage = new Stage();
        bankAccountsStage.setTitle("Bank Account List");
        btnAddBankAccount = new Button("Add Bank Account");
        btnViewBankAccount = new Button("View Bank Accounts");
        homePageBox = new VBox();
        homePageBox.setAlignment(Pos.CENTER);
        homePageBox.setSpacing(50);
        homePageBox.getChildren().addAll(btnAddBankAccount, btnViewBankAccount);
        homeScene = new Scene(homePageBox, 300, 250);
        homeWindow.setTitle("Bank Home");
        homeWindow.setScene(homeScene);
        homeWindow.show();
        //add bank account button
        btnAddBankAccount.setOnAction(e -> {
            homeWindow.setScene(addBankAccount);
            homeWindow.show();
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
        deleteBankAccountById.setPromptText("Accoumt Numbber");

        // delete account button
        deleteBankAccount = new Button("Delete");
        //view bank account button
        viewBankAccountBtn = new Button("View Bank Account");
        // delete bank account button
        deleteBankAccount.setOnAction(e -> {
            deleteButtonClicked();
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
        bankAccountObsList = FXCollections.observableArrayList();

        // enter bank account information
        enterBankAccount.setOnAction(e -> {
            try {
                bankAccount = new BankAccount(bankName.getText(), Integer.parseInt(routingNumber.getText()), Integer.parseInt(accountNumber.getText()), Double.parseDouble(balance.getText()));
                bankAccountObsList.add(bankAccount);
                bankName.clear();
                routingNumber.clear();
                accountNumber.clear();
                balance.clear();
                // bankAccounts.add(b);
                homeWindow.setScene(homeScene);
            } catch (NumberFormatException ex) {
                if (bankName.getText().equals("") || routingNumber.getText().equals(null) || accountNumber.getText().equals("") || balance.getText().equals(null)) {
                    AlertError.displayError("Error", "All fields must be entered to continue", Alert.AlertType.ERROR);
                } else {
                    AlertError.displayError("Error", "Text entered where a number was expected", Alert.AlertType.ERROR);
                }
            }

        });
        // view bank account invoke TransactionGUI class method
        viewBankAccountBtn.setOnMouseClicked(e -> {
            try{
                 TransactionsGUI.getBankAccount(bankAccountsTable.getSelectionModel().getSelectedItem());
            }catch(NullPointerException ex){
                //nothing selected
            }
           
        });
    }

    public static void main(String[] args) {
        launch(args);

    }

    //button to delete bank account
    public static void deleteButtonClicked() {
        //get list of items selected
        ObservableList<BankAccount> bankAccountsSelected;
        //get the bank accounts that are selected
        bankAccountsSelected = bankAccountsTable.getSelectionModel().getSelectedItems();
        //remove bank accounts from the list
        bankAccountsSelected.forEach(bankAccountObsList::remove);
    }

}
