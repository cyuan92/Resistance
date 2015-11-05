package com.steveandconnie.projects.resistance.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by connieyuan on 9/26/15.
 */
public class Resistance implements Parcelable {

    private int currentMissionNum;
    private boolean[] missionHistory;
    private ArrayList<Player> playerList;
    private HashMap<String, Player> nameToPlayerMap;

    // functions for Parcelable object
    public static final Parcelable.Creator<Resistance> CREATOR = new Parcelable.Creator<Resistance>() {
        public Resistance createFromParcel(Parcel in) {
            return new Resistance(in);
        }
        public Resistance[] newArray(int size) {
            return new Resistance[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeInt(currentMissionNum);
        parcel.writeBooleanArray(missionHistory);
        parcel.writeTypedList(playerList);
        parcel.writeInt(nameToPlayerMap.size());
        for (String key: nameToPlayerMap.keySet()) {
            parcel.writeString(key);
            parcel.writeParcelable(nameToPlayerMap.get(key), 0);
        }
    }

    public Resistance(Parcel in) {
        currentMissionNum = in.readInt();
        missionHistory = in.createBooleanArray();
        playerList = new ArrayList<Player>();
        in.readTypedList(playerList, Player.CREATOR);

        nameToPlayerMap = new HashMap<String, Player>();
        int size = in.readInt();
        for (int i=0; i<size; i++) {
            String key = in.readString();
            Player player = in.readParcelable(Player.class.getClassLoader());
            nameToPlayerMap.put(key, player);
        }
    }
    // end functions for Parcelable object


    // constructors to create resistance object
    public Resistance(ArrayList<Player> playerList) {
        this.currentMissionNum = 1;
        this.missionHistory = new boolean[0];
        this.playerList = playerList;
    }

    public Resistance(int currentMissionNum, boolean[] missionHistory, ArrayList<Player> playerList) {
        this.currentMissionNum = currentMissionNum;
        this.missionHistory = missionHistory;
        this.playerList = playerList;
        nameToPlayerMap = new HashMap<String, Player>();
        for(Player player : playerList) {
            nameToPlayerMap.put(player.getPlayerName(), player);
        }
    }
    // end constructors


    // current mission number
    public int getCurrentMissionNum() {
        return currentMissionNum;
    }

    public void incrementMissionNum() {
        currentMissionNum++;
    }
    // end current mission number


    // mission history
    public void setMissionResult(boolean result) {
        missionHistory = Arrays.copyOf(missionHistory, missionHistory.length+1);
        missionHistory[currentMissionNum-1] = result;
    }

    public boolean[] getMissionHistory() {
        return missionHistory;
    }
    // end mission history


    // player list
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public int getNumPlayers() {
        return playerList.size();
    }

    public Player getPlayerFromName(String name) {
        return nameToPlayerMap.get(name);
    }
    // end player list


    // game progression
    public boolean checkIfGameOver() {
        if (currentMissionNum < 3) {
            return false;
        } else if (currentMissionNum == 3 || currentMissionNum == 4) {
            int numPass = 0;
            int numFail = 0;
            for (int i = 0; i < currentMissionNum; i++) {
                if (missionHistory[i]) {
                    numPass += 1;
                } else {
                    numFail += 1;
                }
            }
            if (numPass >= 3 || numFail >= 3) {
                return true;
            } else {
                return false;
            }
        } else if (currentMissionNum == 5) {
            return true;
        }
        return false;
    }
    // end game progression
}
