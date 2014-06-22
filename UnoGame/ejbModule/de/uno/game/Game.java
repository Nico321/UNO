package de.uno.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.ChangeDirectionCard;
import de.uno.card.DrawCard;
import de.uno.card.NormalCard;
import de.uno.card.SkipCard;
import de.uno.deck.Deck;
import de.uno.gamemanager.GameManagerLocal;
import de.uno.player.Player;

/**
 * Session Bean implementation class UnoGame
 * 
 * @author Nico Lindmeyer 737045
 * 
 */
public class Game implements GameLocal{
	
	private HashMap<Integer, Player> players;
	private LinkedList<Player> winners;
	private int playerStep = 1, currentPlayer = 1, gameProgress = 0, direction = 1;
	private Deck deck, stack;
	private CardColor wishedColor;
	
	private final long TIMEOUT = Integer.MAX_VALUE;
	Timer timer = new Timer();
	MyTimerTask task = new MyTimerTask(this);
	
	private GameManagerLocal gameManager;
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	@Override
	public CardColor getWishedColor() {
		return wishedColor;
	}

	@Override
	public void setWishedColor(CardColor wishedColor) {
		this.wishedColor = wishedColor;
	}

    public Game(Player player) {
		
        deck = new Deck();
        deck.initializeCompleteDeck();
        deck.shuffleCards();
        
        stack = new Deck();
        
        players = new HashMap<Integer, Player>();
        players.put(1,player);

        
    }

	@Override
	public Player getNextPlayer() {
		return players.get(currentPlayer);
	}

	public boolean checkGameState(){
		if(getNumberOfPlayers() == 1)
		{
			closeGame();
			return true;
		}
		else{
			for(Player p:players.values()){
				if(p.getHand().getCards().size() == 0){
					if(p.calledUno()){
						log.info(p.getUsername() + " won the game");
						closeGame();
						return true;	
					}
					else{
						this.drawCard(p, 1);
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private void updateCurrentPlayerID(){
		currentPlayer += (playerStep * direction);
		if (currentPlayer > players.size()){
			while(currentPlayer > players.size()){
				currentPlayer -= players.size();
			}
		}
		if (currentPlayer < 1){
			while(currentPlayer < 1){
				currentPlayer += players.size();
			}
		}
		if(players.get(currentPlayer).getDisconnected() != null){
			playerStep = 1;
			
			updateCurrentPlayerID();
		}
		gameProgress++;
	}
	
	private int getNumberOfPlayers(){
		int puffer = 0;
		for(Player p : players.values()){
			if(p.getDisconnected() == null)
				puffer++;
		}
		return puffer;
	}
	
	@Override
	public boolean putCard(Player player, Card card){
		if(player.equals(this.getNextPlayer())){
			if(cardIsValid(card)){
				stack.addCard(card);
				if (card.getClass() == SkipCard.class){
					System.out.println("Skippiskip");
					playerStep = 2;
				}
				else if (card.getClass() == ChangeDirectionCard.class){
					if(players.size() > 2){
						direction *= -1;
						playerStep = 1;
					}
					else
						playerStep = 0;					
				}
				else{
					playerStep = 1;
				}
				
				this.getNextPlayer().getHand().removeCard(card);
				if (!checkGameState()){
					updateCurrentPlayerID();
					task.cancel();
					task = new MyTimerTask(this);
					timer.scheduleAtFixedRate(task, TIMEOUT, TIMEOUT);
				}
				log.info(player.getUsername() + " played " + card + " - " + this.getNextPlayer().getUsername() + " is next.");
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	private void closeGame(){
		log.info("Game finished, trying to write Highscore");
		InitialContext context;
		try {
			context = new InitialContext();
			String lookupString = "java:global/UnoEAR/UnoGame/GameManager!de.uno.gamemanager.GameManagerLocal";
			gameManager = (GameManagerLocal) context.lookup(lookupString);
		}
		catch (NamingException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		HashMap<Player, Integer> playerPoints = sortPlayers();
		
		while(winners.size()>1){
			for (Iterator<Player> it = winners.iterator(); it.hasNext();) {
			    Player p = it.next();
			    
			    while(it.hasNext()){			    
			    	playerPoints.put(p, playerPoints.get(p) + it.next().getPoints());
			    }
			    winners.removeFirst();
			}
		}
		for(Player p : playerPoints.keySet()){
			if(p.getDisconnected() != null)
			{
				playerPoints.put(p, 0);
			}
		}
		
		gameManager.updateHighScore(playerPoints);
		log.info("Highscore successfully updated, closing game");
		gameManager.removeGame(this);
			
			
		
		System.out.println("Timer canceled");
		task.cancel();
		timer.cancel();
	}
	
	private HashMap<Player, Integer> sortPlayers(){
		winners = new LinkedList<Player>();
		int bonus = 100, count = 0;
		
		HashMap<Player, Integer> sortedPlayers = new HashMap<Player, Integer>();
		
		while(sortedPlayers.size() != players.size()){
			int puffer = 999;
			Player playerPuff = null;
			for(Player p : players.values()){
				if(!sortedPlayers.containsKey(p)){
					if(p.getHand().getCards().size() < puffer){
						puffer = p.getHand().getCards().size();
						playerPuff = p;
					}
				}
			}
			sortedPlayers.put(playerPuff, bonus - count);
			count +=30;
			winners.addLast(playerPuff);
		}
		
		return sortedPlayers;
		
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
	public HashMap<String, Integer> getGameStatus() {
		HashMap<String, Integer> gameStatus = new HashMap<String, Integer>();
		for(int i = 1; i <= players.size(); i++){
			gameStatus.put(players.get(i).getUsername(),players.get(i).getHand().getCards().size());
		}
		return gameStatus;
	}

	@Override
	public Card getStackCard() {
		return stack.getFirstCard();
	}

	@Override
	public LinkedList<Card> drawCard(Player player, int quantity) {
		if(player.equals(this.getNextPlayer())){
			if(deck.count() < quantity){
				deck.addCard(stack.cleanDeck());
			}
			LinkedList<Card> returnCard = deck.removeCard(quantity);
			this.getNextPlayer().getHand().addCard(returnCard);
			if(quantity == 1){
					playerStep = 1;
				updateCurrentPlayerID();
			}
			if(quantity >= 2){
				if(this.getStackCard() instanceof DrawCard){
					((DrawCard)this.getStackCard()).setDrawed(true);
					gameProgress++;
				}
			}
			if(player.calledUno()){
				this.callLocalUno(player, false);
			}
			log.info(player.getUsername() +  " drawed " + quantity  + " cards");
			return returnCard;
		}
		else
			return null;
	}

	@Override
	public void addPlayer(Player player) {
		players.put(players.size() + 1, player);
	}

	@Override
	public void startGame() {
		log.info("starting game...");
		for(int i = 1; i<=players.size();i++){
			players.get(i).getHand().addCard(deck.removeCard(5));
		}
		do{
			stack.addCard(deck.removeCard());
		}
		while(stack.getFirstCard().getClass() == DrawCard.class || stack.getFirstCard().getColor() == CardColor.BLACK || stack.getFirstCard().getClass() == SkipCard.class || stack.getFirstCard().getClass() == ChangeDirectionCard.class);
		
		
		timer.scheduleAtFixedRate(task, TIMEOUT, TIMEOUT);
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

	@Override
	public boolean playerAssignedToGame(Player player) {
		for(Player p : players.values()){
			if(p.equals(player))
				return true;
		}
		return false;
	}

	@Override
	public boolean callUno(Player player) {
		if (player.getHand().getCards().size() == 1){
			log.info(player.getUsername() + " called Uno");
			callLocalUno(player, true);
			return true;
		}
		else
			return false;
	}
	
	private void callLocalUno(Player remotePlayer, boolean value){
		remotePlayer.callUno(value);
		for(Player p : players.values()){
			if (p.equals(remotePlayer))
				p.callUno(value);
		}
	}

	@Override
	public int getGameProgress() {
		return gameProgress;
	}
}
