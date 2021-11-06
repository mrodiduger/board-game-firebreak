package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.FireEngine;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.Session;

/**
 * class of extinguish command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class Extinguish implements UserRequest {
    
    /**
     * input string must match this pattern
     */
    final String inputFormat = "^(extinguish)\\s[ABCD]((0)|([1-9]\\d*)),\\d+,\\d+$";
    //final String INPUT_FORMAT = "^(extinguish)\\s[ABCD]\\d+,\\d+,\\d+$";
    
    private Session session;
    private String fireEngineID;
    private int row;
    private int column;
    
    /**
     * constructor
     */
    public Extinguish() {
        
    }
    
    /**
     * executes the command  if the game is not finished yet and if the wind is already simulated
     */
    public void execute() throws GameLogicException {
        FireBreaker game = this.session.getGame();
        if (game.isGameFinished()) {
            throw new GameLogicException("game is already finished");
        }
        FireEngine fireEngine = Player.getFireEngineFromID(fireEngineID);
        if (!game.getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("the wind needs to be simulated");
        }
        fireEngine.extinguish(row, column);
        if (game.getGameBoard().getBurningCells().get(0).isEmpty()
                && game.getGameBoard().getBurningCells().get(1).isEmpty() ) {
            this.session.getGame().setGameFinished(true);
            Terminal.printLine("win");
        } else {
            Terminal.printLine(game.getGameBoard().getGameBoard()[row][column].getCellType().getIdentifier() 
                    + "," + fireEngine.getActionPoint());
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
        this.fireEngineID = parameters[0];
        this.row = Integer.parseInt(parameters[1]);
        this.column = Integer.parseInt(parameters[2]);
    }

}
