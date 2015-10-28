package com.steveandconnie.projects.resistance.common;

/**
 * Created by Steve on 9/9/2015.
 */
public enum Role  {
    REBEL, SPY;

    @Override
    public String toString() {
        switch(this) {
            case REBEL:
                return "Rebel";
            case SPY:
                return "Spy";
            default:
                return "None";
        }
    }

}
