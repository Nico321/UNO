package de.uno.common;


import java.util.HashMap;
import java.util.LinkedList;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.player.Player;


public interface GameConnectionRemote {

	
	public String getNextPlayer(String player);
	public String drawCard(String player, int quantity);
	public boolean putCard(String player, String card);
	public String getPlayerStatus(String player);
	public String getStackCard(String player);
	public void startGame(String player);
	public String getHand(String player);
	public void setWishedColor(String player, String wishedColor);
	public String getWishedColor(String player);
	public void createNewGame(String player);
	public void addPlayer(String creator, String member);
}
