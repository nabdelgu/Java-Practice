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
public class BlackJackPlayer {
    private String player;
    private int Score;
    private boolean playerHold;

    public BlackJackPlayer(String player, int Score) {
        this.player = player;
        this.Score = Score;
        playerHold = false;
    }
 
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }
    
    public boolean getPlayerHold(){
        return this.playerHold;
    }
    
    public void addToScore(int diceRoll){
        this.Score += diceRoll;
    }

    public boolean isPlayerHold() {
        return playerHold;
    }

    public void setPlayerHold(boolean playerHold) {
        this.playerHold = playerHold;
    }
    
    
    @Override
    public String toString() {
        return "BlackJack{" + "player=" + player + ", Score=" + Score + '}';
    }
    
    
}
