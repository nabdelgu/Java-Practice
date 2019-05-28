package blackjack.GUI;

import blackjack.game.BlackJackPlayer;
import blackjack.game.BlackJackRound;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
 * @author Noah Abdelguerfi
 * @since 05/25/2019
 */
public class BlackJackGUI extends Application {

    private static ArrayList<BlackJackPlayer> players;
    private static Scene playGame;
    private static Label playerName, roundScore, currentBalance, betAmountLbl, instructionsLbl;
    private static HBox details, placeBets, diceHBox;
    private static VBox playerAction, instructionVBox;

    private static final Random random = new Random();
    private static Stage window;
    private static Button placeBet, hitButton, standButton, withdrawButton, playAgain;
    private static BorderPane borderPane;
    private static TextField betAmount;
    private static BlackJackPlayer currentPlayer;
    private static BlackJackRound blackJackRoundPlayer;
    private static final ArrayList<BlackJackRound> blackJackRound = new ArrayList<>();
    private static ImageView dice1View, dice2View;
    private static int roll1, roll2;
    private static int playerIndex = 0;

    @Override
    public void start(Stage primaryStage) throws InvocationTargetException {

        playerName = new Label();
        playerName.setId("bold-label");
        roundScore = new Label("Round Score:");
        roundScore.setId("bold-label");
        currentBalance = new Label();
        currentBalance.setId("bold-label");
        betAmountLbl = new Label();
        betAmountLbl.setId("bold-label");
        instructionsLbl = new Label();
        instructionsLbl.getStyleClass().add("label-instructions");
        // instructionsLbl.setStyle("-fx-font-size: 15pt; -fx-text-fill: #0000ff");
        //instructionsLbl.setStyle("-fx-text-fill: #cc00cc");
        details = new HBox();
        details.setSpacing(10);
        details.getChildren().addAll(playerName, roundScore, currentBalance, betAmountLbl);
        details.getStyleClass().add("label-player-info");

        placeBet = new Button("Place Bet");
        betAmount = new TextField();
        betAmount.setPromptText("Enter Amount to bet");

        placeBets = new HBox();
        placeBets.setPadding(new Insets(10, 10, 10, 10));
        placeBets.setSpacing(10);
        placeBets.getChildren().addAll(placeBet, betAmount);
        //add all layouts to Border Pane

        hitButton = new Button("Hit");
        hitButton.setMaxHeight(100);
        hitButton.setMaxWidth(100);
        standButton = new Button("Stand");
        standButton.setMaxHeight(100);
        standButton.setMaxWidth(100);
        withdrawButton = new Button("Withdraw");
        withdrawButton.setMaxHeight(100);
        withdrawButton.setMaxWidth(100);
        playAgain = new Button("Play again");
        //  playAgain.getT
        playerAction = new VBox(15);
        playerAction.getChildren().addAll(hitButton, standButton, withdrawButton);
        playerAction.setPadding(new Insets(10, 10, 10, 10));
        playerAction.setAlignment(Pos.CENTER);
        borderPane = new BorderPane();

        dice2View = new ImageView();
        dice2View.setFitHeight(50);
        dice2View.setFitWidth(50);

        dice1View = new ImageView();
        dice1View.setFitHeight(50);
        dice1View.setFitWidth(50);

        diceHBox = new HBox(15);

        diceHBox.getChildren().addAll(dice1View, dice2View);
        diceHBox.setAlignment(Pos.CENTER);

        instructionVBox = new VBox(15);
        instructionVBox.getChildren().addAll(instructionsLbl, diceHBox, playAgain);
        instructionVBox.setAlignment(Pos.CENTER);

        borderPane.setTop(details);
        borderPane.setLeft(playerAction);
        borderPane.setBottom(placeBets);
        borderPane.setCenter(instructionVBox);
        playGame = new Scene(borderPane, 800, 500);
        playGame.getStylesheets().add(BlackJackGUI.class.getResource("style.css").toExternalForm());
        window = primaryStage;
        window.setResizable(false);
        players = InitializePlayers.initializePlayers(primaryStage);

        //play game again
        playAgain.setOnAction(e -> {
            //allow users to select players again
            players.clear();
            blackJackRound.clear();
            players = InitializePlayers.initializePlayers(primaryStage);
        });

    }

    /**
     *
     * @param ArrayList<BlackJackPlayer>
     * @since 05/25/2019 Iterates though all the players and lets them place
     * their bets.
     */
    public static void placeBets(ArrayList<BlackJackPlayer> players) {
        //remove players that are out of money
        hitButton.setDisable(true);
        standButton.setDisable(true);
        placeBet.setDisable(false);
        betAmount.setDisable(false);
        withdrawButton.setDisable(false);
        playAgain.setVisible(false);
        dice1View.setImage(null);
        dice2View.setImage(null);
        blackJackRound.clear();
        roundScore.setText("Round Score: ");
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);
        playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
        currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings() + "$");
        betAmountLbl.setText("Wager Amount: ");
        instructionsLbl.setText(currentPlayer.getPlayer() + " place your bet!");
        window.setScene(playGame);
        window.show();

        placeBet.setOnAction(e -> {
            try {
                String errorTitle, errorText;
                errorTitle = "";
                errorText = "";
                //bet has been left blank throw an exception
                if (betAmount.getText().equals("")) {
                    throw new NullPointerException();
                } else if (Integer.parseInt(betAmount.getText()) > currentPlayer.getCurrentHoldings()) {
                    throw new InputMismatchException();
                }

                //next player exists
                if (playerIndex <= players.size()) {

                    //set the current players bet adjust their holdings
                    currentPlayer = players.get(playerIndex);
                    blackJackRound.add(new BlackJackRound(currentPlayer, Integer.parseInt(betAmount.getText()), 0));
                    currentPlayer.setCurrentHoldings(currentPlayer.getCurrentHoldings() - Integer.parseInt(betAmount.getText()));
                    betAmount.clear();

                    //increment the player index
                    playerIndex++;
                    //all players bet play round
                    if ((playerIndex >= players.size())) {
                        placeBet.setDisable(true);
                        betAmount.setDisable(true);
                        betAmount.clear();
                        playRound();
                    } else {
                        //players still left to bet set screen to next player
                        currentPlayer = players.get(playerIndex);
                        playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
                        currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());
                        instructionsLbl.setText(currentPlayer.getPlayer() + " place your bet!");
                        // if next player is dealer play the round
                        if (currentPlayer.isDealer()) {
                            blackJackRound.add(new BlackJackRound(currentPlayer, 0, 0));
                            playRound();
                        }
                    }
                }

            } catch (NullPointerException ex) {
                Alert.displayError("Bet blank", "The bet field cannot be left blank.", AlertType.ERROR);
            } catch (InputMismatchException ex) {
                Alert.displayError("Invalid bet", "You do not have enough to bet that amount.", AlertType.ERROR);
            } catch (NumberFormatException ex) {
                Alert.displayError("Input mismatch", "The input must be a number.", AlertType.ERROR);
            }

        });

        withdrawButton.setOnAction(e -> {
            Alert.displayError("Player withdraw", currentPlayer.getPlayer() + " has withdrawn with " + currentPlayer.getCurrentHoldings() + "$", AlertType.INFORMATION);
            //removes the player from the players array list
            players.remove(currentPlayer);
            //remove the player from the black jack round array list
            for (BlackJackRound r : blackJackRound) {
                if (r.getPlayer().equals(currentPlayer)) {
                    blackJackRound.remove(r);
                }
            }
            //one player left so stop the game
            if (players.size() == 1) {
                instructionsLbl.setText("Game Over");
                withdrawButton.setDisable(true);
                placeBet.setDisable(true);
                betAmount.setDisable(true);
                gameOver();

            } else {
                //set screen to the next player
                currentPlayer = players.get(playerIndex);
                playerName.setText("Player Name: " + players.get(playerIndex).getPlayer());
                currentBalance.setText("Current Holdings: " + currentPlayer.getCurrentHoldings());

            }

        });

    }

    /**
     * @param BlackJackRound This method handles a dealers play
     */
    private static void handleDealerPlay(BlackJackRound blackJackRoundPlayer) {
        // set the labels
        instructionsLbl.setText("Dealer Turn");
        playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BlackJackGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean dealerTurn = true;

        while (dealerTurn) {
            //perform dice roll and display the dice images
            roll1 = randomNumber(1, 6);
            roll2 = randomNumber(1, 6);
            dice1View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll1 + ".png"));
            dice2View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll2 + ".png"));
            roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());

            //add roll to the players round score
            blackJackRoundPlayer.addToRoundScore(roll1 + roll2);

            //dealer keeps rolling until their score is greater than or equal to 16 
            //determineWinner is then called and dealerTurn is set to false
            if (blackJackRoundPlayer.getRoundScore() > 21) {
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                Alert.displayError("Delear Broke", "The Dealer broke with a score of " + blackJackRoundPlayer.getRoundScore() + " turn over", AlertType.INFORMATION);
                dealerTurn = false;
                determineWinner();
            } else if (blackJackRoundPlayer.getRoundScore() == 21) {
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                Alert.displayError("Delear scored 21", "The Dealer score is 21 turn is over", AlertType.INFORMATION);
                dealerTurn = false;
                determineWinner();
            } else if (blackJackRoundPlayer.getRoundScore() < 16) {
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                Alert.displayError("Delear rolled", "The dealer rolled a " + (roll1 + roll2) + " there total score is " + blackJackRoundPlayer.getRoundScore(), AlertType.INFORMATION);
            } else if (blackJackRoundPlayer.getRoundScore() >= 16 && blackJackRoundPlayer.getRoundScore() < 21) {
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                Alert.displayError("Delear stand", "The dealer stood with a score of " + (roll1 + roll2) + " there total score is " + blackJackRoundPlayer.getRoundScore(), AlertType.INFORMATION);
                dealerTurn = false;
                determineWinner();
            }
        }
    }

    /*
    * Handles a round being played. Handles hit and stand button events
     */
    private static void playRound() {
        //set the first player
        playerIndex = 0;
        hitButton.setDisable(false);
        standButton.setDisable(false);
        withdrawButton.setDisable(true);
        placeBet.setDisable(true);
        betAmount.setDisable(true);
        blackJackRoundPlayer = blackJackRound.get(playerIndex);
        playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
        currentBalance.setText("Player Holdings: " + Integer.toString(blackJackRoundPlayer.getPlayer().getCurrentHoldings()));
        betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager() + "$");
        currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings());
        instructionsLbl.setText(blackJackRoundPlayer.getPlayer().getPlayer() + " it is your turn to roll!");

        hitButton.setOnAction(e -> {
            //generate random roll
            roll1 = randomNumber(1, 6);
            roll2 = randomNumber(1, 6);
            //set images accordingly
            dice1View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll1 + ".png"));
            dice2View.setImage(new Image("file:src/BlackJack/Images/dice-six-faces-" + roll2 + ".png"));

            //add rolls to players round score
            blackJackRoundPlayer.addToRoundScore(roll1 + roll2);
            roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());

            //player score is 21 or break
            if (blackJackRoundPlayer.getRoundScore() >= 21 && playerIndex <= blackJackRound.size()) {
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                //player either broke our scored over 21 display message
                if (blackJackRoundPlayer.getRoundScore() == 21) {
                    Alert.displayError("Score 21", "Your score is 21 your turn is over", AlertType.INFORMATION);
                } else if (blackJackRoundPlayer.getRoundScore() > 21) {
                    Alert.displayError("Break", "You rolled " + blackJackRoundPlayer.getRoundScore() + " and broke turn over.", AlertType.INFORMATION);
                }

                playerIndex++;
                //next player exists set screen to next player
                if (playerIndex <= players.size() - 1) {
                    dice1View.setImage(null);
                    dice2View.setImage(null);
                    blackJackRoundPlayer = blackJackRound.get(playerIndex);
                    instructionsLbl.setText(blackJackRoundPlayer.getPlayer().getPlayer() + " it is your turn to roll!");
                    playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
                    betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager() + "$");
                    currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings() + "$");
                    roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                    instructionsLbl.setText(blackJackRoundPlayer.getPlayer().getPlayer() + " it is your turn to roll!");
                    //next player is a dealer call handleDealerPlay method
                    if (blackJackRoundPlayer.getPlayer().isDealer()) {
                        handleDealerPlay(blackJackRoundPlayer);

                    }
                } else {
                    //no next player call determine winner
                    hitButton.setDisable(true);
                    standButton.setDisable(true);
                    dice1View.setImage(null);
                    dice2View.setImage(null);
                    determineWinner();
                }

            }
        }
        );

        standButton.setOnAction(e
                -> {
            //user stands
            dice1View.setImage(null);
            dice2View.setImage(null);
            //see if there is a next player
            playerIndex++;
            if (playerIndex <= players.size() - 1) {
                //set screen to the next player
                blackJackRoundPlayer = blackJackRound.get(playerIndex);
                playerName.setText("Player Name: " + blackJackRoundPlayer.getPlayer().getPlayer());
                betAmountLbl.setText("Wager Amount: " + blackJackRoundPlayer.getRoundWager() + "$");
                currentBalance.setText("Current Holdings: " + blackJackRoundPlayer.getPlayer().getCurrentHoldings() + "$");
                roundScore.setText("Round Score: " + blackJackRoundPlayer.getRoundScore());
                instructionsLbl.setText(blackJackRoundPlayer.getPlayer().getPlayer() + " it is your turn to roll!");
                //if the next player is a dealer call handleDealerPlay
                if (blackJackRoundPlayer.getPlayer().isDealer()) {
                    handleDealerPlay(blackJackRoundPlayer);
                }
            } else {
                //no next player detemine winner
                hitButton.setDisable(true);
                standButton.setDisable(true);
                determineWinner();
            }

        }
        );
    }

    /**
     *
     * @return int gets the max round score that is not greater than 21
     */
    private static int getMaxScore() {
        int maxScore = -1;
        for (BlackJackRound blackJackRound : blackJackRound) {
            if (blackJackRound.getRoundScore() > maxScore && blackJackRound.getRoundScore() <= 21) {
                maxScore = blackJackRound.getRoundScore();
            }
        }
        return maxScore;
    }

    //remove the players that dont have any more money
    private static void removePlayersThatAreOutOfMoney() {
        //iterate through the array list if the players are out of money remove them
        ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();

        for (int i = 0; i < players.size(); i++) {
            //if players score is 0 add them to indexesToDelete array list
            if (players.get(i).getCurrentHoldings() == 0) {
                Alert.displayError("Player out of money", players.get(i).getPlayer() + " is out of money they are being removed from the game.", AlertType.INFORMATION);
                indexesToDelete.add(i);
            }
        }
        //iterate through the array list and delete 
        for (Integer i : indexesToDelete) {
            players.remove(players.get(i));

        }

        if (players.size() == 1) {
            instructionsLbl.setText("Game Over");
            gameOver();
        }
    }

    //determines if the game is over
    private static void gameOver() {
        for (Node node : borderPane.getChildren()) {
            if (node instanceof Button && !((Button) node).getText().equals("Play again")) {
                node.setDisable(true);
            }

        }
        Alert.displayError("Game over", "No players left to play against. Game over", AlertType.INFORMATION);
        playAgain.setVisible(true);
        //  playAgain.setDisable(false);

    }

    /**
     *
     * @param min
     * @param max
     * @return int generates a random number between min and max
     */
    private static int randomNumber(int min, int max) {
        return (random.nextInt((max - min) + 1) + 1);
    }

    //determines the winning black jack player or if there is a tir
    public static void determineWinner() {
        ArrayList<BlackJackPlayer> winnerScoreBlackJackPlayers = new ArrayList<>();
        BlackJackRound winnerPlayerRound = null;
        //get the max round score
        int maxScore = getMaxScore();

        //add all players to the array list if they have the max score
        for (BlackJackRound blackJackRound1 : blackJackRound) {
            if (blackJackRound1.getRoundScore() == maxScore) {
                winnerScoreBlackJackPlayers.add(blackJackRound1.getPlayer());
                winnerPlayerRound = blackJackRound1;
            }
        }
        //if aarray list size is 1 than there is one winner
        if (winnerScoreBlackJackPlayers.size() == 1) {
            //one winner
            if (winnerScoreBlackJackPlayers.get(0).isDealer()) {
                Alert.displayError("Round Winner", "The dealer won.", AlertType.INFORMATION);
            } else {
                winnerScoreBlackJackPlayers.get(0).addToHoldings(winnerPlayerRound.getRoundWager() * 2);
                Alert.displayError("Round Winner", winnerScoreBlackJackPlayers.get(0).getPlayer() + " has won this round and has won " + winnerPlayerRound.getRoundWager() + "$.", AlertType.INFORMATION);

            }
        } else {
            //game is a tie
            for (BlackJackRound blackJackRound3 : blackJackRound) {
                blackJackRound3.getPlayer().addToHoldings(blackJackRound3.getRoundWager());
            }
            Alert.displayError("Round tie.", "The round has ended in a tie", AlertType.INFORMATION);
        }
        //call place bets

        removePlayersThatAreOutOfMoney();
        if (players.size() > 1) {
            placeBets(players);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
