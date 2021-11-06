package edu.kit.informatik.ui.commands;

import java.lang.reflect.InvocationTargetException;

import edu.kit.informatik.exceptions.SyntacticException;

/**
 * command enum to get the corresponding instance of the class, in which given command gets executed
 * @author Rodi
 * @version 1.0
 */
public enum Command {
    /**
     * instance for buy-fire-engine command
     */
    BUYFIREENGINE("buy-fire-engine", BuyFireEngine.class),
    /**
     * instance for extinguish command
     */
    EXTINGUISH("extinguish", Extinguish.class),
    /**
     * instance for fire-to-roll command
     */
    FIRETOROLL("fire-to-roll", FireToRoll.class),
    /**
     * instance for move command
     */
    MOVE("move", Move.class),
    /**
     * instance for refill command
     */
    REFILL("refill", Refill.class),
    /**
     * instance for reset command
     */
    RESET("reset", Reset.class),
    /**
     * instance for show-board command
     */
    SHOWBOARD("show-board", ShowBoard.class),
    /**
     * instance for show-field command
     */
    SHOWFIELD("show-field", ShowField.class),
    /**
     * instance for show-player command
     */
    SHOWPLAYER("show-player", ShowPlayer.class),
    /**
     * instance for turn command
     */
    TURN("turn", Turn.class);
    
    private String commandString;
    private Class<? extends UserRequest> commandClass;
    
    
    /**
     * private constructor of enum
     * @param commandString
     * @param commandClass
     */
    private Command(String commandString, Class<? extends UserRequest> commandClass) {
        this.commandString = commandString;
        this.commandClass = commandClass;
    }
    
    /**
     * getter of commandString attribute 
     * @return commandString attribute 
     */
    public String getCommandString() {
        return commandString;
    }
    
    /**
     * setter of commandString attribute
     * @param commandString
     */
    
    public void setCommandString(String commandString) {
        this.commandString = commandString;
    }

    /**
     * getter of commandClass attribute
     * @return class of the command instance
     */
    public Class<? extends UserRequest> getCommandClass() {
        return commandClass;
    }


    /**
     * 
     * @param inputString read with Terminal.readLine in session class
     * @return the instance of the corresponding class
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws SyntacticException
     */
    public static UserRequest getCorrespondingInstance(String inputString) throws InstantiationException, 
        IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
        NoSuchMethodException, SecurityException, SyntacticException {
        for (Command command : Command.values()) {
            if (command.getCommandString().equals(inputString) ) {
                return command.getCommandClass().getDeclaredConstructor().newInstance();
            }
        }
        throw new SyntacticException();
    }

}
