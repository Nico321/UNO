package de.uno.lobbymanagement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jws.WebMethod;
import javax.jws.WebService;

import biz.source_code.base64Coder.Base64Coder;
import de.uno.gamemanager.GameManagerLocal;
import de.uno.player.Player;
import de.uno.usermanagement.User;


@Singleton
@WebService
public class Lobby {
	HashMap<User, LobbyGame> possibleGames;
	@EJB
	private GameManagerLocal gameManager;
	
    @PostConstruct
    public void init() {
    	possibleGames = new HashMap<User, LobbyGame>();
    }
    private String serialize(Serializable o){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }
    
	private static Object deserialize(String s){
		byte [] data = Base64Coder.decode( s );
        ObjectInputStream ois;
        Object o = null;
		try {
			ois = new ObjectInputStream( 
			                                new ByteArrayInputStream(  data ) );
	        try {
				o  = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return o;
	}
	
	@WebMethod
	public String showOpenGames(){
		return serialize(possibleGames);
	}
	
	@WebMethod
	public void createNewGame(String screator, Boolean isPublic){
		User creator = (User)deserialize(screator);
		LobbyGame newGame = new LobbyGame(creator, isPublic);
		possibleGames.put(creator, newGame);
	}
	
	@WebMethod
	public void addFriendsToGame(String smod, String sfriend){
		User mod = (User)deserialize(smod);
		User friend = (User)deserialize(sfriend);
		if(possibleGames.get(mod) != null)
			possibleGames.get(mod).addFriend(friend);
	}
	
	@WebMethod
	public void enterRandom(String suser){
		User user = (User)deserialize(suser);
		for(User u : possibleGames.keySet()){
			LobbyGame game = (LobbyGame)possibleGames.get(u);
				if(game.getFill() == true)
					game.addFriend(user);		
		}
	}
	
	@WebMethod
	public void startGame(String smod){
		User mod = (User)deserialize(smod);
		if(possibleGames.get(mod) != null){
			if(possibleGames.get(mod).rdyToStart() == true){
				//Mit Nico �ber die Anbindung an das Game reden!
					Player creator = new Player(mod.getUsername());
					gameManager.createGame(creator);
					for(User u : possibleGames.get(mod).getPlayer().values()){
						if(u.getUsername() != mod.getUsername())
							gameManager.getPlayersGame(creator).addPlayer(new Player(u.getUsername()));
					}
					gameManager.getPlayersGame(creator).startGame();
			}
		}
	}

}
