package edu.kit.informatik.ui.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.InitializationException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.GameGenerator;
import edu.kit.informatik.ui.Session;

/**
 * class of reset command, in which the command gets executed
 * @author Rodi
 * @version 1.0
 */
public class Reset implements UserRequest {
    
    /**
     * input string must match this regular expression
     */
    final String inputForm = "^(reset)$";
    
    private Session session;
    
    /**
     * resets the game by generating a new game with the same parameter
     * and reseting the players
     */
    public void execute() {
        Player.resetPlayers();
        GameGenerator resetGame;
        try {
            resetGame = new GameGenerator(this.session.getFirstInitializationArgs());
            this.session.setGame(resetGame.getGame());
        } catch (InitializationException e) {
            Terminal.printError(e.getMessage());
        }
        Terminal.printLine("OK");
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
