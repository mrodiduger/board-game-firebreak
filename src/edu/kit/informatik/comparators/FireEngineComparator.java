package edu.kit.informatik.comparators;

import java.util.Comparator;

import edu.kit.informatik.logic.FireEngine;

/**
 * this comparator orders fire engine according to their identifiers
 * @author Rodi
 * @version 1.0
 */
public class FireEngineComparator implements Comparator<FireEngine> {

    @Override
    public int compare(FireEngine left, FireEngine right) {
        int result = left.getIdentifier().compareTo(right.getIdentifier());
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}

