package de.highscore.system;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.highscore.common.HighscoreOnlineService;

public class HighscoreOnlineServiceImpl implements HighscoreOnlineService{
	
	private static Logger jlog =  Logger.getLogger(HighscoreOnlineServiceImpl.class.getPackage().getName());
	private HashMap<String, Integer> hashMap;

	@Override
	public void addNewUser(String username, Integer points) throws RemoteException {
		if (!hashMap.containsKey(username)) {
			hashMap.put(username, points);
			jlog.log(Level.INFO, "Neuen User hinzugefügt. username="+username);
		}
		
	}

	@Override
	public void addPointsToUser(String username, Integer points) throws RemoteException {
		if(hashMap.containsKey(username)){
			hashMap.put(username, hashMap.get(username) + points);
			jlog.log(Level.INFO, points + " Punkte zu " + username + " hinzugefügt");
		}
		
	}

	@Override
	public HashMap<String, Integer> getHighscore() throws RemoteException {
		return hashMap;
	}

	
}
