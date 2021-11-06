package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.Session;
/**
 * class of move command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */

public class Move implements UserRequest {
    /**
     * move command input must match this pattern
     */
    private final String inputFormat = "^(move)\\s[ABCD]((0)|([1-9]\\d*)),\\d+,\\d+$";
    
    private Session session;
    private String fireEngineID;
    private int row;
    private int column;
    
    /**
     * constructor
     */
    public Move() {
        
    }
    
    /**
     * 
     */
    public void execute() throws GameLogicException {
        if (this.session.getGame().isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        if (!this.session.getGame().getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("the wind needs to be simulated");
        }
        Player.getFireEngineFromID(this.fireEngineID).move(row, column);
        Terminal.printLine("OK");
    }
    
    /**
     * checks whether input for the command matches the regular expression
     * @param inputString read with Terminal.readLine in session class
     */
    public void hasCorrectForm(String inputString) throws SyntacticException {
        if (!inputString.matches(inputFormat)) {
            throw new SyntacticException();
        }
    }
    
    /**
     * set attributes ready to execute
     * @param session current session
     * @param parameters extracted from input String
     */
    public void setParametersReady(Session session, String[] parameters) {
        this.session = session;
        this.fireEngineID = parameters[0];
        this.row = Integer.parseInt(parameters[1]);
        this.column = Integer.parseInt(parameters[2]);
    }
    
}
