package de.uno.gamemanager;

import java.util.HashMap;

import de.uno.game.GameLocal;
import de.uno.player.Player;
/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public interface GameManagerLocal {
	
	public void addGame(GameLocal game);
	public void removeGame(GameLocal game);
	public GameLocal getPlayersGame(Player player);
	public void createGame(Player player);
	public void updateHighScore( HashMap<Player, Integer> pointList);
}
