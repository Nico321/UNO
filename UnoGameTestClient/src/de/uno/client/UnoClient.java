package de.uno.client;

import java.util.Iterator;
import java.util.LinkedList;

import javax.naming.InitialContext;

import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.DrawCard;
import de.uno.common.CardNotValidException;
import de.uno.common.GameRemote;
import de.uno.player.Player;

public class UnoClient {

	private static GameRemote uno;
	private static Player nico,daniel;
	private static boolean drawedMarker = false;
	
	public static void main(String[] args) {
		try {
			InitialContext context = new InitialContext();
		       
			//Lookup-String für eine EJB besteht aus: Name_EA/Name_EJB-Modul/Name_EJB-Klasse!Name_RemoteInterface
			String lookupString = "ejb:UnoEAR/UnoGame/Game!de.uno.common.GameRemote?stateful";
			uno = (GameRemote) context.lookup(lookupString);
			   
			//Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
			System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
			System.out.println(uno.toString());
			System.out.println();
			nico = new Player("Nico");
			daniel = new Player("Daniel");
			uno.addPlayer(nico);
			uno.addPlayer(daniel);
			uno.startGame();
			nico.getHand().addCard(uno.getHand(nico).getCards());
			daniel.getHand().addCard(uno.getHand(daniel).getCards());
			
			showCards(nico.getHand(),"Nico");
			showCards(daniel.getHand(), "Daniel");
			
			while(nico.getHand().getCards().size() != 0 && daniel.getHand().getCards().size() != 0){
				placeCard();
			}
			showCards(nico.getHand(),"Nico");
			showCards(daniel.getHand(), "Daniel");
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	private static void placeCard(){
		Player turnPlayer = null;
		if(uno.getNextPlayer().equals(nico))
			turnPlayer = nico;
		else if (uno.getNextPlayer().equals(daniel))
			turnPlayer = daniel;
		
		if(uno.getStackCard().getClass() == DrawCard.class && drawedMarker){
			LinkedList<Card> drawCard = uno.drawCard(((DrawCard) uno.getStackCard()).getQuantity());
			turnPlayer.getHand().addCard(drawCard);
			drawedMarker = false;
		}
		else {
			for(Iterator<Card> it = turnPlayer.getHand().getCards().iterator(); it.hasNext();){
				Card card = it.next();
				try{
					uno.putCard(card);
					drawedMarker = true;
					if(card.getColor() == CardColor.BLACK)
						uno.setWishedColor(CardColor.BLUE);
					System.out.println(turnPlayer.getUsername() + " played " + card);
					it.remove();
					break;
				}
				catch (CardNotValidException ex){
					System.out.println(turnPlayer.getUsername() +" Card not Valid: " + uno.getStackCard() + " --- " + card);
					if(!it.hasNext()){
						LinkedList<Card> drawCard = uno.drawCard(1);
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
