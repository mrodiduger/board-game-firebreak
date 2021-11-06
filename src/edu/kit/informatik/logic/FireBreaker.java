package edu.kit.informatik.logic;

import java.util.List;

import edu.kit.informatik.exceptions.GameLogicException;

/**
 * this class represents the game itself
 * @author Rodi
 * @version 1.0
 */
public class FireBreaker {
    
    private GameBoard gameBoard;
    private Player currentPlayer = Player.PLAYER_A;
    private Turn currentTurn = new Turn(Player.PLAYER_A, true);
    private boolean isGameFinished;

    /**
     * constructor
     * @param gameBoard
     */
    public FireBreaker(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        for (Player player : Player.values()) {
            player.setGame(this);
        }
        Player.PLAYER_A.setStationCell(this.gameBoard.getGameBoard()[0][0]);
        Player.PLAYER_B.setStationCell(this.gameBoard.getGameBoard()[this.gameBoard.getRowSize() - 1]
                [this.gameBoard.getColumnSize() - 1]);
        Player.PLAYER_C.setStationCell(this.gameBoard.getGameBoard()[this.gameBoard.getRowSize() - 1][0]);
        Player.PLAYER_D.setStationCell(this.gameBoard.getGameBoard()[0][this.gameBoard.getColumnSize() - 1]);
    }


    /**
     * getter
     * @return gameBoard
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }


    /**
     * setter
     * @param gameBoard
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * getter
     * @return currentTurn
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }


    /**
     * setter
     * @param currentTurn
     */
    public void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }


    /**
     * getter
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * setter
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    /**
     * getter
     * @return isGameFinished
     */
    public boolean isGameFinished() {
        return isGameFinished;
    }


    /**
     * setter
     * @param isGameFinished
     */
    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }


    /**
     * if everybody has played, a new round begins
     */
    public void turn() {
        this.getCurrentPlayer().setHasPlayed(true);
        boolean hasEverybodyPlayed = true;
        for (Player player : Player.values()) {
            if (player.isInGame()) {
                if (!player.hasPlayed()) {
                    hasEverybodyPlayed = false;
                    break;
                }
            }
        }
        if (hasEverybodyPlayed) {
            getCurrentTurn().updateTurn();
            setCurrentPlayer(getCurrentTurn().getFirstPlayer());
            Player.resetTurnBasedAttributes();
            this.getGameBoard().resetIsEffectedByFire();
        } else {
            //this.getCurrentPlayer().setHasPlayed(true);
            do {
                this.setCurrentPlayer(getCurrentPlayer().nextPlayer());
            } while (!getCurrentPlayer().isInGame());
        }
    }
    
    /**
     * buys fire engine for the current player and place it on the cell with given row and column indices
     * @param i index of row
     * @param j index of column
     * @throws GameLogicException
     */
    public void buyFireEngine(int i, int j) throws GameLogicException {
        if (i < 0 || i >= getGameBoard().getGameBoard().length || j < 0 
                || j >= getGameBoard().getGameBoard()[0].length) {
            throw new GameLogicException();
        }
        if (!this.getCurrentPlayer().getStationSurrounding().contains(getGameBoard().getGameBoard()[i][j])) {
            throw new GameLogicException();
        }
        this.getCurrentPlayer().buyFireEngine(i, j);
    }
    
    /**
     * simulates wind of the given direction
     * if (number == 1) simulates winds of all directions
     * if (number == 6) does not simulate wind
     * @param number
     * @throws GameLogicException
     */
    public void simulateWind(int number) throws GameLogicException {
        if (this.getCurrentTurn().isWindSimulated()) {
            throw new GameLogicException("can not simulate wind in a round twice");
        }
        List<BoardCell> weakBurningCells = this.getGameBoard().getBurningCells().get(0);
        List<BoardCell> strongBurningCells = this.getGameBoard().getBurningCells().get(1);
        if (number == 1) {
            for (Wind wind : Wind.values()) {
                for (BoardCell cell : strongBurningCells) {
                    wind.setGame(this);
                    wind.simulateWind(cell.getLocation());
                }
            }
            for (BoardCell cell : weakBurningCells) {
                cell.setCellType(cell.getCellType().onFire());
                if (!cell.getCurrentFireEngines().isEmpty()) {
                    for (FireEngine engine : cell.getCurrentFireEngines()) {
                        engine.setBoardCell(null);
                        engine.setInGame(false);
                        engine.getPlayer().checkIfPlayerIsInGame();
                    }
                }
            }
            this.getCurrentTurn().setWindSimulated(true);
        } else if (number == 6) {
            this.getCurrentTurn().setWindSimulated(true);
        } else {
            Wind wind = Wind.getWindFromIndex(number);
            wind.setGame(this);
            for (BoardCell cell : strongBurningCells) {
                wind.simulateWind(cell.getLocation());
            }
            for (BoardCell cell : weakBurningCells) {
                cell.setCellType(cell.getCellType().onFire());
                if (!cell.getCurrentFireEngines().isEmpty()) {
                    for (FireEngine engine : cell.getCurrentFireEngines()) {
                        engine.setBoardCell(null);
                        engine.setInGame(false);
                        engine.getPlayer().checkIfPlayerIsInGame();
                    }
                }
            }
            this.getCurrentTurn().setWindSimulated(true);
        }
    }
    
    
    
    
    
}
