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

    public BlackJackPlayer(String player, int Score) {
        this.player = player;
        this.Score = Score;
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

    @Override
    public String toString() {
        return "BlackJack{" + "player=" + player + ", Score=" + Score + '}';
    }
    
    
}
