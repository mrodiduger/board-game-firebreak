package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.BoardCell;
import edu.kit.informatik.ui.Session;

/**
 * class of show-field command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class ShowField implements UserRequest {
    
    /**
     * input string must match this pattern
     */
    final String inputFormat = "^(show-field)\\s\\d+,\\d+$";
    
    private Session session;
    private int row;
    private int column;
    
    /**
     * prints the information of the cell with given row and column indices
     */
    public void execute() throws GameLogicException {
        BoardCell[][] board = this.session.getGame().getGameBoard().getGameBoard();
        if (this.row >= board.length || this.column >= board[0].length) { // < 0 case is checked in regex
            throw new GameLogicException();
        }
        if (board[row][column].getCellType().canFillWater()) {
            Terminal.printLine(board[row][column].getCellType().getIdentifier());
        } else {
            String output = board[row][column].getCellType().getIdentifier();
            for (String id : board[row][column].getFireEngineIDsSorted()) {
                output = output + "," + id;
            }
            Terminal.printLine(output);
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
        this.row = Integer.parseInt(parameters[0]);
        this.column = Integer.parseInt(parameters[1]);
    }

}
