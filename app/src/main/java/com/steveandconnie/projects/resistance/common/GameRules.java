package com.steveandconnie.projects.resistance.common;

import android.util.SparseIntArray;

/**
 * Created by connieyuan on 9/27/15.
 */
public class GameRules {
    public static final SparseIntArray NUM_PLAYERS_TO_SPIES;

    public static final SparseIntArray MISSION_ONE;
    public static final SparseIntArray MISSION_TWO;
    public static final SparseIntArray MISSION_THREE;
    public static final SparseIntArray MISSION_FOUR;
    public static final SparseIntArray MISSION_FIVE;

    public static final SparseIntArray MISSION_FOUR_FAILS_NEEDED;

    static {
        NUM_PLAYERS_TO_SPIES = new SparseIntArray();
        NUM_PLAYERS_TO_SPIES.put(5, 2);
        NUM_PLAYERS_TO_SPIES.put(6, 2);
        NUM_PLAYERS_TO_SPIES.put(7, 3);
        NUM_PLAYERS_TO_SPIES.put(8, 3);
        NUM_PLAYERS_TO_SPIES.put(9, 3);
        NUM_PLAYERS_TO_SPIES.put(10, 4);

        MISSION_ONE = new SparseIntArray();
        MISSION_ONE.put(5, 2);
        MISSION_ONE.put(6, 2);
        MISSION_ONE.put(7, 2);
        MISSION_ONE.put(8, 3);
        MISSION_ONE.put(9, 3);
        MISSION_ONE.put(10, 3);

        MISSION_TWO = new SparseIntArray();
        MISSION_TWO.put(5, 3);
        MISSION_TWO.put(6, 3);
        MISSION_TWO.put(7, 3);
        MISSION_TWO.put(8, 4);
        MISSION_TWO.put(9, 4);
        MISSION_TWO.put(10, 4);

        MISSION_THREE = new SparseIntArray();
        MISSION_THREE.put(5, 2);
        MISSION_THREE.put(6, 3);
        MISSION_THREE.put(7, 3);
        MISSION_THREE.put(8, 4);
        MISSION_THREE.put(9, 4);
        MISSION_THREE.put(10, 4);

        MISSION_FOUR = new SparseIntArray();
        MISSION_FOUR.put(5, 3);
        MISSION_FOUR.put(6, 3);
        MISSION_FOUR.put(7, 4);
        MISSION_FOUR.put(8, 5);
        MISSION_FOUR.put(9, 5);
        MISSION_FOUR.put(10, 5);

        MISSION_FIVE = new SparseIntArray();
        MISSION_FIVE.put(5, 3);
        MISSION_FIVE.put(6, 4);
        MISSION_FIVE.put(7, 4);
        MISSION_FIVE.put(8, 5);
        MISSION_FIVE.put(9, 5);
        MISSION_FIVE.put(10, 5);

        MISSION_FOUR_FAILS_NEEDED = new SparseIntArray();
        MISSION_FOUR_FAILS_NEEDED.put(5, 1);
        MISSION_FOUR_FAILS_NEEDED.put(6, 1);
        MISSION_FOUR_FAILS_NEEDED.put(7, 2);
        MISSION_FOUR_FAILS_NEEDED.put(8, 2);
        MISSION_FOUR_FAILS_NEEDED.put(9, 2);
        MISSION_FOUR_FAILS_NEEDED.put(10, 2);
    }

    public static boolean checkNumPlayersSelected(int numSelected, int missionNum, int numPlayers) {
        return getNumPlayersToSelect(missionNum, numPlayers) == numSelected;
    }

    public static int getNumPlayersToSelect(int missionNum, int numPlayers) {
        switch (missionNum) {
            case 1:
                return MISSION_ONE.get(numPlayers);
            case 2:
                return MISSION_TWO.get(numPlayers);
            case 3:
                return MISSION_THREE.get(numPlayers);
            case 4:
                return MISSION_FOUR.get(numPlayers);
            case 5:
                return MISSION_FIVE.get(numPlayers);
            default:
                return -1;
        }
    }
}
