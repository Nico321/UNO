package de.uno.lobbymanagement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jws.WebMethod;
import javax.jws.WebService;

import biz.source_code.base64Coder.Base64Coder;
import de.uno.gamemanager.GameManagerLocal;
import de.uno.player.Player;
import de.uno.usermanagement.*;
/**
 * Lobby Singleton, manages all obbyGames
 *  
 * @author Daniel Reider 734544
 * 
 */

@Singleton
@WebService
public class Lobby {
	HashMap<String, LobbyGame> possibleGames;
	@EJB
	private GameManagerLocal gameManager;
	@EJB
	private UserManagementLocal userManagement;
	private static final Logger log = Logger.getLogger( UserDAO.class.getName() );
	
    @PostConstruct
    public void init() {
    	possibleGames = new HashMap<String, LobbyGame>();
    	
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
	
	//Game eroeffnen Param1= creatorUsername Param2=isPublic
	@WebMethod
	public boolean createNewGame(String creatorUsername, Boolean isPublic){
		if( userManagement.FindUserByName(creatorUsername) != null ){
			User creator = userManagement.FindUserByName(creatorUsername);
			LobbyGame newGame = new LobbyGame(creator, isPublic);
			System.out.println("new Game");
			possibleGames.put(creator.getUsername(), newGame);
			log.info("new lobby game created");
			return true;
		}
		else{
			log.info("no lobby game created");
			return false;
		}
	}
	
	@WebMethod
	public void addFriendsToGame(String smod, String sfriend){
		User mod = (User)deserialize(smod);
		User friend = (User)deserialize(sfriend);
		if(possibleGames.get(mod) != null){
			possibleGames.get(mod).addFriend(friend);
			log.info("friend added to lobby game");
		}
	}
	
	@WebMethod
	public void enterRandom(String username){
		User user = userManagement.FindUserByName(username);
		for(String u : possibleGames.keySet()){
			LobbyGame game = (LobbyGame) possibleGames.get(u);
				if(game.getFill() == true)
					game.addFriend(user);		
		}
	}
	
	@WebMethod
	public void startGame(String UsernameCreator){
		User mod = userManagement.FindUserByName(UsernameCreator);
		if(possibleGames.get(mod) != null){
			log.info("send lobby game to real game");
				//Mit Nico �ber die Anbindung an das Game reden!
					Player creator = new Player(mod.getUsername());
					gameManager.createGame(creator);
					for(User u : possibleGames.get(mod).getPlayer().values()){
						if(u.getUsername() != mod.getUsername()){
							gameManager.getPlayersGame(creator).addPlayer(new Player(u.getUsername()));
							log.info("transfered player to game:" + u.getUsername());
						}
					}
					gameManager.getPlayersGame(creator).startGame();
					log.info("lobby game send to real game");
		}
	}
	
	@WebMethod
	public void joinLobbyGame(String creatorUsername, String joinUsername){
		User player = userManagement.FindUserByName(joinUsername);
		possibleGames.get(creatorUsername).addMeToGame(player);
		log.info("User joined open game from: " + creatorUsername);
	}

}
