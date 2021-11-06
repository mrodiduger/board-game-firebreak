package edu.kit.informatik.logic;

import edu.kit.informatik.exceptions.GameLogicException;

/**
 * instances of this class represents the different kind of cell types
 * @author Rodi
 * @version 1.0
 */
public enum CellType {
    
    /**
     * instance of a dry forest
     */
    DRY("d", false, true, false) {
        @Override
        public CellType extinguish() {
            return WET;
        }
        
        @Override
        public CellType onFire() {
            return WEAKFIRE;
        }
    },
    /**
     * instance of a wet forest
     */
    WET("w", false, true, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return DRY;
        }
    },
    /**
     * instance of a slightly burning field
     */
    WEAKFIRE("+", false, true, true) {
        @Override
        public CellType extinguish() {
            return WET;
        }
        
        @Override
        public CellType onFire() {
            return STRONGFIRE;
        }
    },
    /**
     * instance of a heavily burning field
     */
    STRONGFIRE("*", false, false, true) {
        @Override
        public CellType extinguish() {
            return WEAKFIRE;
        }
        
        @Override
        public CellType onFire() {
            return STRONGFIRE;
        }
    },
    /**
     * instance of a pond(water source)
     */
    LAKE("L", true, false, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return LAKE;
        }
    },
    /**
     * instance of the station A
     */
    STATION_A("A", true, false, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return STATION_A;
        }
    },
    /**
     * instance of the station B
     */
    STATION_B("B", true, false, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return STATION_B;
        }
    },
    /**
     * instance of the station C
     */
    STATION_C("C", true, false, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return STATION_C;
        }
    },
    /**
     * instance of the station D
     */
    STATION_D("D", true, false, false) {
        @Override
        public CellType extinguish() throws GameLogicException {
            throw new GameLogicException();
        }
        
        @Override
        public CellType onFire() {
            return STATION_D;
        }
    };
    
    private String identifier;
    private boolean canFillWater;
    private boolean isPassable;
    private boolean isFire;
    
    private CellType(String identifier, boolean canFillWater, boolean isPassable, boolean isFire) {
        this.identifier = identifier;
        this.canFillWater = canFillWater;
        this.isPassable = isPassable;
        this.isFire = isFire;
    }

    /**
     * getter
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * getter
     * @return canFillWater
     */
    public boolean canFillWater() {
        return canFillWater;
    }

    /**
     * setter
     * @param canFillWater
     */
    public void setCanFillWater(boolean canFillWater) {
        this.canFillWater = canFillWater;
    }

    /**
     * getter
     * @return isPassable
     */
    public boolean isPassable() {
        return isPassable;
    }

    /**
     * setter
     * @param isPassable
     */
    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    /**
     * getter 
     * @return isFire
     */
    public boolean isFire() {
        return isFire;
    }

    /**
     * setter
     * @param isFire
     */
    public void setFire(boolean isFire) {
        this.isFire = isFire;
    }


    /**
     * 
     * @return CellType after the extinguish command
     * @throws GameLogicException if the cell type is not extinguishable
     */
    public abstract CellType extinguish() throws GameLogicException;
    
    /**
     * @return CellType after fire-to-roll
     */
    public abstract CellType onFire();
    
}
