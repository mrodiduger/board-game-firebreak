package edu.kit.informatik.ui;

import java.lang.reflect.InvocationTargetException;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.exceptions.SyntacticException;
import edu.kit.informatik.ui.commands.Command;
import edu.kit.informatik.ui.commands.UserRequest;

/**
 * this class parses the input string
 * @author Rodi
 * @version 1.0
 */
public class Input {
    
    private Session session;
    private UserRequest userRequest;
    private String[] parameters;
    
    /**
     * constructor
     * @param inputString
     * @param session
     * @throws SyntacticException
     */
    public Input(String inputString, Session session) throws SyntacticException {
        this.session = session;
        this.parseInputString(inputString);
        
    }
    
    /**
     * getter of the session attribute
     * @return session
     */
    public Session getSession() {
        return session;
    }
    
    /**
     * setter of the session attribute
     * @param session
     */
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    /**
     * getter of the userRequest attribute
     * @return corresponding userRequest
     */
    
    public UserRequest getUserRequest() {
        return userRequest;
    }
    /**
     * 
     * @param userRequest
     */
    
    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
    
    /**
     * getter of the parameters attribute
     * @return parameters
     */
    public String[] getParameters() {
        return parameters;
    }
    
    /**
     * setter of the parameters attribute
     * @param parameters
     */
    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
    
    private void parseInputString(String inputString) throws SyntacticException {
        String[] inputArray = inputString.split(" "); 
        if (inputArray.length == 2) {
            this.setParameters(inputArray[1].split(","));
        }
        
        try {
            if (inputArray.length == 0) {
                throw new SyntacticException();
            }
            //gets the corresponding instance of a command
            UserRequest userRequest = Command.getCorrespondingInstance(inputArray[0]); 
            userRequest.hasCorrectForm(inputString);
            userRequest.setParametersReady(this.session, this.parameters);
            this.userRequest = userRequest;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException | SyntacticException e) {
            Terminal.printError(e.getMessage());
        }
    }
    
    

}
