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
    private int roundWager;
    private int roundScore;

    public BlackJackRound(BlackJackPlayer player, int roundWager, int roundScore) {
        this.player = player;
        this.roundWager = roundWager;
        this.roundScore = roundScore;
    }    

    public BlackJackPlayer getPlayer() {
        return player;
    }

    public void setPlayer(BlackJackPlayer player) {
        this.player = player;
    }

    public int getRoundWager() {
        return roundWager;
    }

    public void setRoundWager(int roundWager) {
        this.roundWager = roundWager;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }
    
    public void addToRoundScore(int diceRoll){
        this.roundScore += diceRoll;
    }

    @Override
    public String toString() {
        return "BlackJackRound{" + "player=" + player + ", roundWager=" + roundWager + ", roundScore=" + roundScore + '}';
    }  
       
}
