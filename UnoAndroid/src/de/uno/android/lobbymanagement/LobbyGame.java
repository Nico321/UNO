package de.uno.android.lobbymanagement;

import java.util.HashMap;

import de.uno.android.usermanagement.User;


public class LobbyGame {
	HashMap<Integer, User> player;
	Boolean isPublic;
	Integer countPlayer = 2;
	Boolean fill;
	
	public LobbyGame(User creator, Boolean isPublic){
		player.put(1, creator);
		this.isPublic = isPublic;
	}
	
	public User getCreator(){
		return player.get(1);
	}
	
	public void addFriend(User friend){
		if(countPlayer < 4)
			player.put(countPlayer, friend);
		countPlayer++;
	}
	
	public Boolean rdyToStart(){
		if(countPlayer >= 2)
			return true;
		else
			return false;
	}

	public void fillWithRandom(User creator){
		if(creator.equals(player.get(1))){
			fill = true;
		}
	}
	
	public Boolean getFill(){
		return fill;
	}

	public HashMap<Integer, User> getPlayer() {
		return player;
	}
}
