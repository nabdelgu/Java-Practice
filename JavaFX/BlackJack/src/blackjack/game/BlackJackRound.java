package blackjack.game;

/**
 *
 * @author Noah Abdelguerfi
 * @since 05/26/2019
 */
public class BlackJackRound {

    private BlackJackPlayer player;
    private int roundWager;
    private int roundScore;

    /**
     *
     * @param BlackJackPlayer player
     * @param int roundWager
     * @param int roundScore
     */
    public BlackJackRound(BlackJackPlayer player, int roundWager, int roundScore) {
        this.player = player;
        this.roundWager = roundWager;
        this.roundScore = roundScore;
    }

    /**
     *
     * @return BlackJackPlayer player
     */
    public BlackJackPlayer getPlayer() {
        return player;
    }

    /**
     *
     * @param BlackJackPlayer player
     */
    public void setPlayer(BlackJackPlayer player) {
        this.player = player;
    }

    /**
     *
     * @return int roundWager
     */
    public int getRoundWager() {
        return roundWager;
    }

    /**
     *
     * @param int roundWager
     */
    public void setRoundWager(int roundWager) {
        this.roundWager = roundWager;
    }

    /**
     *
     * @return int roundScore
     */
    public int getRoundScore() {
        return roundScore;
    }

    /**
     *
     * @param int roundScore
     */
    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

   /**
    * 
    * @param int diceRoll 
    */
    public void addToRoundScore(int diceRoll) {
        this.roundScore += diceRoll;
    }

    /**
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "BlackJackRound{" + "player=" + player + ", roundWager=" + roundWager + ", roundScore=" + roundScore + '}';
    }

}
