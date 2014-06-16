package de.uno.android;


import java.util.HashMap;
import java.util.Stack;

import android.app.Application;
import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.NormalCard;
import de.uno.common.GameConnectionRemote;
import de.uno.player.Player;

public class GameApplication extends Application{
	
	//Der Spieler der momentan an der Reihe ist
	private Player actualPlayer;
	//Das Spielerobjekt des lokalen Spielers
	private Player localPlayer;
	//Die gewünschte Farbe falls eine Farbwahlkarte gespielt wurde
	private CardColor wishedColor;
	//Die Hand des lokane Spielers
	private Hand localPlayerHand;
	//Anzahl der Spieler die momentan am Spiel teilnehmen
	private int playerCount;
	//Der Kartenstapel
	private Stack<Card> playedCards;
	//
	private HashMap<String, Integer> gameStatus;
	//singleton instanz der Spielapplikation
	private static GameApplication instance;
	
	public Card getLastPlayedCard() {
		return this.playedCards.pop();
	}
	
	public Card readLastPlayedCard(){
		return this.playedCards.peek();
	}

	public void addPlayedCard(Card card) {
		this.playedCards.push(card);
	}

	public HashMap<String, Integer> getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(HashMap<String, Integer> gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void onCreate(){
		super.onCreate();
		instance = this;
		this.playedCards = new Stack<Card>();
		this.gameStatus = new HashMap<String,Integer>();
	}
	
	public static GameApplication getInstance(){
		return instance;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public Player getLocalPlayer() {
		return localPlayer;
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	private GameConnectionRemote gameStub ;

	public GameConnectionRemote getGameStub() {
		return gameStub;
	}

	public void setGameStub(GameConnectionRemote gameStub) {
		this.gameStub = gameStub;
	}

	public Player getActualPlayer() {
		return actualPlayer;
	}

	public void setActualPlayer(Player actualPlayer) {
		this.actualPlayer = actualPlayer;
	}
	
	/**
	 * Methode die prüft ob die Karte des lokane Spielers gelegt werden kann oder nicht
	 * @param card
	 * @return boolean
	 */
	public boolean isCardValid(Card card){
		Card lastCard = playedCards.peek();
		if(card.getColor() == CardColor.BLACK){
			if (lastCard.getColor() != CardColor.BLACK){
				return true;
			}
			else
				return false;
		}
		else {
			if (lastCard.getColor() == CardColor.BLACK){
				if (card.getColor() == wishedColor)
					return true;
				else
					return false;
			}
			else{
				if (lastCard.getColor() == card.getColor())
					return true;
				else  {
					if (lastCard.getClass() == NormalCard.class && card.getClass() == NormalCard.class){
						if( ((NormalCard) lastCard).getNumber() == ((NormalCard) card).getNumber() )
							return true;
						else
							return false;
					}
					else {
						if(lastCard.getClass() == card.getClass())
							return true;
						else
							return false;
					}
				}
			}
		}
	}
	

	public CardColor getWishedColor() {
		return wishedColor;
	}

	public void setWishedColor(CardColor wishedColor) {
		this.wishedColor = wishedColor;
	}

	public Hand getLocalPlayerHand() {
		return localPlayerHand;
	}

	public void setLocalPlayerHand(Hand localPlayerHand) {
		this.localPlayerHand = localPlayerHand;
	}
	
	
}
