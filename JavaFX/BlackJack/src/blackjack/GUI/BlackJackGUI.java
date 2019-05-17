/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.GUI;

import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author noaha
 */
public class BlackJackGUI extends Application {

    private static GridPane grid;
    private static ComboBox transactionTypeDropdown;
    private static final int comboboxValues[] = {1, 2, 3, 4, 5};
    private static Label comboBoxLabel, player1, player2, player3, player4, player5;
    private static TextField text1, text2, text3, text4, text5;
    private static Button confirmPlayers;

    @Override
    public void start(Stage primaryStage) {

        //combo box label
        comboBoxLabel = new javafx.scene.control.Label("Number of players");

        //number of players drop down
        transactionTypeDropdown = new ComboBox(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        //player name labels
        player1 = new Label("Player1");
        player2 = new Label("Player2");
        player3 = new Label("Player3");
        player4 = new Label("Player4");
        player5 = new Label("Player5");
        //player name text field
        text1 = new TextField();
        text2 = new TextField();
        text3 = new TextField();
        text4 = new TextField();
        text5 = new TextField();

        //confirm players button
        confirmPlayers = new Button("Enter");

        grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(comboBoxLabel, 0, 0);
        grid.add(transactionTypeDropdown, 1, 0);
        //add text labels
        grid.add(player1, 0, 1);
        grid.add(player2, 0, 2);
        grid.add(player3, 0, 3);
        grid.add(player4, 0, 4);
        grid.add(player5, 0, 5);
// add text fields
        grid.add(text1, 1, 1);
        grid.add(text2, 1, 2);
        grid.add(text3, 1, 3);
        grid.add(text4, 1, 4);
        grid.add(text5, 1, 5);

        //add button
        grid.add(confirmPlayers, 1, 7);

        //  StackPane root = new StackPane();
        //  root.getChildren().add(btn);
        Scene scene = new Scene(grid, 400, 350);

        primaryStage.setTitle("Enter player names");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
