package com.steveandconnie.projects.resistance;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connieyuan on 9/26/15.
 */
public class Resistance implements Parcelable {

    private int currentMissionNum;
    private boolean[] missionHistory;

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
    }

    public Resistance(Parcel in) {
        currentMissionNum = in.readInt();
        missionHistory = in.createBooleanArray();
    }

    public Resistance(int currentMissionNum, boolean[] missionHistory) {
        this.currentMissionNum = currentMissionNum;
        this.missionHistory = missionHistory;
    }

    public int getCurrentMissionNum() {
        return currentMissionNum;
    }

    public void setMissionResult(boolean result) {
        missionHistory[currentMissionNum-1] = result;
        currentMissionNum++;
    }

    public boolean[] getMissionHistory() {
//        if (currentMissionNum == 1) {
//            return null;
//        }
//        return Arrays.copyOfRange(missionHistory, 0, currentMissionNum-1);
        return missionHistory;
    }

}
