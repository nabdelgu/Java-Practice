/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.GUI;

import blackjack.game.BlackJackPlayer;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author noaha
 */
public class InitializePlayers {

    private static GridPane grid;
    private static ComboBox transactionTypeDropdown;
    private static Label comboBoxLabel, player1, player2, player3, player4, player5;
    private static TextField text1, text2, text3, text4, text5;
    private static Button confirmPlayers;
    private static Scene setupPlayers;
    private static final ArrayList<BlackJackPlayer> players = new ArrayList<>();

    public static ArrayList<BlackJackPlayer> initializePlayers(Stage primaryStage, Scene playGame) {

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

        //add button
        grid.add(confirmPlayers, 1, 7);

        //setup player scenes
        setupPlayers = new Scene(grid, 400, 350);

        primaryStage.setTitle("Enter player names");
        primaryStage.setScene(setupPlayers);
        primaryStage.show();

        //update the labels and textfields when the user selects the number of players
        transactionTypeDropdown.setOnAction(e -> {
            int number = Integer.parseInt(transactionTypeDropdown.getValue().toString());
            text1.clear();
            text2.clear();
            text3.clear();
            text4.clear();
            text5.clear();
            grid.getChildren().removeAll(player1, player2, player3, player4, player5, text1, text2, text3, text4, text5);
            switch (number) {
                case 1:
                    grid.add(player1, 0, 1);
                    grid.add(text1, 1, 1);
                    break; // break is optional

                case 2:
                    grid.add(player1, 0, 1);
                    grid.add(text1, 1, 1);
                    grid.add(player2, 0, 2);
                    grid.add(text2, 1, 2);
                    break;
                case 3:
                    grid.add(player1, 0, 1);
                    grid.add(text1, 1, 1);
                    grid.add(player2, 0, 2);
                    grid.add(text2, 1, 2);
                    grid.add(player3, 0, 3);
                    grid.add(text3, 1, 3);
                    break;
                case 4:
                    grid.add(player1, 0, 1);
                    grid.add(text1, 1, 1);
                    grid.add(player2, 0, 2);
                    grid.add(text2, 1, 2);
                    grid.add(player3, 0, 3);
                    grid.add(text3, 1, 3);
                    grid.add(player4, 0, 4);
                    grid.add(text4, 1, 4);
                    break;
                case 5:
                    grid.add(player1, 0, 1);
                    grid.add(text1, 1, 1);
                    grid.add(player2, 0, 2);
                    grid.add(text2, 1, 2);
                    grid.add(player3, 0, 3);
                    grid.add(text3, 1, 3);
                    grid.add(player4, 0, 4);
                    grid.add(text4, 1, 4);
                    grid.add(player5, 0, 5);
                    grid.add(text5, 1, 5);
                    break;
            }
        });

        confirmPlayers.setOnAction(e -> {
            for (Node node : grid.getChildren()) {
                if (node instanceof TextField) {
                    // clear
                    System.out.println(((TextField) node).getText());
                    players.add(new BlackJackPlayer(((TextField) node).getText(), 0));
                }
            }
            //go to play game scene
            BlackJackGUI.placeBets(players);

        });
        return players;

    }

}
