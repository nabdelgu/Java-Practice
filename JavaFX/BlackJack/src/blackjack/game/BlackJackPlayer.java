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
    private int currentHoldings;

    public BlackJackPlayer(String player, int currentHoldings) {
        this.player = player;
        this.currentHoldings = currentHoldings;
    }
 
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getCurrentHoldings() {
        return currentHoldings;
    }

    public void setCurrentHoldings(int currentHoldings) {
        this.currentHoldings = currentHoldings;
    }
    
    public void addToHoldings(int amount){
        this.currentHoldings += amount;
    }

    @Override
    public String toString() {
        return "BlackJackPlayer{" + "player=" + player + ", currentHoldings=" + currentHoldings + '}';
    }   
    
}
