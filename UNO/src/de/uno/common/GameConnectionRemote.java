package de.uno.common;

/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public interface GameConnectionRemote {

	
	public String getCurrentPlayer(String player);
	public String drawCard(String player, int quantity);
	public boolean putCard(String player, String card);
	public String getGameStatus(String player);
	public String getStackCard(String player);
	public void startGame(String player);
	public String getHand(String player);
	public void setWishedColor(String player, String wishedColor);
	public String getWishedColor(String player);
	public void createNewGame(String player);
	public void addPlayer(String creator, String member);
	public int getGameProgress(String player);
	public boolean callUno(String player);
	public boolean isGameFinished(String player);
	public boolean leaveGame(String player);
	public String getWinners(String playerString);
	public String getDisconnectedPlayers(String playerString);
}
