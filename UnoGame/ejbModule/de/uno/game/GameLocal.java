package de.uno.game;


import java.util.HashMap;
import java.util.LinkedList;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.player.Player;


public interface GameLocal {

	
	public Player getNextPlayer();
	public LinkedList<Card> drawCard(int quantity);
	public boolean putCard(Card card);
	public HashMap<String, Integer> getPlayerStatus();
	public Card getStackCard();
	public void addPlayer(Player player);
	public void startGame();
	public Hand getHand(Player player);
	public void setWishedColor(CardColor wishedColor);
	public CardColor getWishedColor();
	public boolean playerAssignedToGame(Player player);
}
