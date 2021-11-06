package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.BoardCell;
import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.ui.Session;

/**
 * class of show-board command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class ShowBoard implements UserRequest {
    
    /**
     * input string must match this regular expression
     */
    final String inputForm = "^(show-board)$";
    
    private Session session;
    
    /**
     * 
     */
    public void execute() {
        FireBreaker game = this.session.getGame();
        String board = "";
        int counter = 1;
        for (BoardCell[] row : game.getGameBoard().getGameBoard()) {
            for (BoardCell cell : row) {
                if (cell.getCellType().isFire()) {
                    board = board + cell.getCellType().getIdentifier();
                } else {
                    board = board + "x";
                }
                if ((counter % game.getGameBoard().getGameBoard()[0].length) != 0) {
                    board = board + ","; 
                }
                counter++;
            }
            board = board + "\n";
        }
        
        Terminal.printLine(board.substring(0, board.length() - 1));
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
     * set attributes ready to execute
     * @param session current session
     * @param parameters extracted from input String
     */
    public void setParametersReady(Session session, String[] parameters) {
        this.session = session;
    }

}
