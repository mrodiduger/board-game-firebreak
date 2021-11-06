package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.exceptions.InitializationException;

/**
 * this class represent the game board
 * @author Rodi
 * @version 1.0
 */
public class GameBoard {
    private FireBreaker game;
    private BoardCell[][] gameBoard;
    private int rowSize;
    private int columnSize;
    
    /**
     * constructor
     * @param stringBoard
     * @throws InitializationException if the board can not get initialized
     */
    public GameBoard(String[][] stringBoard) throws InitializationException {
        this.gameBoard = new BoardCell[stringBoard.length][stringBoard[0].length];
        this.fillBoard(stringBoard);
        this.rowSize = this.gameBoard.length;
        this.columnSize = this.gameBoard[0].length;
        this.setWaterAdjacency();
    }
    
    /**
     * getter of the gameBoard attribute
     * @return gameBoard
     */
    public BoardCell[][] getGameBoard() {
        return gameBoard;
    }
    
    /**
     * getter of the game attribute
     * @return game of this game board 
     */
    public FireBreaker getGame() {
        return game;
    }

    /**
     * setter of the game attribute
     * @param game
     */
    public void setGame(FireBreaker game) {
        this.game = game;
    }

    /**
     * getter of the rowSize attribute
     * @return row size of the board
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * getter of the columnSize attribute
     * @return column size of the board
     */
    public int getColumnSize() {
        return columnSize;
    }

    //this method turns a stringBoard to a board of boardCells
    private void fillBoard(String[][] stringBoard) throws InitializationException {
        for (int i = 0; i < stringBoard.length; i++) {
            label:
            for (int j = 0; j < stringBoard[0].length; j++) {
                for (CellType type : CellType.values()) {
                    if (stringBoard[i][j].equals(type.getIdentifier())) {
                        this.gameBoard[i][j] = new BoardCell(this, type, i, j);
                        continue label;
                    }
                }
                for (Player p : Player.values()) {
                    if (stringBoard[i][j].equals(p.getIdentifier() + "0")) {
                        this.gameBoard[i][j] = new BoardCell(this, CellType.DRY, i, j);
                        p.getFireEngines().get(0).setBoardCell(this.gameBoard[i][j]);
                        this.gameBoard[i][j].getCurrentFireEngines().add(p.getFireEngines().get(0));
                        continue label;
                    }
                }
            }
        }
    }

    //this method sets the cells around water as adjacentToWater
    private void setWaterAdjacency() {
        for (int i = 0; i < this.rowSize; i++) {
            for (int j = 0; j < this.columnSize; j++) {
                if (getGameBoard()[i][j].getCellType().canFillWater()) {
                    getGameBoard()[i][j].setAdjacentCellsAdjacentToWater();
                }
            }
        }
    }
    
    /**
     * resets all of the board cells "not effected by fire" for fire-to-roll command
     */
    public void resetIsEffectedByFire() {
        for (BoardCell[] row : this.getGameBoard()) {
            for (BoardCell cell : row) {
                cell.setEffectedByFire(false);
            }
        }
    }
    
    /**
     * 
     * @return a list of list of boardcells, 0th index is the list of slighlty burning cells
     * 1th index is the list of heavily burning cells
     */
    public List<List<BoardCell>> getBurningCells() {
        List<BoardCell> strongBurningCells = new ArrayList<BoardCell>();
        List<BoardCell> weakBurningCells = new ArrayList<BoardCell>();
        for (BoardCell[] row : this.getGameBoard()) {
            for (BoardCell cell : row) {
                if (cell.getCellType().equals(CellType.STRONGFIRE)) {
                    strongBurningCells.add(cell);
                } else if (cell.getCellType().equals(CellType.WEAKFIRE)) {
                    weakBurningCells.add(cell);
                }
            }
        }
        List<List<BoardCell>> burningCells = new ArrayList<List<BoardCell>>();
        burningCells.add(weakBurningCells); //index 0
        burningCells.add(strongBurningCells); //index 1
        return burningCells;
    }
    
    
    
    


    
    
}
