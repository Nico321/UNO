package de.uno.game;

import java.util.HashMap;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.NormalCard;
import de.uno.common.CardNotValidException;
import de.uno.common.GameRemote;
import de.uno.deck.Deck;
import de.uno.player.Player;

/**
 * Session Bean implementation class UnoGame
 */
@Stateful
@Remote(GameRemote.class)
public class Game implements GameRemote{

	private HashMap<Integer, Player> players;
	private int playerStep = 1, currentPlayer = 1;
	private Deck deck, stack;
	private CardColor wishedColor;

	@Override
	public CardColor getWishedColor() {
		return wishedColor;
	}

	@Override
	public void setWishedColor(CardColor wishedColor) {
		this.wishedColor = wishedColor;
	}

	@PostConstruct
    public void GamePrep() {
		
		
        deck = new Deck();
        deck.initializeCompleteDeck();
        deck.shuffleCards();
        
        stack = new Deck();
        
        players = new HashMap<Integer, Player>();
    }

	@Override
	public Player getNextPlayer() {
		return players.get(currentPlayer);
	}

	private void updateCurrentPlayerID(){
		currentPlayer += playerStep;
		if (currentPlayer > players.size()){
			currentPlayer = 1;
		}
		if (currentPlayer < 1){
			currentPlayer = players.size();
		}
	}
	
	@Override
	public void putCard(Card card) throws CardNotValidException{
		if(cardIsValid(card)){
			stack.addCard(card);
			if (card.getClass().getName().equals("SkipCard")){
				if (playerStep <2 && playerStep > -2)
					playerStep *= 2;
			}
			else if (card.getClass().getName().equals("ChangeDirectionCard")){
				playerStep *= -1;
			}
			else{
				playerStep = 1;
			}
			
			this.getNextPlayer().getHand().removeCard(card);
			updateCurrentPlayerID();
		}
		else
			throw new CardNotValidException("Card not valid.");
	}
	
	private boolean cardIsValid(Card card){
		Card lastCard = stack.getFirstCard();
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
	
	@Override
	public HashMap<String, Integer> getPlayerStatus() {
		HashMap<String, Integer> playerStatus = new HashMap<String, Integer>();
		for(int i = 1; i <= players.size(); i++){
			playerStatus.put(players.get(i).getUsername(),players.get(i).getHand().getCards().size());
		}
		return playerStatus;
	}

	@Override
	public Card getStackCard() {
		return stack.getFirstCard();
	}

	@Override
	public LinkedList<Card> drawCard(int quantity) {
		if(deck.count() < quantity){
			deck.addCard(stack.cleanDeck());
		}
		LinkedList<Card> returnCard = deck.removeCard(quantity);
		this.getNextPlayer().getHand().addCard(returnCard);
		if(playerStep < 0)
			playerStep = -1;
		else
			playerStep = 1;
		updateCurrentPlayerID();
		return returnCard;
	}

	@Override
	public void addPlayer(Player player) {
		players.put(players.size() + 1, player);
	}

	@Override
	public void startGame() {
		for(int i = 1; i<=players.size();i++){
			players.get(i).getHand().addCard(deck.removeCard(5));
		}
		stack.addCard(deck.removeCard());
	}

	@Override
	public Hand getHand(Player player) {
		for(int i = 1; i<=players.size();i++){
			if(players.get(i).equals(player)){
				return players.get(i).getHand();
			}
		}
		return null;
	}

}
