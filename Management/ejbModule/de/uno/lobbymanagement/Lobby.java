package de.uno.lobbymanagement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * Lobby Singleton, manages all lobbyGames
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
	
	//Creates HashMap for all lobbyGames
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
	
	/**
	 * Methode um sich alle LobbyGames anzuzeigen
	 * @return alle Lobby games werden zur�ckgegeben
	 */
	@WebMethod
	public String showOpenGames(){
		ArrayList<String> creatorNames = new ArrayList<String>();
		for( String lg: possibleGames.keySet()){
			creatorNames.add(lg);
		}
		return serialize(creatorNames);
	}
	
	/**
	 * Neues LobbyGame erstellen
	 * @param creatorUsername Username des Erstellers, Aktueller Username
	 * @param isPublic true=Public game, sichtbar in der lobby false= nicht in der Lobby sichtbar
	 * @return true=new game created false= failed to create
	 */
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
	
	/**
	 * Methode um Leute aus seiner Freundesliste zu einem LobbyGame inzuzuf�gen
	 * @param smod Username Moderator/Creator des LobbyGames
	 * @param sfriend Name des Freundes, der hinzugef�gt werden soll
	 */
	@WebMethod
	public void addFriendsToGame(String smod, String sfriend){
		User mod = (User)deserialize(smod);
		User friend = (User)deserialize(sfriend);
		if(possibleGames.get(mod) != null){
			possibleGames.get(mod).addFriend(friend);
			log.info("friend added to lobby game");
		}
	}
	
	/**
	 * Methode um einem Random Game beizutreten (isPublic == true)
	 * @param username Username des Users, der einem LobbyGame zugeteilt werden m�chte
	 */
	@WebMethod
	public void enterRandom(String username){
		User user = userManagement.FindUserByName(username);
		for(String u : possibleGames.keySet()){
			LobbyGame game = (LobbyGame) possibleGames.get(u);
				if(game.getFill() == true)
					game.addFriend(user);		
		}
	}
	
	/**
	 * Methode um ein LobbyGame zu starten ==> richtiges Game
	 * @param UsernameCreator Username des Creators/Moderators
	 */
	@WebMethod
	public void startGame(String UsernameCreator){
		User mod = userManagement.FindUserByName(UsernameCreator);
		if(possibleGames.get(mod) != null){
			log.info("send lobby game to real game");
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
	
	/**
	 * Mehtode um einen User zu einem LobbyGame zuzuweisen
	 * @param creatorUsername Username des Creators/Mods
	 * @param joinUsername Username, des Users, der einem (isPublic==true) LobbyGame beitreten m�chte
	 */
	@WebMethod
	public boolean joinLobbyGame(String creatorUsername, String joinUsername){
		try{
		User player = userManagement.FindUserByName(joinUsername);
		possibleGames.get(creatorUsername).addMeToGame(player);
		log.info("User joined open game from: " + creatorUsername);
		return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Methode zeigt Spieler, die an einem Spiel teilnehmen
	 * @param creatorUsername Username des Creators
	 * @return List<String> mit userNames
	 */
	@WebMethod
	public String showParticipatingPlayer(String creatorUsername){
		ArrayList<String> userNames = new ArrayList<String>();
		LobbyGame thisGame = possibleGames.get(creatorUsername);
		for( User u: thisGame.getPlayer().values()){
			userNames.add(u.getUsername());
		}
		return serialize(userNames);
	}
}
