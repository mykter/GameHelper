package org.macnair.gamehelper;


import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class Player implements Parcelable {
	private Boolean anon;
	private String name;
	private Time lastSeen;
	
	public Player() {
		this.name = null;
		this.anon = false;
		this.lastSeen = null;
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
	
	public Time getLastSeen() {
		return this.lastSeen;
	}
	public void setSeenNow() {
		this.lastSeen = new Time();
		this.lastSeen.setToNow();
	}
	
	public Boolean isAnonymous() {
		return anon;
	}
	public void setAnonymous(Boolean anonymous) {
		this.anon = anonymous;
	}
	
	public static final Comparator<Player> NAME_ORDER = new NameComparator();
	private static class NameComparator implements Comparator<Player> {
	    public int compare(Player p1, Player p2) {
	        return p1.getName().compareTo(p2.getName());
	    }
	};
	
	// Comparator that will order most recently seen players first (smaller)
	public static final Comparator<Player> SEEN_ORDER = new LastSeenComparator();
	private static class LastSeenComparator implements Comparator<Player> {
	    public int compare(Player p1, Player p2) {
	    	if (p1.getLastSeen() == null) {
	    		if (p2.getLastSeen() == null) {
	    			return 0;
	    		} else {
	    			return 1;
	    		}
	    	} else if (p2.getLastSeen() == null) {
	    		return -1;
	    	} else {
	    		return -1 * Time.compare(p1.getLastSeen(), p2.getLastSeen());
	    	}
	    }
	};
	
	// Sorts first using SEEN_ORDER, breaking ties using NAME_ORDER
	public static final Comparator<Player> SEEN_THEN_NAME_ORDER = new SeenNameComparator();
	private static class SeenNameComparator implements Comparator<Player> {
	    public int compare(Player p1, Player p2) {
	        int seenOrder = SEEN_ORDER.compare(p1, p2);
	        if (seenOrder == 0)
	        	return NAME_ORDER.compare(p1, p2);
	        else
	        	return seenOrder;
	    }
	};
	
	// Parcel stuff below here

	@Override
	public int describeContents() {
		return 0;
	}

	public Player(Parcel in) {
		this.name = in.readString();
		this.anon = (in.readInt() == 1);
		
		long time = in.readLong();
		if (time == 0)
			this.lastSeen = null;
		else {
			this.lastSeen = new Time();
			this.lastSeen.set(time);
		}
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		if (anon) {
			dest.writeInt(1);
		} else {
			dest.writeInt(0);
		}
		
		if (this.lastSeen == null)
			dest.writeLong(0);
		else
			dest.writeLong(this.lastSeen.toMillis(true));
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
