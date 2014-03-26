package de.highscore.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface HighscoreOnlineService extends Remote{
	
	public static final String INTERFACE_ID = "XbankOnlineService";
	
	/**
	 * Operation zum hinzufügen eines neuen Users
	 * @param username
	 * @param points
	 * @throws RemoteException
	 */
	public void addNewUser(String username, Integer points) throws RemoteException;
	
	/**
	 * Operation um Punkte zu einem User hinzuzufügen
	 * @param username
	 * @param points
	 * @throws RemoteException
	 */
	public void addPointsToUser(String username, Integer points) throws RemoteException;
	
	/**
	 * Operation zum Abfragen der Highscore
	 * @return
	 * @throws RemoteException
	 */
	public HashMap<String, Integer> getHighscore() throws RemoteException;
}
