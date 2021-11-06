package edu.kit.informatik.ui.commands;

import edu.kit.informatik.exceptions.GameLogicException;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.ui.Session;

/**
 * an interface to execute commands easily
 * @author Rodi
 * @version 1.0
 */
public interface UserRequest {
    
    
    /**
     * executes the command
     * @throws GameLogicException if a semantic problem occurs
     */
    void execute() throws GameLogicException;
    
    /**
     * 
     * @param inputString read with Terminal.readLine
     * @throws SyntacticException if inputString does not match with the required pattern
     */
    void hasCorrectForm(String inputString) throws SyntacticException;
    
    /**
     * 
     * @param session current session which stores game as an attribute
     * @param parameters extracted from the input string
     */
    void setParametersReady(Session session, String[] parameters);
    
    

}
