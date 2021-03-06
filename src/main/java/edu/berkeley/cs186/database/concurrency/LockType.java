package edu.berkeley.cs186.database.concurrency;

import java.util.Arrays;
import java.util.List;

public enum LockType {
    S,   // shared
    X,   // exclusive
    IS,  // intention shared
    IX,  // intention exclusive
    SIX; // shared intention exclusive

    /**
     * This method checks whether lock types A and B are compatible with
     * each other. If a transaction can hold lock type A on a resource
     * at the same time another transaction holds lock type B on the same
     * resource, the lock types are compatible. A null represents no lock.
     */
    public static boolean compatible(LockType a, LockType b) {
        //throw new UnsupportedOperationException("TODO(hw5): implement");
        //IS, IX, S, SIX, X, null
        boolean [][] matrix = new boolean[][]{
                {true, true, true, true, false, true},
                {true, true, false, false, false, true},
                {true, false, true, false, false, true},
                {true, false, false, false, false, true},
                {false, false, false, false, false, true},
                {true, true, true, true, true, true}
        };

        int aint;
        int bint;

        if (a == null) {
            aint = 5;
        } else {
            switch (a) {
                case S:
                    aint = 2;
                    break;
                case X:
                    aint = 4;
                    break;
                case IS:
                    aint = 0;
                    break;
                case IX:
                    aint = 1;
                    break;
                case SIX:
                    aint = 3;
                    break;
                default:
                    aint = 5;
                    break;
            }
        }

        if (b == null) {
            bint = 5;
        } else {
            switch (b) {
                case S:
                    bint = 2;
                    break;
                case X:
                    bint = 4;
                    break;
                case IS:
                    bint = 0;
                    break;
                case IX:
                    bint = 1;
                    break;
                case SIX:
                    bint = 3;
                    break;
                default:
                    bint = 5;
                    break;
            }
        }

        return matrix[aint][bint];


    }

    /**
     * This method returns the least permissive lock on the parent resource
     * that must be held for a lock of type A to be granted. A null
     * represents no lock.
     */
    public static LockType parentLock(LockType a) {
        //throw new UnsupportedOperationException("TODO(hw5): implement");
        LockType b;
        if (a == null) {
            return null;
        } else {
            switch (a) {
                case SIX: b = IX;
                break;
                case IX: b = IX;
                break;
                case IS: b = IS;
                break;
                case X: b = IX;
                break;
                case S: b = IS;
                break;
                default: b = null;
                break;
            }
        }

        return b;
    }

    /**
     * This method returns whether a lock can be used for a situation
     * requiring another lock (e.g. an S lock can be substituted with
     * an X lock, because an X lock allows the transaction to do everything
     * the S lock allowed it to do). A null represents no lock.
     */
    public static boolean substitutable(LockType substitute, LockType required) {
        //throw new UnsupportedOperationException("TODO(hw5): implement");
        //IS, IX, S, SIX, X, null
        //first: substitute
        //second: required
        boolean [][] sub = new boolean[][]{
                {true, false, false, false, false, true},
                {true, true, false, true, false, true},
                {true, false, true, false, false, true},
                {true, true, true, true, false, true},
                {true, true, true, true, true, true},
                {false, false, false, false, false, true}
        };

        int aint;
        int bint;

        if (substitute == null) {
            aint = 5;
        } else {
            switch (substitute) {
                case S:
                    aint = 2;
                    break;
                case X:
                    aint = 4;
                    break;
                case IS:
                    aint = 0;
                    break;
                case IX:
                    aint = 1;
                    break;
                case SIX:
                    aint = 3;
                    break;
                default:
                    aint = 5;
                    break;
            }
        }

        if (required == null) {
            bint = 5;
        } else {
            switch (required) {
                case S:
                    bint = 2;
                    break;
                case X:
                    bint = 4;
                    break;
                case IS:
                    bint = 0;
                    break;
                case IX:
                    bint = 1;
                    break;
                case SIX:
                    bint = 3;
                    break;
                default:
                    bint = 5;
                    break;
            }
        }

        return sub[aint][bint];
    }

    @Override
    public String toString() {
        switch (this) {
        case S: return "S";
        case X: return "X";
        case IS: return "IS";
        case IX: return "IX";
        case SIX: return "SIX";
        default: throw new UnsupportedOperationException("bad lock type");
        }
    }
};

