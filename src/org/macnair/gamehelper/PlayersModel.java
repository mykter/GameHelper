package org.macnair.gamehelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.macnair.gamehelper.Player;

// Model of the players that the GameHelper is aware of - handles persistence of those players.
// A singleton
public class PlayersModel {
	private List<Player> allPlayers;
	// Identifies those players that are currently selected
	private Map<Player, Boolean> currentPlayers;
	
	public PlayersModel() {
		allPlayers = new ArrayList<Player>();
		allPlayers.add(new Player(false,"Mike"));
		allPlayers.add(new Player(false,"Emma"));
		allPlayers.add(new Player(false,"Bagnio"));
		allPlayers.add(new Player(false,"Popov"));
		allPlayers.add(new Player(false,"Bonshun"));
		allPlayers.add(new Player(false,"Bootster"));
		allPlayers.add(new Player(false,"Duck"));
		allPlayers.add(new Player(false,"Edward"));
		allPlayers.add(new Player(false,"James"));
		
		// initially no-one is selected
		currentPlayers = new HashMap<Player, Boolean>();
	}
	
	public List<Player> getAllPlayers() {
		return allPlayers;
	}
	// Pass in a comparator such as Player.SEEN_ORDER
	public List<Player> getAllPlayers(Comparator<Player> comp) {
		List<Player> sortedPlayers = new ArrayList<Player>(allPlayers);
		Collections.sort(sortedPlayers, comp);
		return sortedPlayers;
	}
	
	public List<Player> getCurrentPlayers() {
		List<Player> current = new ArrayList<Player>();
		for (Player p : allPlayers) {
			if (currentPlayers.get(p)) {
				current.add(p);
			}
		}
		return current;
	}
	// Pass in a comparator such as Player.SEEN_ORDER
	public List<Player> getCurrentPlayers(Comparator<Player> comp) {
		List<Player> sortedPlayers = this.getCurrentPlayers();
		Collections.sort(sortedPlayers, comp);
		return sortedPlayers;
	}
	
	public void setCurrentPlayers(List<Player> Players ) {
		currentPlayers = new HashMap<Player,Boolean>();
		for (Player p: Players) {
			currentPlayers.put(p,true);
		}
	}
	// Adds a new named player and returns that player
	public Player newPlayer(String name) {
		Player p = new Player(false, name); 
		allPlayers.add(p);
		return p;
	}
	
	// Removes p from the players, returns false if the player wasn't found, true otherwise
	public Boolean deletePlayer(Player p) {
		if (!allPlayers.contains(p))
			return false;
		
		allPlayers.remove(p);
		return true;
	}
}

