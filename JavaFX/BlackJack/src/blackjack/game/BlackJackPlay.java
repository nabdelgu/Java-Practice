/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author noaha
 */
public class BlackJackPlay {

    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private static ArrayList<BlackJackPlayer> blackJackPlayers = new ArrayList<>();
    private static HashMap<BlackJackPlayer, Boolean> blackjackPlayersHm = new HashMap<BlackJackPlayer, Boolean>();

    private static void inilizeGame() {

        // Scanner sc = new Scanner(System.in);
        System.out.println("Enter a players names. Enter -1 when finished.");
        while (true) {
            String nextValue = sc.next();
            if (nextValue.equals("-1")) {
                break;
            } else {
                BlackJackPlayer player = new BlackJackPlayer(nextValue, 0);

                blackJackPlayers.add(player);
                blackjackPlayersHm.put(player, false);
            }
        }
    }

    private static void playRound() {
        boolean playRound = true;
        int playerIndex = 0;
        int maxScore = 0;
        int diceRoll;

        while (playRound) {
            //current black jack player
            BlackJackPlayer currentPlayer = blackJackPlayers.get(playerIndex);
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
    }

    private static int rollDice() {
        return (random.nextInt((6 - 1) + 1) + 1) + (random.nextInt((6 - 1) + 1) + 1);
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

    public static BlackJackPlayer determineWinner(int maxScore) {
        ArrayList<BlackJackPlayer> winnerScoreBlackJackPlayers = new ArrayList<>();
        for (BlackJackPlayer player : blackJackPlayers) {
            System.out.println(player);
            if (player.getScore() == maxScore) {
                winnerScoreBlackJackPlayers.add(player);
            }
        }
        if (winnerScoreBlackJackPlayers.size() == 1) {
            //one winner
            System.out.println("here");
            return winnerScoreBlackJackPlayers.get(0);
        } else {
            //tie game
            return null;
        }

    }

    public static void main(String[] args) {
        inilizeGame();
        playRound();

    }
}
