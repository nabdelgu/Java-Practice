/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.GUI;

import blackjack.game.BlackJackPlayer;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author noaha
 */
public class BlackJackGUI extends Application {

    /*private static GridPane grid;
    private static ComboBox transactionTypeDropdown;
    private static final int comboboxValues[] = {1, 2, 3, 4, 5};
    private static Label comboBoxLabel, player1, player2, player3, player4, player5;
    private static TextField text1, text2, text3, text4, text5;
    private static Button confirmPlayers;
    private Scene setupPlayers, playGame;
     */
    private static ArrayList<BlackJackPlayer> players;
    private static Scene playGame;
    private static Label playerName, roundScore, currentBalance;
    private static HBox details;

    @Override
    public void start(Stage primaryStage) {

        playerName = new Label("Player Name:");
        roundScore = new Label("Round Score:");
        currentBalance = new Label("Current Balance:");

        details = new HBox();

        details.getChildren().addAll(playerName, roundScore, currentBalance);

        playGame = new Scene(details, 400, 350);

        players = InitializePlayers.initializePlayers(primaryStage, playGame);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
