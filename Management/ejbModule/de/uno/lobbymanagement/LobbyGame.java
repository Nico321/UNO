package de.uno.lobbymanagement;
import java.io.Serializable;
import java.util.HashMap;
import de.uno.usermanagement.User;
/**
 * LobbyGame Class - Game which is used in our Lobby (not started)
 *  
 * @author Daniel Reider 734544
 * 
 */

public class LobbyGame implements Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<Integer, User> player;
	Boolean isPublic;
	Integer countPlayer = 2;
	Boolean fill;
	
	public LobbyGame(User creator, Boolean isPublic){
		player = new HashMap<Integer, User>();
		player.put(1, creator);
		System.out.println("creator in player");
		this.isPublic = isPublic;
	}
	
	public String getCreator(){
		return player.get(1).getUsername();
	}
	
	public boolean addFriend(User friend){
		if(countPlayer < 4){
			player.put(countPlayer, friend);
			countPlayer++;
			return true;
		}
		return false;
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
	
	public boolean addMeToGame(User me){
		if(countPlayer < 4){
			player.put(countPlayer, me);
			countPlayer++;
			return true;
		}
		return false;
	}
}
