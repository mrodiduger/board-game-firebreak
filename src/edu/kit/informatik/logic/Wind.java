package edu.kit.informatik.logic;

import edu.kit.informatik.exceptions.GameLogicException;

/**
 * the instances of this enum represent the wind blowing in different directions
 * @author Rodi
 * @version 1.0
 */
public enum Wind {
    
    /**
     * wind, that spreads fire northward
     */
    WINDNORTH(2) {
        @Override
        public int[] windSpecificOperation(int[] location) {
            int[] copyLocation = new int[2];
            copyLocation[0] = location[0] - 1; //row - 1
            copyLocation[1] = location[1];
            return copyLocation;
        }
    },
    /**
     * wind, that spreads fire eastward
     */
    WINDEAST(3) {
        @Override
        public int[] windSpecificOperation(int[] location) {
            int[] copyLocation = new int[2];
            copyLocation[0] = location[0];
            copyLocation[1] = location[1] + 1; //column + 1
            return copyLocation;
        }
    },
    /**
     * wind, that spreads fire southward
     */
    WINDSOUTH(4) {
        @Override
        public int[] windSpecificOperation(int[] location) {
            int[] copyLocation = new int[2];
            copyLocation[0] = location[0] + 1; // row + 1
            copyLocation[1] = location[1];
            return copyLocation;
        }
    },
    /**
     * wind, that spreads fire westward
     */
    WINDWEST(5) {
        @Override
        public int[] windSpecificOperation(int[] location) {
            int[] copyLocation = new int[2];
            copyLocation[0] = location[0];
            copyLocation[1] = location[1] - 1; //column - 1
            return copyLocation;
        }
    };
    
    private FireBreaker game;
    private int index;
    
    /**
     * constructor
     * @param index
     */
    private Wind(int index) {
        this.index = index;
    }
    
    /**
     * 
     * @param location
     * @return the location of the cell, which should be effected by fire
     */
    public abstract int[] windSpecificOperation(int[] location);
    

    /**
     * getter of the index attribute
     * @return index attribute
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
     * getter of the game attribute
     * @return game attribute
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
     * 
     * @param index
     * @return wind with the given index
     */
    public static Wind getWindFromIndex(int index) {
        for (Wind wind : Wind.values()) {
            if (wind.getIndex() == index) {
                return wind;
            }
        }
        return null;
    }
    
    /**
     * simulates wind on the cell with the given location
     * @param location of the cell, on which the wind is simulated
     * @throws GameLogicException
     */
    public void simulateWind(int[] location) throws GameLogicException {
        BoardCell[][] board = this.getGame().getGameBoard().getGameBoard();
        int[] newLocation = this.windSpecificOperation(location);
        int row = newLocation[0];
        int column = newLocation[1];

        if (row >= 0 && row < board.length && column >= 0 && column < board[0].length) {
            if (!board[row][column].isEffectedByFire()) {
                board[row][column].setCellType(board[row][column].getCellType().onFire());
                board[row][column].setEffectedByFire(true);
                if (board[row][column].getCellType().equals(CellType.STRONGFIRE)) {
                    if (!board[row][column].getCurrentFireEngines().isEmpty()) {
                        for (FireEngine engine : board[row][column].getCurrentFireEngines()) {
                            engine.setBoardCell(null);
                            engine.setInGame(false);
                            engine.getPlayer().checkIfPlayerIsInGame();
                        }
                        board[row][column].getCurrentFireEngines().clear();
                    }
                }
            }

        }
        
    }
    
    
    
    
    
    
    
    
}
