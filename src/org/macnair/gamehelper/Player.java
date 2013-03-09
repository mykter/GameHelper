package org.macnair.gamehelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

	private Boolean anon;
	private String name;
	
	public Player() {
		this.name = null;
		this.anon = false;
	}
	
	public Player(Boolean anonymous, String name) {
		this.anon = anonymous;
		this.name = name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return this.getName();
	}
	
	public Boolean isAnonymous() {
		return anon;
	}
	public void setAnonymous(Boolean anonymous) {
		this.anon = anonymous;
	}
	
	// Parcel stuff below here

	@Override
	public int describeContents() {
		return 0;
	}

	public Player(Parcel in) {
		name = in.readString();
		anon = (in.readInt() == 1);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		if (anon) {
			dest.writeInt(1);
		} else {
			dest.writeInt(0);
		}
	}
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
		public Player createFromParcel(Parcel in) {
		    return new Player(in);
		}
		
		public Player[] newArray(int size) {
		    return new Player[size];
		}
	};
}
