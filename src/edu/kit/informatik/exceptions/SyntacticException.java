package edu.kit.informatik.exceptions;

/**
 * this exception is thrown when a syntactic problem occurs
 * @author Rodi
 * @version 1.0
 */

public class SyntacticException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8583593703317844903L;

    /**
     * constructor
     */
    public SyntacticException() {
        super("a syntactic exception occured.");
    }
    
    /**
     * constructor with specified message
     * @param message
     */
    public SyntacticException(String message) {
        super(message);
    }
}
