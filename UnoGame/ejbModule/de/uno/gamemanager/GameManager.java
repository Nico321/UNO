package de.uno.gamemanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

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
    
    @PostConstruct
    public void init() {
    	games = new LinkedList<GameLocal>();
    }

	@Override
	@Lock(LockType.WRITE)
	public void addGame(GameLocal game) {
		games.add(game);
	}

	@Override
	@Lock(LockType.WRITE)
	public void removeGame(GameLocal game) {
		games.remove(game);
	}

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

	@Override
	public void createGame(Player player) {
		games.add(new Game(player));		
	}

	@Override
	@Asynchronous
	public void updateHighScore(HashMap<Player, Integer> pointList) {
		for (Entry<Player, Integer> entry : pointList.entrySet()) {
			try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
				TextMessage message = context.createTextMessage();
				message.setStringProperty("DocType", "Letter");
				message.setText(entry.getKey().getUsername()+":"+entry.getValue().toString());
				context.createProducer().send(outputQueue, message);
			}
			catch (JMSException e) {
				// TODO replace with output to logging framework			
				e.printStackTrace();
			} 
		}
		
	}
    

}
