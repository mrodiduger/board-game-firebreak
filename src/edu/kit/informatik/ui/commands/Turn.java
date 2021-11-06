package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.ui.Session;

/**
 * class of turn command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class Turn implements UserRequest {
    /**
     * turn command must match this regular expression
     */
    final String inputForm = "^(turn)$";
    
    private Session session;
    
    /**
     * constructor
     */
    public Turn() {
        
    }
    
    /**
     * executes the turn command, if the game is not already finished and the wind is already simulated
     * the command changes the current player
     */
    public void execute() throws GameLogicException {
        if (this.session.getGame().isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        if (!this.session.getGame().getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("the wind needs to be simulated");
        }
        this.session.getGame().turn();
        Terminal.printLine(this.session.getGame().getCurrentPlayer().getIdentifier());
    }
    
    /**
     * checks whether input string matches the regular expression for the command
     * @param inputString read with Terminal.readLine in session class
     */
    public void hasCorrectForm(String inputString) throws SyntacticException {
        if (!inputString.matches(inputForm)) {
            throw new SyntacticException();
        }
    }
    
    /**
     * sets session, which stores the current game
     * @param session current session
     * @param parameters extracted from input String, not necessary for this particular command
     */
    public void setParametersReady(Session session, String[] parameters) {
        this.session = session;
    }

}
