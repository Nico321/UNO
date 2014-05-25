package de.uno.Hand;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import de.uno.card.Card;

/**
 * Session Bean implementation class UnoHand
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public class Hand implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private LinkedList<Card> cards;
    /**
     * Default constructor. 
     */
    public Hand() {
        cards = new LinkedList<Card>();
    }

	public void addCard(Card card) {
		cards.add(card);		
	}

	public void addCard(LinkedList<Card> cards) {
		for(Iterator<Card> it = cards.iterator(); it.hasNext();){
			this.cards.add(it.next());
		}
		
	}

	public void removeCard(Card card) {
		for(Iterator<Card> it = cards.iterator(); it.hasNext();){
			Card myCard = it.next();
			if(myCard.equals(card)) {
				it.remove();
			}
		}
	}

	public LinkedList<Card> getCards() {
		return cards;
	}

}
