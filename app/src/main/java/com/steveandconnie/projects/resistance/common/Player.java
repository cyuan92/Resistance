package com.steveandconnie.projects.resistance.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steve on 9/9/2015.
 */
public class Player implements Parcelable {

    private String name;
    private Role role;

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(name);
        parcel.writeSerializable(role);
    }

    public Player(Parcel in) {
        name = in.readString();
        role = (Role) in.readSerializable();
    }

    public Player(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getPlayerName() {
        return name;
    }

    public Role getPlayerRole() {
        return role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player name: ").append(this.getPlayerName()).append(" ");
        sb.append("Player role: ").append(this.getPlayerRole().toString()).append("\n");
        return sb.toString();
    }

}
