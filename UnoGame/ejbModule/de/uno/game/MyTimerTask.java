package de.uno.game;

import java.util.Date;
import java.util.TimerTask;
/**
 * TimerTak for Player Timeout
 * 
 * @author Nico Lindmeyer 737045
 * 
 */
public class MyTimerTask extends TimerTask{
	private Game game;
	
	public MyTimerTask(Game game){
		this.game = game;
	}
	
	@Override
	public void run() {
		game.getNextPlayer().setDisconnected(new Date());
		System.out.println("Player " + game.getNextPlayer().getUsername() + " disconnected.");
		game.checkGameState();
	}

}
