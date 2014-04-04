package de.uno.gamemanager;

import de.uno.game.GameLocal;
import de.uno.player.Player;

public interface GameManagerLocal {
	
	public void addGame(GameLocal game);
	public void removeGame(GameLocal game);
	public GameLocal getPlayersGame(Player player);
	public void createGame(Player player);
}
