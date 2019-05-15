/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.game;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author noaha
 */
public class BlackJackPlay {

    private static ArrayList<BlackJackPlayer> inilizeGame() {
        ArrayList<BlackJackPlayer> playerList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a players names. Enter -1 when finished.");
        while (true) {
            String nextValue = sc.next();
            if (nextValue.equals("-1")) {
                break;
            } else {
                playerList.add(new BlackJackPlayer(nextValue, 0));
            }
        }

        sc.close();
        return playerList;
    }

    private static void PlayGame(ArrayList<BlackJackPlayer> blackJackPlayers) {
        boolean playGame = true;
        //ArrayList<BlackJackPlayer> playerList = inilizeGame();
        int playerIndex = 0;
        while (playGame) {
            System.out.println(playerIndex);
            System.out.println(blackJackPlayers.get(playerIndex));
            
            if(playerIndex == blackJackPlayers.size()-1){
                playerIndex = 0;
            }else{
                 playerIndex++;
            }
            
           
        }

//        for (BlackJackPlayer player : blackJackPlayers) {
//            System.out.println(player);
//        }
    }

    public static void main(String[] args) {
        PlayGame(inilizeGame());
    }
}
