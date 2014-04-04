package de.uno.gameconnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import biz.source_code.base64Coder.Base64Coder;
import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.common.CardNotValidException;
import de.uno.common.GameConnectionRemote;
import de.uno.game.GameLocal;
import de.uno.gamemanager.GameManagerLocal;
import de.uno.player.Player;

/**
 * Session Bean implementation class GameConnectionManager
 */
@Stateless
@Remote
@WebService
public class GameConnectionManager implements GameConnectionRemote {
	
    /**
     * Default constructor. 
     */
    public GameConnectionManager() {
        // TODO Auto-generated constructor stub
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return o;
	}
    
	private GameLocal getGame(Player player){
    	GameManagerLocal gameManager = null;
        InitialContext context;
		try {
			context = new InitialContext();
			//String lookupString = "ejb:UnoEAR/UnoGame/GameManager!de.uno.commonLocal.GameManagerLocal";
			String lookupString = "java:module/GameManager!de.uno.gamemanager.GameManagerLocal";
			gameManager = (GameManagerLocal) context.lookup(lookupString);
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} 
		return gameManager.getPlayersGame(player);
    }

	@Override
	@WebMethod
	public String getNextPlayer(String player) {
		try {
		return serialize(getGame((Player)deserialize(player)).getNextPlayer());
		}catch(Exception e){
			return serialize(getGame((Player)deserialize(player)).getNextPlayer());
		}
	}

	@Override
	@WebMethod
	public String drawCard(String player, int quantity) {
		return serialize(getGame((Player)deserialize(player)).drawCard(quantity));
	}

	@Override
	@WebMethod
	public boolean putCard(String player, String card){
		return getGame((Player)deserialize(player)).putCard((Card)deserialize(card));
	}

	@Override
	@WebMethod
	public String getPlayerStatus(String player) {
		return serialize(getGame((Player)deserialize(player)).getPlayerStatus());
	}

	@Override
	@WebMethod
	public String getStackCard(String player) {
		return serialize(getGame((Player)deserialize(player)).getStackCard());
	}

	@Override
	@WebMethod
	public void startGame(String player) {
		getGame((Player)deserialize(player)).startGame();
	}

	@Override
	@WebMethod
	public String getHand(String player) {
		return serialize(getGame((Player)deserialize(player)).getHand((Player)deserialize(player)));
	}

	@Override
	@WebMethod
	public void setWishedColor(String player, String wishedColor) {
		getGame((Player)deserialize(player)).setWishedColor((CardColor)deserialize(wishedColor));
	}

	@Override
	@WebMethod
	public String getWishedColor(String player) {
		return serialize(getGame((Player)deserialize(player)).getWishedColor());
	}
	@Override
	public void createNewGame(String player) {
		GameManagerLocal gameManager = null;
        InitialContext context;
		try {
			context = new InitialContext();
			//String lookupString = "ejb:UnoEAR/UnoGame/GameManager!de.uno.commonLocal.GameManagerLocal";
			String lookupString = "java:module/GameManager!de.uno.gamemanager.GameManagerLocal";
			gameManager = (GameManagerLocal) context.lookup(lookupString);
			gameManager.createGame((Player)deserialize(player));
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} 
	}
	@Override
	public void addPlayer(String creator, String member) {
		getGame((Player)deserialize(creator)).addPlayer((Player)deserialize(member));
	}

	
}
