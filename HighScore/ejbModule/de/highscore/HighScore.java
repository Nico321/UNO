package de.highscore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import biz.source_code.base64Coder.Base64Coder;
import de.highscore.common.HighScoreLocal;
import de.highscore.common.HighScoreRemote;
import de.highscoredao.UserDAOLocal;


/**
 * Session Bean implementation class HighScore
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
@Stateless
@Remote
@WebService
public class HighScore implements HighScoreRemote, HighScoreLocal {
	@EJB
	UserDAOLocal userDao;
	private static final Logger log = Logger.getLogger(HighScore.class.getName());
	
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
	 * Fügt Punkte zu einem Spieler hinzu
	 * 
	 * @param	username	Username des Spielers dem punkte hinzugefügt werden sollen
	 * @param	points		Anzahl der Punkte die hinzugefügt werden sollen
	 */
	@Override
	public void addPointsToUser(String username, Integer points) {
		userDao.addPointsToUser(username, points);
		log.info("added " + points.toString() + " Points to " + username);
	}
	
	/**
	 * Fragt die HighScore ab
	 * 
	 * @return	liefert eine serialisierte HashMap von User und Punkte
	 */
	@Override
	@WebMethod
	public String getHighscore() {
		HashMap<String, Integer> highscore = new HashMap<String, Integer>();
		
		for(Iterator<User> it = userDao.getAllUsers().iterator(); it.hasNext();){
			User user = it.next();
			highscore.put(user.getUsername(), user.getPoints());
		}
		
		return serialize(highscore);
	}

}
