package de.uno.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import biz.source_code.base64Coder.Base64Coder;
import de.highscore.HighScore;
import de.highscore.HighScoreService;
import de.uno.Hand.Hand;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.DrawCard;
import de.uno.gameconnection.GameConnectionManager;
import de.uno.gameconnection.GameConnectionManagerService;
import de.uno.lobbymanagement.Lobby;
import de.uno.lobbymanagement.LobbyService;
import de.uno.player.Player;
import de.uno.usermanagement.UserManagement;
import de.uno.usermanagement.UserManagementService;
/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
public class UnoClient {


	private static GameConnectionManager uno;
	private static UserManagement usermanagement;
	private static Lobby lobbymanager;
	private static HighScore highscore;
	private static Player nico,daniel,dave;
	private static boolean drawedMarker = false;
	
	public static void main(String[] args) {
		try {
			UserManagementService userService = new UserManagementService();
			usermanagement = userService.getUserManagementPort();
			
			LobbyService lobbyService = new LobbyService();
			lobbymanager = lobbyService.getLobbyPort();
			
			System.out.println("Add User: Daniel & Nico");
			usermanagement.addUser("Nico", "nico");
			usermanagement.addUser("Daniel", "daniel");
			
			System.out.println("Add Daniel to Nicos friendlist");
			usermanagement.addUserToFriendlist("Nico", "Daniel");
			
			System.out.println("Show Nicos friends: ");
			String puffer = usermanagement.showFriendList("Nico");
			List<String> friends = (List<String>) deserialize(puffer);
						
			for(String s:friends){
				System.out.println(s);
			}
			
			if(usermanagement.login("Daniel", "daniel"))
				System.out.println("Daniel ist eingeloggt!");
			if(usermanagement.login("Nico", "nico"))
				System.out.println("Nico ist eingeloggt!");
			
			
			lobbymanager.createNewGame("Nico", true);
			lobbymanager.joinLobbyGame("Nico", "Dave");

			lobbymanager.startGame("Nico");
			
			GameConnectionManagerService service = new GameConnectionManagerService();
			uno = service.getGameConnectionManagerPort();
			
			HighScoreService highscoreService = new HighScoreService();
			highscore = highscoreService.getHighScorePort();
			
			nico = new Player("Nico");
			daniel = new Player("Daniel");
			//dave = new Player("Dave");
			uno.createNewGame(serialize(nico));
			uno.addPlayer(serialize(nico), serialize(daniel));
			//uno.addPlayer(serialize(nico), serialize(dave));
			uno.startGame(serialize(nico));
			nico.getHand().addCard(((Hand) deserialize(uno.getHand(serialize(nico)))).getCards());
			daniel.getHand().addCard(((Hand) deserialize(uno.getHand(serialize(daniel)))).getCards());
			//dave.getHand().addCard(((Hand) deserialize(uno.getHand(serialize(daniel)))).getCards());
			
			showCards(nico.getHand(),"Nico");
			showCards(daniel.getHand(), "Daniel");
			//showCards(dave.getHand(), "Dave");
			/*
			while(nico.getHand().getCards().size() != 0 && daniel.getHand().getCards().size() != 0){
				//-------------- Test zum TimeOut --------------------
				//Thread.sleep(31000);
				//break;
				
				//------------- normales Game -----------------------
				placeCard();
				
				if(nico.getHand().getCards().size() == 1){
					uno.callUno(serialize(nico));
				}
				if(daniel.getHand().getCards().size() == 1){
					uno.callUno(serialize(daniel));
				}
				
				if(nico.getHand().getCards().size() == 0){
					break;
				}
				if(daniel.getHand().getCards().size() == 0){
					break;
				}
			}*/
			showCards(nico.getHand(),"Nico");
			showCards(daniel.getHand(), "Daniel");
			
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
		if(((Player)deserialize(uno.getNextPlayer(serialize(nico)))).equals(nico))
			turnPlayer = nico;
		else if (((Player)deserialize(uno.getNextPlayer(serialize(nico)))).equals(daniel))
			turnPlayer = daniel;
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
