package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.Session;
/**
 * class of refill command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */

public class Refill implements UserRequest {

    /**
     * 
     */
    final String inputFormat = "^(refill)\\s[ABCD]((0)|([1-9]\\d*))$";
    
    private Session session;
    private String fireEngineID;
    
    /**
     * constructor
     */
    public Refill() {
        
    }
    
    /**
     * executes the command if the game is not finished and the wind is already simulated
     */
    public void execute() throws GameLogicException {
        if (this.session.getGame().isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        if (!this.session.getGame().getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("the wind needs to be simulated.");
        }
        Player.getFireEngineFromID(fireEngineID).fillWaterTank();
        Terminal.printLine(Player.getFireEngineFromID(fireEngineID).getActionPoint());
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
    }
}
