package de.uno.gamemanager;

import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.uno.game.Game;
import de.uno.game.GameLocal;
import de.uno.player.Player;

/**
 * Session Bean implementation class GameManager
 */
@Startup
@Singleton
@Local(GameManagerLocal.class)
public class GameManager implements GameManagerLocal {

	public LinkedList<GameLocal> games;
    /**
     * Default constructor. 
     */
    public GameManager() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
    public void init() {
    	games = new LinkedList<GameLocal>();
    	/*GameLocal game = null;
        InitialContext context;
		try {
			context = new InitialContext();
			//String lookupString = "ejb:UnoEAR/UnoGame/GameManager!de.uno.commonLocal.GameManagerLocal";
			String lookupString = "java:app/UnoGame/Game!de.uno.game.GameLocal";
			game= (GameLocal) context.lookup(lookupString);
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} */
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
    

}
