package edu.kit.informatik.logic;

/**
 * this class keeps track of the first player of the round
 * and simulation of the wind
 * @author Rodi
 * @version 1.0
 */
public class Turn {
    
    private Player firstPlayer;
    private boolean isWindSimulated;
    
    /**
     * constructor
     * @param firstPlayer
     * @param isWindSimulated
     */
    public Turn(Player firstPlayer, boolean isWindSimulated) {
        this.firstPlayer = firstPlayer;
        this.isWindSimulated = isWindSimulated;
    }

    /**
     * getter of the firstPlayer attribute
     * @return firstPlayer
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * setter of the firstPlayer attribute
     * @param firstPlayer
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    
    /**
     * getter of the isWindSimulated attribute
     * @return isWindSimulated
     */
    public boolean isWindSimulated() {
        return isWindSimulated;
    }

    /**
     * setter of the isWindSimulated attribute
     * @param isWindSimulated
     */
    public void setWindSimulated(boolean isWindSimulated) {
        this.isWindSimulated = isWindSimulated;
    }

    /**
     * updates turn, by setting the first player as the first next player of the current player, who is in game
     */
    public void updateTurn() {
        this.setFirstPlayer(this.getFirstPlayer().nextPlayer());
        while (!getFirstPlayer().isInGame()) {
            this.setFirstPlayer(this.getFirstPlayer().nextPlayer());
        }
        this.setWindSimulated(false);
    }
    
    
    

}
