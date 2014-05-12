package de.uno.gamemanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;

import de.highscore.common.HighScoreLocal;
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
		InitialContext context;
		HighScoreLocal highscore = null;
		try {
			context = new InitialContext();
			String lookupString = "ejb:HighScoreEAR/HighScore/HighScore!de.highscore.common.HighScoreLocal";
			highscore = (HighScoreLocal) context.lookup(lookupString);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		for (Entry<Player, Integer> entry : pointList.entrySet()) {
			highscore.addPointsToUser(entry.getKey().getUsername(), entry.getValue());
		}
	}
    

}
