package de.uno.gamemanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import de.uno.game.Game;
import de.uno.game.GameLocal;
import de.uno.player.Player;

/**
 * Session Bean implementation class GameManager
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
@Startup
@Singleton
@Local
public class GameManager implements GameManagerLocal {

	public LinkedList<GameLocal> games;
	
	@Resource(mappedName="java:/JmsXA")
	private ConnectionFactory jmsFactory;
  
	@Resource(mappedName="java:/queue/HighscoreJMS")
	private Queue outputQueue;
    
	private static final Logger log = Logger.getLogger(GameManagerLocal.class.getName());
	
    @PostConstruct
    public void init() {
    	games = new LinkedList<GameLocal>();
    }

	/**
	 * Fügt ein Spiel für die interne Verwaltung hinzu
	 * 
	 * @param game Spiel das hinzugefügt werden soll
	 */
	@Override
	@Lock(LockType.WRITE)
	public void addGame(GameLocal game) {
		games.add(game);
	}

	/**
	 * Löscht ein Spiel aus der internen Verwaltung
	 * 
	 * @param game Spiel das gelöscht werden soll
	 */
	@Override
	@Lock(LockType.WRITE)
	public void removeGame(GameLocal game) {
		games.remove(game);
		log.info("removed game from managementlist");
	}

	/**
	 * Sucht das Spiel zum Spieler heraus. 
	 * 
	 * @param player 	Der Spieler, dessen Spiel gesucht werden soll
	 * @return 			Das Spiel des Spielers
	 */
	@Override
	@Lock(LockType.READ)
	public GameLocal getPlayersGame(Player player) {
		for(Iterator<GameLocal> it = games.iterator(); it.hasNext();){
			GameLocal gamePuffer = it.next();
			if(gamePuffer.playerAssignedToGame(player))
				return gamePuffer;
		}
		return null;
	}
	
	/**
	 * erstellt ein neues Spiel und fügt diesem den ersten Spieler hinzu
	 * 
	 * @param player	Spiler der dem SPiel hinzugefügt wird
	 */
	@Override
	public void createGame(Player player) {
		games.add(new Game(player));	
		log.info("added game to managementlist");
	}

	/**
	 * Schickt Aktualisierungen an die Highscore
	 * 
	 * @param pointsList	HashMap mit dem Player und den dazugehörigen Punkten
	 */
	@Override
	@Asynchronous
	public void updateHighScore(HashMap<Player, Integer> pointList) {
		for (Entry<Player, Integer> entry : pointList.entrySet()) {
			try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
				TextMessage message = context.createTextMessage();
				message.setStringProperty("DocType", "Letter");
				message.setText(entry.getKey().getUsername()+":"+entry.getValue().toString());
				context.createProducer().send(outputQueue, message);
				log.info("Send Message to Highscore Server: " + entry.getKey().getUsername() + " - " + entry.getValue().toString() + " Points");
			}
			catch (JMSException e) {
				log.log(Level.SEVERE, "Failed to send message to Highscore server!", e);			
				e.printStackTrace();
			} 
		}
		
	}
    

}
