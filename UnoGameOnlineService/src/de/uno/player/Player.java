package de.uno.player;

import java.io.Serializable;
import java.util.Date;

import de.uno.Hand.Hand;
import de.uno.card.Card;

/**
 * Session Bean implementation class UnoPlayer
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Hand hand;
	private String username;
	private Date disconnected;
	private boolean calledUno;
	
    public String getUsername() {
		return username;
	}

	/**
     * Default constructor. 
     */
    public Player(String username) {
        this.username = username;
        hand = new Hand();
    }

	public Hand getHand() {
		return hand;
	}
	
	public int getPoints(){
		int points = 0;
		for (Card c : this.getHand().getCards()){
			points += c.getCardvalue();
		}
		return points;
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() == Player.class){
			return ((Player) o ).getUsername().equals(this.username);
		}
		else
			return false;
	}

	public Date getDisconnected() {
		return disconnected;
	}

	public void setDisconnected(Date disconnected) {
		this.disconnected = disconnected;
	}

	public boolean calledUno() {
		return calledUno;
	}

	public void callUno(boolean calledUno) {
		this.calledUno = calledUno;
	}

}
