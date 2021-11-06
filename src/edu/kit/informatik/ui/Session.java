package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.FireBreaker;

/**
 * this class is to take the input string and execute the corresponding command
 * on the game
 * @author Rodi
 * @version 1.0
 */
public class Session {
    
    private FireBreaker game;
    private String firstInitializationArgs;
    
    /**
     * constructor
     * @param game initialized game
     * @param args of the first initialization
     */
    public Session(FireBreaker game, String args) {
        this.game = game;
        this.firstInitializationArgs = args;
    }

    /**
     * getter of the game attribute
     * @return game 
     */
    public FireBreaker getGame() {
        return game;
    }

    /**
     * setter of the game attribute
     * @param game
     */
    public void setGame(FireBreaker game) {
        this.game = game;
    }
    
    /**
     * getter of the firstInitializationArgs attribute
     * @return firstInitializationArgs
     */
    public String getFirstInitializationArgs() {
        return firstInitializationArgs;
    }

    /**
     * setter of the firstInitializationArgs attribute
     * @param firstInitializationArgs
     */
    public void setFirstInitializationArgs(String firstInitializationArgs) {
        this.firstInitializationArgs = firstInitializationArgs;
    }

    /**
     * begins the game and reads the input string
     */
    public void begin() {
        String inputString = Terminal.readLine();
        while (!inputString.equals("quit")) {
            try {
                Input input = new Input(inputString, this);
                if (input.getUserRequest() != null) {
                    input.getUserRequest().execute();
                }
            } catch (SyntacticException | GameLogicException e) {
                Terminal.printError(e.getMessage());
            }
            inputString = Terminal.readLine();
        }
    }
    
    

}
