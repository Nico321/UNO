package de.uno.gameconnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import biz.source_code.base64Coder.Base64Coder;
import de.uno.card.Card;
import de.uno.card.CardColor;
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
	
	@EJB
	private GameManagerLocal gameManager;

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
    
	private GameLocal getGame(Player player){
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
		gameManager.createGame((Player)deserialize(player));
	}
	@Override
	public void addPlayer(String creator, String member) {
		getGame((Player)deserialize(creator)).addPlayer((Player)deserialize(member));
	}

	
}
