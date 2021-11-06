package edu.kit.informatik.exceptions;

/**
 * this expception is thrown when a problem occurs while initializing the game
 * @author Rodi
 * @version 1.0
 */
public class InitializationException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1515967169926018011L;

    /**
     * constructor with specified message
     * @param message
     */
    public InitializationException(String message) {
        super(message);
    }
    
    /**
     * constructor with default message
     */
    public InitializationException() {
        super("the game is not able to be initialized");
    }

}
