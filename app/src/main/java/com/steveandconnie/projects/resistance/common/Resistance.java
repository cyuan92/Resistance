package com.steveandconnie.projects.resistance.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by connieyuan on 9/26/15.
 */
public class Resistance implements Parcelable {

    private int currentMissionNum;
    private boolean[] missionHistory;
    private ArrayList<Player> playerList;

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
    }

    public Resistance(Parcel in) {
        currentMissionNum = in.readInt();
        missionHistory = in.createBooleanArray();
        playerList = new ArrayList<Player>();
        in.readTypedList(playerList, Player.CREATOR);
    }


    // Constructor to create resistance object
    public Resistance(int currentMissionNum, boolean[] missionHistory, ArrayList<Player> playerList) {
        this.currentMissionNum = currentMissionNum;
        this.missionHistory = missionHistory;
        this.playerList = playerList;
    }

    public int getCurrentMissionNum() {
        return currentMissionNum;
    }

    public void setMissionResult(boolean result) {
        missionHistory[currentMissionNum-1] = result;
        currentMissionNum++;
    }

    public boolean[] getMissionHistory() {
        return missionHistory;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public int getNumPlayers() {
        return playerList.size();
    }

}
