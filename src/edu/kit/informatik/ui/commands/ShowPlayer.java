package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.FireEngine;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.Session;

/**
 * class of show-player command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class ShowPlayer implements UserRequest {
    
    /**
     * show-player command must match this pattern
     */
    final String inputForm = "^(show-player)$";
    
    private Session session;
    
    
    
    /**
     * executes the show-player command if the game is not over
     * the command prints the information of the current player
     */
    public void execute() throws GameLogicException {
        if (this.session.getGame().isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        Player player = this.session.getGame().getCurrentPlayer();
        String playerInfo = player.getIdentifier() + "," + player.getReputationPoint();
        Terminal.printLine(playerInfo);
        for (FireEngine engine : player.getFireEngines()) {
            if (engine.isInGame()) {
                Terminal.printLine(engine.getInformation());
            }
        }
    }
    
    /**
     * checks whether input for the command matches the regular expression
     * @param inputString read with Terminal.readLine in session class
     */
    public void hasCorrectForm(String inputString) throws SyntacticException {
        if (!inputString.matches(inputForm)) {
            throw new SyntacticException();
        }
    }
    
    /**
     * sets the session attribute to the current session
     * @param session current session
     * @param parameters extracted from input String
     */
    public void setParametersReady(Session session, String[] parameters) {
        this.session = session;
    }

}
