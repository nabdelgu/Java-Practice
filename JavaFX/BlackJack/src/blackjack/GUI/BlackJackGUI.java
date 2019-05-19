/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.GUI;

import blackjack.game.BlackJackPlayer;
import blackjack.game.BlackJackRound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private static Label playerName, roundScore, currentBalance, betAmountLbl;
    private static HBox details, placeBets;
    private static VBox playerAction;

    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private static ArrayList<BlackJackPlayer> blackJackPlayers = new ArrayList<>();
    private static HashMap<BlackJackPlayer, Boolean> blackjackPlayersHm = new HashMap<BlackJackPlayer, Boolean>();
    private static Stage window;
    private static Button placeBet, hitButton, standButton;
    private static BorderPane borderPane;
    private static TextField betAmount;
    private static BlackJackPlayer currentPlayer;
    private static BlackJackRound blackJackRoundPlayer;
    private static ArrayList<BlackJackRound> blackJackRound = new ArrayList<>();
    private static int playerIndex = 0;
    private static int maxScore = 0;

    @Override
    public void start(Stage primaryStage) {

        playerName = new Label();
        roundScore = new Label("Round Score:");
        currentBalance = new Label();
        betAmountLbl = new Label();
        details = new HBox();
        details.setSpacing(10);
        details.getChildren().addAll(playerName, roundScore, currentBalance, betAmountLbl);

        placeBet = new Button("Place Bet");
        betAmount = new TextField();
        betAmount.setPromptText("Enter Amount to bet");

        placeBets = new HBox();
        placeBets.setSpacing(10);
        placeBets.getChildren().addAll(placeBet, betAmount);
        //add all layouts to Border Pane

        hitButton = new Button("Hit");
        standButton = new Button("Stand");
        playerAction = new VBox();
        playerAction.getChildren().addAll(hitButton, standButton);
        playerAction.setAlignment(Pos.CENTER);
        borderPane = new BorderPane();
        borderPane.setTop(details);
        borderPane.setLeft(playerAction);
        borderPane.setBottom(placeBets);

        window = primaryStage;
        players = InitializePlayers.initializePlayers(primaryStage, playGame);

    }

    /*private static void nextPlayer(BlackJackPlayer currentPlayer) {
        playerName = new Label("Player Name: " + currentPlayer.getPlayer());
        roundScore = new Label("Round Score:" + currentPlayer.getScore());
        currentBalance = new Label("Current Balance:");

        details = new HBox();

        details.getChildren().addAll(playerName, roundScore, currentBalance);

        playGame = new Scene(details, 400, 350);
        window.setScene(playGame);
        window.show();
    }*/
    public static void placeBets(ArrayList<BlackJackPlayer> players) {
        // boolean placeBets = true;
        hitButton.setDisable(true);
        standButton.setDisable(true);
        placeBet.setDisable(false);
        betAmount.setDisable(false);
        blackJackRound.clear();
        roundScore.setText("Round Score: ");
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);
        playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
        currentBalance.setText("Player Holdings: " + Integer.toString(players.get(playerIndex).getCurrentHoldings()));
        // betAmountLbl.setText("Wager Amount: " + betAmount.getText());
        currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());

        playGame = new Scene(borderPane, 400, 350);
        window.setScene(playGame);
        window.show();

        placeBet.setOnAction(e -> {
            System.out.println("Player Index: " + playerIndex);
            // System.out.println(players.size() - 1);
            if (playerIndex <= players.size()) {

                currentPlayer = players.get(playerIndex);
                blackJackRound.add(new BlackJackRound(currentPlayer, Integer.parseInt(betAmount.getText()), 0));
                currentPlayer.setCurrentHoldings(currentPlayer.getCurrentHoldings() - Integer.parseInt(betAmount.getText()));
                betAmount.clear();

                //set next player labels
                playerIndex++;
                if ((playerIndex >= players.size())) {
                    System.out.println("here1");
                    placeBet.setDisable(true);
                    betAmount.setDisable(true);
                    betAmount.clear();
                    playRound();
                } else {
                    System.out.println("here2");
                    currentPlayer = players.get(playerIndex);
                    playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
                    // betAmountLbl.setText("Wager Amount: " + betAmount.getText());
                    currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());
                }
            } 
        });

    }

    private static void playRound() {
        System.out.println(blackJackRound.get(0));
        System.out.println(blackJackRound.get(1));
        playerIndex = 0;
        maxScore = 0;
        hitButton.setDisable(false);
        standButton.setDisable(false);
        blackJackRoundPlayer = blackJackRound.get(playerIndex);
        playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
        currentBalance.setText("Player Holdings: " + Integer.toString(blackJackRoundPlayer.getPlayer().getCurrentHoldings()));
        betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager());
        currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings());

        hitButton.setOnAction(e -> {
            blackJackRoundPlayer.addToRoundScore(rollDice() + rollDice());

            roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
            //player score is 21 or break
            System.out.println("Player Index: " + playerIndex);
            if (blackJackRoundPlayer.getRoundScore() >= 21 && playerIndex <= blackJackRound.size()) {
                // if (playerIndex <= blackJackRound.size()) {
                playerIndex++;
                if (playerIndex <= players.size() - 1) {
                    System.out.println("here");
                    if (blackJackRoundPlayer.getRoundScore() > maxScore) {
                        maxScore = blackJackRoundPlayer.getRoundScore();
                    }

                    blackJackRoundPlayer = blackJackRound.get(playerIndex);
                    playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
                    currentBalance.setText("Player Holdings: " + Integer.toString(blackJackRoundPlayer.getPlayer().getCurrentHoldings()));
                    betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager());
                    currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings());
                    roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                } else {
                    hitButton.setDisable(true);
                    standButton.setDisable(true);
                    System.out.println("round over determine winner");
                    determineWinner(maxScore);
                }
                // }

            }
        });

        standButton.setOnAction(e -> {
            System.out.println("here2");
            System.out.println(playerIndex);
            System.out.println(blackJackRound.size() - 1);

            playerIndex++;
            if (blackJackRoundPlayer.getRoundScore() > maxScore) {
                maxScore = blackJackRoundPlayer.getRoundScore();
            }

            if (playerIndex <= players.size() - 1) {
                blackJackRoundPlayer = blackJackRound.get(playerIndex);
                playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
                currentBalance.setText("Player Holdings: " + Integer.toString(blackJackRoundPlayer.getPlayer().getCurrentHoldings()));
                betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager());
                currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings());
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                //determine winner
            } else {
                hitButton.setDisable(true);
                standButton.setDisable(true);
                System.out.println("round over determine winner");
                determineWinner(maxScore);
            }

        });
    }

    /*  private static void playRound() {
        boolean playRound = true;  placeBet.disableProperty();
        int playerIndex = 0;
        int maxScore = 0;
        int diceRoll;

        while (playRound) {
            //current black jack player
            BlackJackPlayer currentPlayer = blackJackPlayers.get(playerIndex);
            nextPlayer(currentPlayer);
            //playerTurn = (playerOption != 1) || (currentPlayer.getScore() <= 21 || currentPlayer.getScore() || holdIndicator;
            while (blackjackPlayersHm.get(currentPlayer) == false && currentPlayer.getScore() < 21) {
                System.out.println("Current Player: " + currentPlayer.toString() + "\n press 1 to hold and 2 to hit.");
                int playerOption = sc.nextInt();
                switch (playerOption) {
                    case 2:
                        //hit option
                        diceRoll = rollDice();
                        System.out.println("You rolled a " + diceRoll);
                        currentPlayer.addToScore(diceRoll);
                        System.out.println("Your current score is " + currentPlayer.getScore());
                        if (currentPlayer.getScore() >= 21) {
                            blackjackPlayersHm.put(currentPlayer, true);
                        }
                        break;

                    case 1:
                        //hold option
                        blackjackPlayersHm.put(currentPlayer, true);
                        break;
                    //invalid option

                    default:
                        break;
                }
            }
            if (currentPlayer.getScore() > maxScore && currentPlayer.getScore() <= 21) {
                maxScore = currentPlayer.getScore();
            }

            if (playerIndex == blackJackPlayers.size() - 1) {
                playerIndex = 0;
            } else {
                playerIndex++;
            }
            // deermine if all players played
            playRound = allPlayersPlayed();
            System.out.println("Max Score:" + maxScore);
        }

        BlackJackPlayer winnerPlayer = determineWinner(maxScore);

        if (winnerPlayer == null) {
            System.out.println("Game is a tie");
        } else {
            System.out.println(winnerPlayer.getPlayer() + " won the round.");
        }

        //determine winner
    }*/
    private static int rollDice() {
        return (random.nextInt((6 - 1) + 1) + 1);
    }

    private static boolean allPlayersPlayed() {
        //player held
        for (Map.Entry<BlackJackPlayer, Boolean> player : blackjackPlayersHm.entrySet()) {
            if (player.getValue() == false) {
                return true;
            }
        }
        return false;
    }

    public static void determineWinner(int maxScore) {
        ArrayList<BlackJackPlayer> winnerScoreBlackJackPlayers = new ArrayList<>();
        int roundWinnings = 0;
        BlackJackRound winnerPlayerRound = null;
        //ArrayList<BlackJackRound> blackJackRound
        for (BlackJackRound blackJackRound1 : blackJackRound) {
            roundWinnings += blackJackRound1.getRoundWager();
            System.out.println(blackJackRound1);
            if (blackJackRound1.getRoundScore() == maxScore) {
                winnerScoreBlackJackPlayers.add(blackJackRound1.getPlayer());
                winnerPlayerRound = blackJackRound1;
            }
        }
        if (winnerScoreBlackJackPlayers.size() == 1) {
            //one winner
            System.out.println("here");
            winnerScoreBlackJackPlayers.get(0).addToHoldings(roundWinnings);
            Alert.displayError("Round Winner", winnerScoreBlackJackPlayers.get(0).getPlayer() + " has won this round and has won " + (roundWinnings - winnerPlayerRound.getRoundScore()) + "$.", AlertType.INFORMATION);
            // return winnerScoreBlackJackPlayers.get(0);
        } else {
            //tie game
            StringBuilder playerTied = new StringBuilder();
            for (BlackJackRound blackJackRound2 : blackJackRound) {
                if (winnerScoreBlackJackPlayers.get(winnerScoreBlackJackPlayers.size() - 1).equals(blackJackRound2)) {
                    playerTied.append(blackJackRound2.getPlayer());
                    playerTied.append(",");
                } else {
                    playerTied.append(blackJackRound2.getPlayer());
                }
            }

            Alert.displayError("Round tie.", playerTied.toString() + " have tied", AlertType.INFORMATION);
        }
        
        placeBets(players);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
