package de.uno.common;

/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public interface GameConnectionRemote {

	
	public String getNextPlayer(String player);
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
	public void callUno(String player);
	public int getGameProgress(String player);
}
