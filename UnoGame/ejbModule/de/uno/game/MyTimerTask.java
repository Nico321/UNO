package de.uno.game;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;
/**
 * TimerTak for Player Timeout
 * 
 * @author Nico Lindmeyer 737045
 * 
 */
public class MyTimerTask extends TimerTask{
	private Game game;
	private static final Logger log = Logger.getLogger(MyTimerTask.class.getName());
	
	public MyTimerTask(Game game){
		this.game = game;
	}
	
	@Override
	public void run() {
		game.getNextPlayer().setDisconnected(new Date());
		log.warning(game.getNextPlayer().getUsername() + " disconnected.");
		
		game.disconnectedCallback();
	}

}
