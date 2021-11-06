package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.comparators.FireEngineComparator;

/**
 * this class represents a board cell
 * @author Rodi
 * @version 1.0
 */
public class BoardCell {
    
    private GameBoard board;
    private int[] location = new int[2];
    private CellType cellType;
    private List<FireEngine> currentFireEngines = new ArrayList<FireEngine>();
    private boolean isAdjacentToWater;
    private boolean isEffectedByFire; //reset every turn;
    
    /**
     * constructor
     * @param board
     * @param cellType
     * @param rowLocation
     * @param colLocation
     */
    public BoardCell(GameBoard board, CellType cellType, int rowLocation, int colLocation) {
        this.board = board;
        this.cellType = cellType;
        this.location[0] = rowLocation;
        this.location[1] = colLocation;
    }

    /**
     * getter
     * @return board
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * setter 
     * @param board
     */
    public void setBoard(GameBoard board) {
        this.board = board;
    }

    /**
     * getter 
     * @return cellType
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * setter
     * @param cellType
     */
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    /**
     * getter
     * @return currentFireEngines
     */
    public List<FireEngine> getCurrentFireEngines() {
        Collections.sort(currentFireEngines, new FireEngineComparator());
        return currentFireEngines;
    }


    /**
     * getter
     * @return location
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * setter
     * @param location
     */
    public void setLocation(int[] location) {
        this.location = location;
    }
    
    /**
     * getter 
     * @return isAdjacentToWater
     */
    public boolean isAdjacentToWater() {
        return isAdjacentToWater;
    }

    /**
     * setter
     * @param isAdjacentToWater
     */
    public void setAdjacentToWater(boolean isAdjacentToWater) {
        this.isAdjacentToWater = isAdjacentToWater;
    }

    /**
     * getter
     * @return isEffectedByFire
     */
    public boolean isEffectedByFire() {
        return isEffectedByFire;
    }

    /**
     * setter
     * @param isEffectedByFire
     */
    public void setEffectedByFire(boolean isEffectedByFire) {
        this.isEffectedByFire = isEffectedByFire;
    }
    
    /**
     * this is a helper method for show-field command
     * @return list of identifiers of fire engines standing on the cell
     */
    public List<String> getFireEngineIDsSorted() {
        List<String> fireEngineIDs = new ArrayList<String>();
        for (FireEngine engine : this.getCurrentFireEngines()) {
            fireEngineIDs.add(engine.getIdentifier());
        }
        Collections.sort(fireEngineIDs);
        return fireEngineIDs;
    }

    /**
     * to determine the reachable cells for move method
     * @return list of reachable cells
     */
    public List<BoardCell> getReachableCells() {
        List<BoardCell> reachableCells = new ArrayList<BoardCell>();
        int row = this.location[0];
        int column = this.location[1];
        for (int i = row - 1; i <= row + 1; i = i + 2) {
            if (i >= 0 && i < this.getBoard().getGameBoard().length) {
                BoardCell neighbourCell = this.getBoard().getGameBoard()[i][column];
                if (neighbourCell.getCellType().isPassable()) {
                    reachableCells.add(neighbourCell);
                }
            }
        }
        for (int j = column - 1; j <= column + 1; j = j + 2) {
            if (j >= 0 && j < this.getBoard().getGameBoard()[0].length) {
                BoardCell neighbourCell = this.getBoard().getGameBoard()[row][j];
                if (neighbourCell.getCellType().isPassable()) {
                    reachableCells.add(neighbourCell);
                }
            }
        }
        return reachableCells;
    }
    
    /**
     * only to use in GameBoard class to setWaterAdjacency
     */
    public void setAdjacentCellsAdjacentToWater() {
        int row = this.getLocation()[0];
        int column = this.getLocation()[1];
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.getBoard().getRowSize() && j >= 0 && j < this.getBoard().getColumnSize()) {
                    this.getBoard().getGameBoard()[i][j].setAdjacentToWater(true);
                }
            }
        }
    }
    
    
    
    
    

}
