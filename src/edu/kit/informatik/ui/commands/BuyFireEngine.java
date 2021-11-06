package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.ui.Session;

/**
 * class of buy-fire-engine command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class BuyFireEngine implements UserRequest {
    
    /**
     * input string must match this regular expression
     */
    String inputFormat = "^(buy-fire-engine)\\s\\d+,\\d+$";
    
    private Session session;
    private int row;
    private int column;
    
    /**
     * construcotr
     */
    public BuyFireEngine() {
        
    }
    
    /**
     * executes the command, if the game is not already finished and if the wind is already simulated
     */
    public void execute() throws GameLogicException {
        FireBreaker game = this.session.getGame();
        if (game.isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        if (!game.getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("wind needs to be simulated");
        }
        game.buyFireEngine(this.row, this.column);
        Terminal.printLine(game.getCurrentPlayer().getReputationPoint());
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
     * sets attributes of the class ready to execute
     * @param session current session
     * @param parameters extracted from input string
     */
    
    public void setParametersReady(Session session, String[] parameters) {
        this.session = session;
        this.row = Integer.parseInt(parameters[0]);
        this.column = Integer.parseInt(parameters[1]);
    }

}
