/*
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 * To change this license header, choose License Headers in Project Properties.
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 * To change this template file, choose Tools | Templates
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 * and open the template in the editor.
 */
package blackjack.GUI;

import blackjack.game.BlackJackPlayer;
import blackjack.game.BlackJackRound;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author noaha
 */
public class BlackJackGUI extends Application {

    private static ArrayList<BlackJackPlayer> players;
    private static Scene playGame;
    private static Label playerName, roundScore, currentBalance, betAmountLbl;
    private static HBox details, placeBets, diceHBox;
    private static VBox playerAction;

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
    private static ImageView dice1View, dice2View;
    private static int roll1, roll2;
    private static int playerIndex = 0;
    private static int maxScore = 0;

    @Override
    public void start(Stage primaryStage) throws InvocationTargetException {

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
        hitButton.setMaxHeight(100);
        hitButton.setMaxWidth(100);
        standButton = new Button("Stand");
        standButton.setMaxHeight(100);
        standButton.setMaxWidth(100);
        playerAction = new VBox(15);
        playerAction.getChildren().addAll(hitButton, standButton);
        playerAction.setPadding(new Insets(10, 10, 10, 10));
        playerAction.setAlignment(Pos.CENTER);
        borderPane = new BorderPane();

        dice2View = new ImageView();
        dice2View.setFitHeight(50);
        dice2View.setFitWidth(50);

        //dice2View.setImage(new Image(getClass().getResource("/blackjack/Images/dice-six-faces-dice-six-faces-" + roll1 + ".png").toExternalForm()));
        dice1View = new ImageView();
        dice1View.setFitHeight(50);
        dice1View.setFitWidth(50);

        diceHBox = new HBox(15);

        diceHBox.getChildren().addAll(dice1View, dice2View);
        diceHBox.setAlignment(Pos.CENTER);
        diceHBox.setPadding(new Insets(10, 10, 10, 10));

        borderPane.setTop(details);
        borderPane.setLeft(playerAction);
        borderPane.setBottom(placeBets);
        borderPane.setCenter(diceHBox);
        playGame = new Scene(borderPane, 650, 350);
        window = primaryStage;
        window.setResizable(false);
        players = InitializePlayers.initializePlayers(primaryStage, playGame);

    }

    public static void placeBets(ArrayList<BlackJackPlayer> players) {
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
        currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());
        betAmountLbl.setText("Wager Amount: ");
        window.setScene(playGame);
        window.show();

        placeBet.setOnAction(e -> {
            try {
                if (betAmount.getText().equals("")) {
                    throw new NullPointerException();
                }
                if (playerIndex <= players.size()) {

                    currentPlayer = players.get(playerIndex);
                    blackJackRound.add(new BlackJackRound(currentPlayer, Integer.parseInt(betAmount.getText()), 0));
                    currentPlayer.setCurrentHoldings(currentPlayer.getCurrentHoldings() - Integer.parseInt(betAmount.getText()));
                    betAmount.clear();

                    //set next player labels
                    playerIndex++;
                    if ((playerIndex >= players.size())) {
                        placeBet.setDisable(true);
                        betAmount.setDisable(true);
                        betAmount.clear();
                        playRound();
                    } else {
                        currentPlayer = players.get(playerIndex);
                        playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
                        // betAmountLbl.setText("Wager Amount: " + betAmount.getText());
                        currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());
                    }
                }

            } catch (NullPointerException ex) {
                Alert.displayError("Player name blank", "All player names must be entered", AlertType.ERROR);
            }

        });

    }

    private static void playRound() {
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
            roll1 = rollDice();
            roll2 = rollDice();
            //validate file exists

            dice1View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll1 + ".png"));
            dice2View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll2 + ".png"));

            blackJackRoundPlayer.addToRoundScore(roll1 + roll2);

            roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
            //player score is 21 or break
            if (blackJackRoundPlayer.getRoundScore() >= 21 && playerIndex <= blackJackRound.size()) {
                playerIndex++;
                if (playerIndex <= players.size() - 1) {
                    dice1View.setImage(null);
                    dice2View.setImage(null);
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
                    determineWinner(maxScore);
                }

            }
        });

        standButton.setOnAction(e -> {
            dice1View.setImage(null);
            dice2View.setImage(null);
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
                determineWinner(maxScore);
            }

        });
    }

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
            if (blackJackRound1.getRoundScore() == maxScore) {
                winnerScoreBlackJackPlayers.add(blackJackRound1.getPlayer());
                winnerPlayerRound = blackJackRound1;
            }
        }
        if (winnerScoreBlackJackPlayers.size() == 1) {
            //one winner
            winnerScoreBlackJackPlayers.get(0).addToHoldings(roundWinnings);
            Alert.displayError("Round Winner", winnerScoreBlackJackPlayers.get(0).getPlayer() + " has won this round and has won " + (roundWinnings - winnerPlayerRound.getRoundWager()) + "$.", AlertType.INFORMATION);
        } else {
            //tie game
            StringBuilder playerTied = new StringBuilder();
            for (BlackJackRound blackJackRound2 : blackJackRound) {
                if (winnerScoreBlackJackPlayers.get(winnerScoreBlackJackPlayers.size() - 1).equals(blackJackRound2)) {
                    playerTied.append(blackJackRound2.getPlayer().getPlayer());
                    playerTied.append(",");
                } else {
                    playerTied.append(blackJackRound2.getPlayer().getPlayer());
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
        // System.out.println("Dice Roll 1: " + new File(BlackJackGUI.class.getResource("src/BlackJack/Images/dice-six-faces-1.png").toString()).exists());
        launch(args);
    }

}
