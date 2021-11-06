package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.Session;
/**
 * class of fire-to-roll command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */

public class FireToRoll implements UserRequest {

    /**
     * input string must match this pattern
     */
    final String inputFormat = "^(fire-to-roll)\\s[1-6]$";
    
    private Session session;
    private int number;
    
    /**
     * constructor
     */
    public FireToRoll() {
        
    }
    
    
    /**
     * executes the command if the game is not already finished
     */
    public void execute() throws GameLogicException {
        FireBreaker game = this.session.getGame();
        if (game.isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        game.simulateWind(this.number);
        if (Player.thereAreEnginesLeft()) {
            if (!game.getCurrentPlayer().isInGame()) {
                game.turn();
                //in the assignment it is not clear in which case we should give a player identifier as an output
                //My code shows how i interpreted it but i am not 100% sure
                Terminal.printLine(game.getCurrentPlayer().getIdentifier());
            } else {
                Terminal.printLine("OK");
            }
        } else {
            this.session.getGame().setGameFinished(true);
            Terminal.printLine("lose");
        }
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
        this.number = Integer.parseInt(parameters[0]);
    }
}
