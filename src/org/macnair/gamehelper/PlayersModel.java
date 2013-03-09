package org.macnair.gamehelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.macnair.gamehelper.Player;

// Model of the players that the GameHelper is aware of - handles persistence of those players.
// A singleton
public class PlayersModel {
	private List<Player> players;
	
	public PlayersModel() {
		players = new ArrayList<Player>();
		players.add(new Player(false,"Mike"));
		players.add(new Player(false,"Emma"));
		players.add(new Player(false,"Bagnio"));
		players.add(new Player(false,"Popov"));
		players.add(new Player(false,"Bonshun"));
		players.add(new Player(false,"Bootster"));
		
	}
	
	public static final int SORT_RECENT = 0;
	public static final int SORT_NAME = 1;
	public List<Player> getPlayers(int sort) {
		Comparator<Player> comp;
		switch(sort) {
			case SORT_RECENT: // TODO
			case SORT_NAME:
				comp = new Comparator<Player>() {
				    public int compare(Player p1, Player p2) {
				        return p1.getName().compareTo(p2.getName());
				    }
				};
				break;
			default:
				throw new RuntimeException("Invalid sort order provided to getPlayers: " + sort);
		}
		
		List<Player> sortedPlayers = new ArrayList<Player>(players);
		Collections.sort(sortedPlayers, comp);
		return sortedPlayers;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	// Adds a new named player and returns that player
	public Player newPlayer(String name) {
		Player p = new Player(false, name); 
		players.add(p);
		return p;
	}
	
	// Removes p from the players, returns false if the player wasn't found, true otherwise
	public Boolean deletePlayer(Player p) {
		if (!players.contains(p))
			return false;
		
		players.remove(p);
		return true;
	}
}

