package de.uno.game;


import java.util.HashMap;
import java.util.LinkedList;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.player.Player;

/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public interface GameLocal {

	
	public Player getNextPlayer();
	public LinkedList<Card> drawCard(Player player, int quantity);
	public boolean putCard(Player player, Card card);
	public HashMap<String, Integer> getGameStatus();
	public Card getStackCard();
	public void addPlayer(Player player);
	public void startGame();
	public Hand getHand(Player player);
	public void setWishedColor(CardColor wishedColor);
	public CardColor getWishedColor();
	public boolean playerAssignedToGame(Player player);
	boolean callUno(Player player);
	public int getGameProgress();
	public boolean isGameFinished();
	public boolean leaveGame(Player player);
	public HashMap<String, Integer> getWinners();
}
