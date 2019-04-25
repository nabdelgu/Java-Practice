/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class BankGUI {

    private static Label bankNameLbl, routingNumberLbl, accoutNumberLbl, balanceLbl;
    private static TextField bankName, routingNumber, accountNumber, balance;
    private static Button btn;
    private static GridPane grid;
    private static Scene scene;
    private static BankAccount b1;

    public static BankAccount getBankInformation() {
        Stage window = new Stage();
        scene = new Scene(new Group(), 450, 250);

        bankName = new TextField("Bank Name");
        routingNumber = new TextField("Routing Number");
        accountNumber = new TextField("Account Number");
        balance = new TextField("Balance");

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

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        window.setScene(scene);
        window.setTitle("Enter Bank Information");
        window.show();

        btn.setOnAction(e -> {
            b1 = new BankAccount(bankName.getText(), Integer.parseInt(routingNumber.getText()), Integer.parseInt(accountNumber.getText()), Double.parseDouble(balance.getText()));
            System.out.println(b1);
            window.close();
        });

        return b1;
    }
}
