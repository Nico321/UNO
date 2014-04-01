package de.uno.player;

import java.io.Serializable;

import de.uno.Hand.Hand;

/**
 * Session Bean implementation class UnoPlayer
 */

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Hand hand;
	private String username;
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
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() == Player.class){
			return ((Player) o ).getUsername().equals(this.username);
		}
		else
			return false;
	}

}
