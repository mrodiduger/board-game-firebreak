package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.InitializationException;
import edu.kit.informatik.logic.FireBreaker;

/**
 * Main class of the program
 * @author Rodi
 * @version 1.0
 */
public final class Main {
    
    private Main() {
        
    }

    /**
     * the program starts here
     * @param args
     */
    public static void main(String[] args) {
        
        
        try {
            if (args.length != 1) {
                throw new InitializationException();
            }
            GameGenerator gameGenerator = new GameGenerator(args[0]);
            FireBreaker game = gameGenerator.getGame();
            Session session = new Session(game, args[0]);
            session.begin();
        } catch (InitializationException | NumberFormatException e) {
            Terminal.printError(e.getMessage());
        }
    }
}
