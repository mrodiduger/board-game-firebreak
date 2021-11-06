package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.comparators.FireEngineComparator;
import edu.kit.informatik.exceptions.GameLogicException;

/**
 * instances of this enum represent each player, also A,B,C,D
 * @author Rodi
 * @version 1.0
 */
public enum Player {
    /**
     * instance of the player A
     */
    PLAYER_A("A") {
        @Override
        public Player nextPlayer() {
            return PLAYER_B;
        }

    },
    /**
     * instance of the player B
     */
    PLAYER_B("B") {
        @Override
        public Player nextPlayer() {
            return PLAYER_C;
        }

    },
    
    /**
     * instance of the player C
     */
    PLAYER_C("C") {
        @Override
        public Player nextPlayer() {
            return PLAYER_D;
        }
       
    },
    
    /**
     * instance of the player D
     */
    PLAYER_D("D") {
        @Override
        public Player nextPlayer() {
            return PLAYER_A;
        }
    };
    
    
    private List<FireEngine> fireEngines = new ArrayList<FireEngine>();
    private int reputationPoint;
    private String identifier;
    private boolean hasPlayed;
    private boolean isInGame;
    private FireBreaker game;
    private BoardCell stationCell;
    
    
    private Player(String identifier) {
        this.identifier = identifier;
        this.reputationPoint = 0;
        this.fireEngines.add(new FireEngine(this, 0));
        this.hasPlayed = false;
        this.isInGame = true;
    }
    /**
     * getter of the game attribute
     * @return game
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
     * getter of the fireEngines attribute
     * @return list of fire engines sorted
     */
    public List<FireEngine> getFireEngines() {
        Collections.sort(fireEngines, new FireEngineComparator());
        return fireEngines;
    }

    /**
     * getter of the reputationPoint attribute
     * @return reputationPoint of the player
     */
    public int getReputationPoint() {
        return reputationPoint;
    }

    /**
     * setter of the reputationPoint attribute
     * @param reputationPoint
     */
    public void setReputationPoint(int reputationPoint) {
        this.reputationPoint = reputationPoint;
    }

    /**
     * getter of the identifier attribute
     * @return identifier of the player
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * getter of hasPlayed attribute
     * @return hasPlayed 
     */
    public boolean hasPlayed() {
        return hasPlayed;
    }

    /**
     * setter of hasPlayed attribute
     * @param hasPlayed
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }
    
    /**
     * getter of the isInGame attribute
     * @return true if player is still in game, false if not
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
     * getter of the stationCell attribute
     * @return stationCell
     */
    
    public BoardCell getStationCell() {
        return stationCell;
    }
    /**
     * setter of the stationCell attribute
     * @param stationCell
     */
    
    public void setStationCell(BoardCell stationCell) {
        this.stationCell = stationCell;
    }
    
    /**
     * @return next Player of the player, which calls the method
     */
    
    public abstract Player nextPlayer();
    
    /**
     * 
     * @param identifier
     * @return Player with the given identifier
     */
    public static Player getPlayerFromIdentifier(String identifier) {
        for (Player player : Player.values()) {
            if (player.getIdentifier().equals(identifier)) {
                return player;
            }
        }
        return null;
    }
    /**
     * resets attributes of the players and its engines, that are turn based
     */
    
    public static void resetTurnBasedAttributes() {
        for (Player player : Player.values()) {
            player.setHasPlayed(false);
            for (FireEngine fireEngine : player.getFireEngines()) {
                fireEngine.setActionPoint(3);
                fireEngine.getExtinguishedLocations().clear();
                fireEngine.setHasActed(false);
            }
        }
    }
    /**
     * checks if the game is on 
     * @return true if there are engines left in the game, false if not
     */
    
    public static boolean thereAreEnginesLeft() {
        boolean thereAreEnginesLeft = false;
        for (Player player : Player.values()) {
            for (FireEngine engine : player.getFireEngines()) {
                if (engine.isInGame()) {
                    thereAreEnginesLeft = true;
                    break;
                }
            }
        }
        return thereAreEnginesLeft;
    }
    /**
     * helper method for getFireEngineFromID
     * @param index
     * @return
     */
    
    private FireEngine getFireEngineFromIndex(int index) {
        for (FireEngine fireEngine : this.getFireEngines()) {
            if (fireEngine.getIndex() == index) {
                return fireEngine;
            }
        }
        return null;
    }
    
    /**
     * buys a fire engine and place it on the cell with given row and column indices
     * @param i row index
     * @param j column index
     * @throws GameLogicException
     */
    public void buyFireEngine(int i, int j) throws GameLogicException {  
        if (getReputationPoint() >= 5) {
            int index = this.getFireEngines().get(this.getFireEngines().size() - 1).getIndex() + 1;
            FireEngine newFireEngine = new FireEngine(this, index);
            newFireEngine.setBoardCell(this.getGame().getGameBoard().getGameBoard()[i][j]);
            this.getGame().getGameBoard().getGameBoard()[i][j].getCurrentFireEngines().add(newFireEngine);
            this.getFireEngines().add(newFireEngine);
            this.setReputationPoint(this.getReputationPoint() - 5);
            return;
        }
        throw new GameLogicException("not enough reputation points");
    }
    
    /**
     * 
     * @param identifier
     * @return fireEngine with the given identifier(ID)
     * @throws GameLogicException
     */
    public static FireEngine getFireEngineFromID(String identifier) throws GameLogicException {
        String[] idAndIndex = identifier.split("(?<=\\d)(?=\\D)|(?=\\d)(?<=\\D)"); // seperate number and character
        Player player = getPlayerFromIdentifier(idAndIndex[0]);
        if (player != null) {
            FireEngine fireEngine = player.getFireEngineFromIndex(Integer.parseInt(idAndIndex[1]));
            if (fireEngine != null) {
                return fireEngine;
            } else {
                throw new GameLogicException("there is no such fire engine.");
            }
        }
        throw new GameLogicException("there is no such player.");
    }
    
    /**
     * 
     * @return list of cells around the station of the player, which calls the method
     */
    public List<BoardCell> getStationSurrounding() {
        List<BoardCell> cells = new ArrayList<BoardCell>();
        GameBoard board = this.getGame().getGameBoard();
        int row = this.getStationCell().getLocation()[0];
        int column = this.getStationCell().getLocation()[1];
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < board.getRowSize() && j >= 0 && j < board.getColumnSize()) {
                    if (!board.getGameBoard()[i][j].getCellType().isFire() 
                            && !board.getGameBoard()[i][j].getCellType().canFillWater()) {
                        cells.add(board.getGameBoard()[i][j]);
                    }
                }
            }
        }
        return cells;
    }
    /**
     * checks if the player is still in game, by iterating its fire engines
     */
    
    public void checkIfPlayerIsInGame() {
        this.setInGame(false);
        for (FireEngine engine : this.getFireEngines()) {
            if (engine.isInGame()) {
                this.setInGame(true);
            }
        }
    }
    /**
     * resets the attributes of all players
     */
    
    public static void resetPlayers() {
        for (Player player : Player.values()) {
            player.getFireEngines().clear();
            player.reputationPoint = 0;
            player.fireEngines.add(new FireEngine(player, 0));
            player.hasPlayed = false;
            player.isInGame = true;
        }
    }


}
