package blackjack.game;

/**
 *
 * @author Noah Abdelguerfi
 * @since 05/26/2019
 */
public class BlackJackPlayer {

    private String player;
    private int currentHoldings;
    private boolean dealer;

    /**
     *
     * @param String player
     * @param int currentHoldings
     * @param boolean dealer
     */
    public BlackJackPlayer(String player, int currentHoldings, boolean dealer) {
        this.player = player;
        this.currentHoldings = currentHoldings;
        this.dealer = dealer;
    }

    /**
     *
     * @return String player
     */
    public String getPlayer() {
        return player;
    }

    /**
     *
     * @param String player
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     *
     * @return int currentHoldings
     */
    public int getCurrentHoldings() {
        return currentHoldings;
    }

    /**
     *
     * @param int currentHoldings
     */
    public void setCurrentHoldings(int currentHoldings) {
        this.currentHoldings = currentHoldings;
    }

    /**
     *
     * @param int amount
     */
    public void addToHoldings(int amount) {
        this.currentHoldings += amount;
    }

    /**
     *
     * @return boolean dealer
     */
    public boolean isDealer() {
        return dealer;
    }

    /**
     *
     * @param boolean dealer
     */
    public void setIsDealer(boolean dealer) {
        this.dealer = dealer;
    }

    /**
     * 
     * @return String 
     */
    @Override
    public String toString() {
        return "BlackJackPlayer{" + "player=" + player + ", currentHoldings=" + currentHoldings + '}';
    }

}
