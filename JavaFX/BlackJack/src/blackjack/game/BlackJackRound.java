/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.game;

/**
 *
 * @author noaha
 */
public class BlackJackRound {
    private BlackJackPlayer player;
    private int currentHoldings;
    private int roundScore;

    public BlackJackRound(BlackJackPlayer player, int currentHoldings, int roundScore) {
        this.player = player;
        this.currentHoldings = currentHoldings;
        this.roundScore = roundScore;
    }    

    public BlackJackPlayer getPlayer() {
        return player;
    }

    public void setPlayer(BlackJackPlayer player) {
        this.player = player;
    }

    public int getCurrentHoldings() {
        return currentHoldings;
    }

    public void setCurrentHoldings(int currentHoldings) {
        this.currentHoldings = currentHoldings;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    @Override
    public String toString() {
        return "BlackJackRound{" + "player=" + player + ", currentHoldings=" + currentHoldings + ", roundScore=" + roundScore + '}';
    }  
       
}
