package edu.kit.informatik.exceptions;

/**
 * this exception is thrown when something contradicts with the game rules
 * @author Rodi
 * @version 1.0
 */
public class GameLogicException extends Exception {

    
    /**
     * 
     */
    private static final long serialVersionUID = 350154901323968065L;

    /**
     * constructor with default message
     */
    public GameLogicException() {
        super("this action causes a problem related to game logic");
    }
    
    /**
     * constructor with specified message
     * @param message
     */
    public GameLogicException(String message) {
        super(message);
    }
}
