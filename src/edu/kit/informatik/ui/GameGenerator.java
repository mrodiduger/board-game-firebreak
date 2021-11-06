package edu.kit.informatik.ui;

import edu.kit.informatik.exceptions.InitializationException;
import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.GameBoard;

/**
 * this class generates a game with the given parameter string
 * @author Rodi
 * @version 1.0
 */
public class GameGenerator {
    
    /**
     * args[0] must match this pattern
     */
    static String inputRegex = "^((\\d)+),((\\d)+),"
            + "(([ABCDL+*dw]|(A0)|(B0)|(C0)|(D0)),)+([ABCDL+*dw]|((A0)|(B0)|(C0)|(D0)))$";
        
    private FireBreaker game;
    private int[] size = new int[2];
        
    /**
     * constructor, generates a game and initialize game attribute with this generated game
     * @param inputString
     * @throws InitializationException if game can not be initialized
     */
    public GameGenerator(String inputString) throws InitializationException {
        
        if (!inputString.matches(inputRegex)) {
            throw new InitializationException();
        }
        
        String[] inputStringArray = inputString.split(",");
        int[] size = setSize(inputStringArray);
        if (inputStringArray.length != (size[0] * size[1] + 2)) {
            throw new InitializationException();
        }
        String[][] board = new String[size[0]][size[1]];
        fillBoard(inputStringArray, board);
        controlBoard(board);
        this.game = new FireBreaker(new GameBoard(board));
        this.game.getGameBoard().setGame(this.game);
    }
    
    
    /**
     * getter of the game attribute
     * @return generated game
     */
    public FireBreaker getGame() {
        return this.game;
    }
    
    private int[] setSize(String[] inputString) throws InitializationException, NumberFormatException {
        int sizeRow = Integer.parseInt(inputString[0]);
        int sizeColumn = Integer.parseInt(inputString[1]);
        if (sizeRow < 5 || sizeColumn < 5) {
            throw new InitializationException("game board needs to be greater than or equal to 5x5");
        } else if ((sizeRow % 2) != 1 || (sizeColumn % 2) != 1 ) {
            throw new InitializationException("row or column size of the board needs to be odd");
        }
        int[] size = {sizeRow, sizeColumn};
        this.size = size;
        return size;
    }
    
    private void fillBoard(String[] inputArray, String[][] board) throws InitializationException {
        boolean isThereHeavyFire = false;
        boolean isThereWeakFire = false;
        int pondCounter = 0;
        int row = 0;
        for (int i = 2; i < inputArray.length; i = i + this.size[1]) {
            int column = 0;
            for (int j = i; j < i + this.size[1]; j++) {
                if (inputArray[j].equals("+")) {
                    isThereWeakFire = true;
                }
                if (inputArray[j].equals("*")) {
                    isThereHeavyFire = true;
                }
                if (inputArray[j].equals("L")) {
                    pondCounter++;
                }
                board[row][column] = inputArray[j];
                column++;
            }
            row++;
        }
        if (pondCounter != 4) {
            throw new InitializationException("there are more than 4 ponds");
        }
        
        if (!isThereHeavyFire || !isThereWeakFire) {
            throw new InitializationException("there is no fire");
        }
    }
    
    
    
    private void controlBoard(String[][] board) throws InitializationException {
        if (!board[0][0].equals("A") || !board[0][this.size[1] - 1].equals("D") 
                || !board[this.size[0] - 1][0].equals("C")
                || !board[this.size[0] - 1][this.size[1] - 1].equals("B")) {
            throw new InitializationException();
        }
        if (!board[1][1].equals("A0") || !board[1][this.size[1] - 2].equals("D0") 
                || !board[this.size[0] - 2][1].equals("C0") 
                || !board[this.size[0] - 2][this.size[1] - 2].equals("B0")) {
            throw new InitializationException();
        }
        if (!board[0][(this.size[1] - 1) / 2].equals("L") || !board[(this.size[0] - 1) / 2][0].equals("L") 
                || !board[(this.size[0] - 1) / 2][this.size[1] - 1].equals("L") 
                || !board[this.size[0] - 1][(this.size[1] - 1) / 2].equals("L")) { 
            throw new InitializationException();
        }
    }
    


   
}
