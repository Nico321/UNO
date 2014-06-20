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
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
@Stateless
@Remote
@WebService
public class GameConnectionManager implements GameConnectionRemote {
	
	@EJB
	private GameManagerLocal gameManager;

	/**
	 * serialisiert ein Obejkt
	 * @param o	Objekt, dass serialisiert werden soll
	 * @return	Gibt das serialisierte Objekt als STring zurück
	 */
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
    
    /**
	 * deserialisiert ein Obejkt
	 * @param o	Objekt, dass deserialisiert werden soll
	 * @return	Gibt das deserialisierte Objekt  zurück
	 */
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
	 * Sucht das passende Spiel zum Spieler
	 * @param player	Spieler, dessem Spiel gesucht werden soll
	 * @return			Spiel des Spielers
	 */
	private GameLocal getGame(Player player){
		return gameManager.getPlayersGame(player);
    }

	/**
	 * gibt den nächsten Spieler zurück
	 * 
	 * @param player Spieler, aus dessen Spiel der nächste Spieler gesucht werden soll
	 * @return 		Nächster Spieler des SPiels
	 */
	@Override
	@WebMethod
	public String getNextPlayer(String player) {
		return serialize(getGame((Player)deserialize(player)).getNextPlayer());
	}

	/**
	 * Zieht eine Karte
	 * 
	 * @param	player	Spieler der eine Karte ziehen soll
	 * @param quantity 	Anzahl der Karten die gezogen werden sollen
	 * @return			serialisierte Liste von Karten
	 */
	@Override
	@WebMethod
	public String drawCard(String player, int quantity) {
		Player p = (Player)deserialize(player);
		return serialize(getGame(p).drawCard(p, quantity));
	}
	
	/**
	 * legt eine Karte
	 * 
	 * @param	player 	Spieler der die Karte legt
	 * @param	card	Karte die gelegt werden soll
	 * @return			boolean der erfolg vermeldet oder nicht
	 */
	@Override
	@WebMethod
	public boolean putCard(String player, String card){
		Player p = (Player)deserialize(player);
		return getGame(p).putCard(p, (Card)deserialize(card));
	}

	/**
	 * Gibt den Spieler status zurück
	 * 
	 * @param	player	Spieler dessen Spiel ausgegeben werden soll
	 * @return			serialisierte HashMap des Spielstatus
	 */
	@Override
	@WebMethod
	public String getGameStatus(String player) {
		return serialize(getGame((Player)deserialize(player)).getGameStatus());
	}

	/**
	 * Oberste Karte auf dem Ablagestapel holen
	 * 
	 * @param	player	Spieler dess Spiel gesucht werden soll
	 * @return			serialiserte Karte, die oben drauf liegt
	 */
	@Override
	@WebMethod
	public String getStackCard(String player) {
		return serialize(getGame((Player)deserialize(player)).getStackCard());
	}

	/**
	 * Startet das Spiel, verteilt Karten, mischt das Deck etc
	 * 
	 * @param	player 	Spieler, dessen Spiel gestartet werden soll
	 * 
	 */
	@Override
	@WebMethod
	public void startGame(String player) {
		getGame((Player)deserialize(player)).startGame();
	}

	/**
	 * Gibt die Hand eines Spielers zurück
	 * 
	 * @param	player	Spieler, dessen Hand zurückgegeben werden soll
	 * @return			serialisierte Hand
	 */
	@Override
	@WebMethod
	public String getHand(String player) {
		return serialize(getGame((Player)deserialize(player)).getHand((Player)deserialize(player)));
	}

	/**
	 * Setzt die wunsch Farbe bei einer schwarzen Karte
	 * 
	 * @param	player	Spieler der die Farbe setzen möchte
	 */
	@Override
	@WebMethod
	public void setWishedColor(String player, String wishedColor) {
		getGame((Player)deserialize(player)).setWishedColor((CardColor)deserialize(wishedColor));
	}

	/**
	 * gewünschte Farbe abfragen
	 * 
	 * @param	player	Spieler der die gewünschte Farbe abfragen möchte
	 * @return			serialisierte Farbe
	 */
	@Override
	@WebMethod
	public String getWishedColor(String player) {
		return serialize(getGame((Player)deserialize(player)).getWishedColor());
	}
	
	/**
	 * erstellt ein neues Spiel
	 * 
	 * @param	player	Spieler der das Spiel erstellen möchte
	 */
	@Override
	public void createNewGame(String player) {
		gameManager.createGame((Player)deserialize(player));
	}
	
	/**
	 * Fügt ein Spieler zu einem Spiel hinzu
	 * 
	 * @param	creator	Ersteller des Spiel zu dem der User hinzugefügt werden soll
	 * @param	member	Spieler der hinzugefügt werden soll
	 */
	@Override
	public void addPlayer(String creator, String member) {
		getGame((Player)deserialize(creator)).addPlayer((Player)deserialize(member));
	}

	/**
	 * Uno sagen
	 * 
	 * @param	player	Spieler der UNO sagen möchte
	 */
	@Override
	public void callUno(String player) {
		Player p = (Player)deserialize(player);
		getGame(p).callUno(p);
		
	}

	
}
