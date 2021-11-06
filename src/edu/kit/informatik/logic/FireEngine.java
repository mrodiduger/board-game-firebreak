package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.exceptions.GameLogicException;
/**
 * this class represent the fire engine
 * @author Rodi
 * @version 1.0
 */

public class FireEngine {
    
    private Player player;
    private BoardCell boardCell;
    private int index;
    private int actionPoint;
    private int waterLevel;
    private boolean hasActed = false;
    private boolean isInGame = true;
    private List<int[]> extinguishedLocations = new ArrayList<int[]>(); //reset every round
    
    /**
     * constructor with default values of attributes
     * @param player
     * @param index
     */
    public FireEngine(Player player, int index) {
        this.player = player;
        this.index = index;
        this.actionPoint = 3;
        this.waterLevel = 3;
    }
    
    /**
     * identifier of a fire engine is (PlayerName + index)
     * @return identifier of the fire engine
     */
    public String getIdentifier() {
        return (this.getPlayer().getIdentifier() + this.getIndex());
    }

    /**
     * getter of the player attribute
     * @return player of the fire engine
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * setter of the player attribute
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * getter of the index attribute
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * setter of the index attribute
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * getter of the actionPoint attribute
     * @return actionPoint of the engine
     */
    public int getActionPoint() {
        return actionPoint;
    }

    /**
     * setter of the actionPoint attribute
     * @param actionPoint
     */
    public void setActionPoint(int actionPoint) {
        this.actionPoint = actionPoint;
    }

    /**
     * getter of the waterLevel attribute
     * @return water level of the engine
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * setter of the waterLevel attribute
     * @param waterLevel
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    /**
     * getter of the boardCell attribute
     * @return board cell, on which the engine stands
     */
    public BoardCell getBoardCell() {
        return boardCell;
    }

    /**
     * setter of the boardCell attribute
     * @param boardCell
     */
    public void setBoardCell(BoardCell boardCell) {
        this.boardCell = boardCell;
    }
    
    /**
     * getter of the isInGame attribute
     * @return isInGame
     */
    public boolean isInGame() {
        return isInGame;
    }

    /**
     * setter of the isInGame attribute
     * @param isInGame
     */
    public void setInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
    
    /**
     * getter of the extinguishedLocations attribute
     * @return list of extinguished locations
     */
    public List<int[]> getExtinguishedLocations() {
        return extinguishedLocations;
    }

    /**
     * getter of the hasActed attribute
     * @return hasActed
     */
    public boolean hasActed() {
        return hasActed;
    }

    /**
     * setter of the hasActed attribute
     * @param hasActed
     */
    public void setHasActed(boolean hasActed) {
        this.hasActed = hasActed;
    }

    /**
     * this method is a helper for show-player command
     * @return string of information of the engine in in assignment specified order
     */
    public String getInformation() {
        String info = this.getIdentifier() + "," + this.getWaterLevel() + "," + this.getActionPoint()
            + "," + this.getBoardCell().getLocation()[0] + "," + this.getBoardCell().getLocation()[1];
        return info; // info = id + water + action + row + column
    }

    /**
     * sets the water level as 3
     * @throws GameLogicException if water tank is full or if there are not enough action points
     * or if the engine is not adjacent to a water source
     */
    public void fillWaterTank() throws GameLogicException {
        if (this.getWaterLevel() == 3) {
            throw new GameLogicException("water tank is full");
        }
        if (this.getBoardCell().isAdjacentToWater()) {
            if (this.getActionPoint() != 0) {
                this.setActionPoint(this.getActionPoint() - 1);
                this.waterLevel = 3;
                this.setHasActed(true);
            } else {
                throw new GameLogicException("not enough action points");
            }
            
        } else {
            throw new GameLogicException("engine is not adjacent to a water source");
        }
        
    }
    
    /**
     * moves the fire engine to the cell with given row and column indices
     * @param row
     * @param column
     * @throws GameLogicException
     */
    public void move(int row, int column) throws GameLogicException {

        if (!this.isInGame()) {
            throw new GameLogicException("this engine is not in the game!");
        }
        if (this.hasActed()) {
            throw new GameLogicException("can not move after an action!");
        }
        if (!this.getBoardCell().getBoard().getGame().getCurrentPlayer().equals(this.getPlayer())) {
            throw new GameLogicException("you can not move this figure");
        }
        if (this.getActionPoint() == 0) {
            throw new GameLogicException("not enough action points!");
        }
       
        BoardCell[][] board = this.getBoardCell().getBoard().getGameBoard();
        if (row < 0 || column < 0 || row >= board.length || column >= board[0].length) {
            throw new GameLogicException();
        }
        if (board[row][column].getCellType().isFire()) {
            throw new GameLogicException();
        }
        if (row == this.getBoardCell().getLocation()[0] && column == this.getBoardCell().getLocation()[1]) {
            throw new GameLogicException();
        }
        outer:
        if (!this.getBoardCell().getReachableCells().contains(board[row][column])) {
            for (BoardCell cell : this.getBoardCell().getReachableCells()) {
                for (BoardCell cellSecond : cell.getReachableCells()) {
                    if (cellSecond.equals(board[row][column])) {
                        break outer;
                    }
                }
            }
            throw new GameLogicException("can not move to this cell");
        }
        this.setActionPoint(this.getActionPoint() - 1); // actionPoint - 1
        this.getBoardCell().getCurrentFireEngines().remove(this); 
        this.setBoardCell(board[row][column]);
        board[row][column].getCurrentFireEngines().add(this);
    }
    
    /**
     * extinguishes the cell with the given row and column indices
     * @param row
     * @param column
     * @throws GameLogicException
     */
    public void extinguish(int row, int column) throws GameLogicException {
        for (int[] location : this.getExtinguishedLocations()) {
            if (location[0] == row && location[1] == column) {
                throw new GameLogicException("can not extinguish a cell twice");
            }
        }
        if (!this.isInGame()) {
            throw new GameLogicException("this engine is not in the game!");
        }
        if (!this.getBoardCell().getBoard().getGame().getCurrentPlayer().equals(this.getPlayer())) {
            throw new GameLogicException("you can not move this figure");
        }
        
        int currentRow = this.getBoardCell().getLocation()[0];
        int currentCol = this.getBoardCell().getLocation()[1];
        if (row == currentRow) {
            if (!((column == currentCol - 1) || (column == currentCol + 1))) {
                throw new GameLogicException("target cell is not adjacent to engine.");
            }
        } else if (column == currentCol) {
            if (!((row == currentRow - 1) || (row == currentRow + 1))) {
                throw new GameLogicException("target cell is not adjacent to engine.");
            }
        } else {
            throw new GameLogicException("target cell is not adjacent to engine.");
        }
        if (this.getActionPoint() == 0) {
            throw new GameLogicException("not enough action points.");
        }
        if (this.getWaterLevel() == 0) {
            throw new GameLogicException("not enough water");
        }
        try {
            if (this.getBoardCell().getBoard().getGameBoard()[row][column].getCellType().isFire()) {
                this.getPlayer().setReputationPoint(this.getPlayer().getReputationPoint() + 1);
            }
            this.getBoardCell().getBoard().getGameBoard()[row][column].setCellType(this.getBoardCell().getBoard().
                    getGameBoard()[row][column].getCellType().extinguish()); //extinguish
            this.setActionPoint(this.getActionPoint() - 1); //actionPoint - 1
            this.setWaterLevel(this.getWaterLevel() - 1); //waterLevel - 1
            this.setHasActed(true); // hasActed
            int[] extinguishedLocation = {row, column};
            this.getExtinguishedLocations().add(extinguishedLocation);
        } catch (IndexOutOfBoundsException e) {
            throw new GameLogicException("there is no such cell");
        } 
    }
    
    
    

    
}
