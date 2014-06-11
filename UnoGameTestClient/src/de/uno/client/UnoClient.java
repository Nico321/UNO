package de.uno.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import biz.source_code.base64Coder.Base64Coder;
import de.highscore.HighScore;
import de.highscore.HighScoreService;
import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.DrawCard;
import de.uno.gameconnection.GameConnectionManager;
import de.uno.gameconnection.GameConnectionManagerService;
import de.uno.player.Player;
/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public class UnoClient {


	private static GameConnectionManager uno;
	private static HighScore highscore;
	private static Player dave,nico;
	private static boolean drawedMarker = false;
	private static Hand daveCards;
	private static Hand nicoCards;
	
	public static void main(String[] args) {
		try {
			GameConnectionManagerService service = new GameConnectionManagerService();
			uno = service.getGameConnectionManagerPort();
			
			HighScoreService highscoreService = new HighScoreService();
			highscore = highscoreService.getHighScorePort();
			
			dave = new Player("Dave");
			nico = new Player("Nico");
			uno.createNewGame(serialize(dave));
			uno.addPlayer(serialize(dave), serialize(nico));
			uno.startGame(serialize(dave));
			dave.getHand().addCard(((Hand) deserialize(uno.getHand(serialize(dave)))).getCards());
			nico.getHand().addCard(((Hand) deserialize(uno.getHand(serialize(nico)))).getCards());
			
			daveCards = dave.getHand();
			nicoCards = nico.getHand();
			
			showCards(dave.getHand(),"Nico");
			showCards(nico.getHand(), "Daniel");
			
			while(dave.getHand().getCards().size() != 0 && nico.getHand().getCards().size() != 0){
				
				
				/*
				placeCard();
				if(dave.getHand().getCards().size() == 0){
					break;
				}
				if(nico.getHand().getCards().size() == 0){
					break;
				}
				*/
			}
			showCards(dave.getHand(),"Nico");
			showCards(nico.getHand(), "Daniel");
			
			Thread.sleep(1000); // Wait for asynchronous method
			System.out.println("============HighScore==============");
			System.out.println(deserialize(highscore.getHighscore()));
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
    private static String serialize(Serializable o){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }
	
    private static Object deserialize(String s){
		byte [] data = Base64Coder.decode( s );
        ObjectInputStream ois;
        Object o = null;
		try {
			ois = new ObjectInputStream( 
			                                new ByteArrayInputStream(  data ) );
	        try {
				o  = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return o;
	}
	
    private static void placeCard(){
		Player turnPlayer = null;
		if(((Player)deserialize(uno.getNextPlayer(serialize(dave)))).equals(dave))
			turnPlayer = dave;
		else if (((Player)deserialize(uno.getNextPlayer(serialize(dave)))).equals(nico))
			turnPlayer = nico;
		if(((Card)deserialize(uno.getStackCard(serialize(turnPlayer)))).getClass() == DrawCard.class && drawedMarker){
			LinkedList<Card> drawCard = (LinkedList<Card>) deserialize(uno.drawCard(serialize(turnPlayer), ((DrawCard) deserialize(uno.getStackCard(serialize(turnPlayer)))).getQuantity()));
			turnPlayer.getHand().addCard(drawCard);
			drawedMarker = false;
		}
		else {
			for(Iterator<Card> it = turnPlayer.getHand().getCards().iterator(); it.hasNext();){
				Card card = it.next();
					if(uno.putCard(serialize(turnPlayer), serialize(card))) {
						if (turnPlayer.getHand().getCards().size() > 1){
						drawedMarker = true;
						if(card.getColor() == CardColor.BLACK)
							uno.setWishedColor(serialize(turnPlayer), serialize(CardColor.BLUE));
						System.out.println(turnPlayer.getUsername() + " played " + card + " --- cards left:" + (turnPlayer.getHand().getCards().size() - 1));
						turnPlayer.getHand().removeCard(card);
						break;
						}
						else {
							turnPlayer.getHand().removeCard(card);
							System.out.println(turnPlayer.getUsername() + " hat gewonnen :) !");
							break;
						}
					}
					else {
						System.out.println(turnPlayer.getUsername() +" Card not Valid: " + ((Card)deserialize(uno.getStackCard(serialize(turnPlayer)))) + " --- " + card);
						if(!it.hasNext()){
							LinkedList<Card> drawCard = (LinkedList<Card>) deserialize(uno.drawCard(serialize(turnPlayer), 1));
							turnPlayer.getHand().addCard(drawCard);
							System.out.println(turnPlayer.getUsername() + " drawed: " +drawCard.getFirst());
							break;
						}					
				}
			}
		}
	}
	
	private static void showCards(Hand hand, String username){
		LinkedList<Card> cards = hand.getCards();
		System.out.println("=============="+ username +" Cards" +"================");
		for(Iterator<Card> it = cards.iterator(); it.hasNext();){
			System.out.println(it.next().toString());
		}
		System.out.println("==============================");
	}

}
