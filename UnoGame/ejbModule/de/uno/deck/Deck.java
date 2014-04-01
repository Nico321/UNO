package de.uno.deck;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.ChangeDirectionCard;
import de.uno.card.DrawCard;
import de.uno.card.NormalCard;
import de.uno.card.SkipCard;

/**
 * Session Bean implementation class UnoDeck
 */

public class Deck{

	private LinkedList<Card> cards;
    /**
     * Default constructor. 
     */
    public Deck() {
        cards = new LinkedList<Card>();
    }

    
	public void addCard(Card card) {
		cards.addFirst(card);
		
	}

	public void addCard(LinkedList<Card> card) {
		for(Iterator<Card> it = card.iterator(); it.hasNext();){
			cards.addLast(it.next());
			it.remove();
		}
		
	}

	public Card removeCard() {		
		return cards.removeFirst();
	}

	public LinkedList<Card> removeCard(Integer quantity) {
		LinkedList<Card> returnCards = new LinkedList<Card>();
		for(int i = 0; i<quantity; i++){
			returnCards.add(cards.removeFirst());
		}
		return returnCards;
	}

	public Integer count() {
		return cards.size();
	}

	public Card getFirstCard() {
		return cards.getFirst();
	}

	public LinkedList<Card> cleanDeck() {
		LinkedList<Card> returnCards = new LinkedList<Card>();
		while(cards.size()>1){
			returnCards.add(cards.removeLast());
		}
		return returnCards;
	}

	public void shuffleCards() {
		Collections.shuffle(cards);
	}
	
	public void initializeCompleteDeck()
	{
		CardColor[] colors = CardColor.values();
		
		for (int i = 0; i<colors.length;i++){
			if(colors[i] != CardColor.BLACK){
				NormalCard card = new NormalCard();
				card.setColor(colors[i]);
				card.setNumber(0);
				this.cards.add(card);
			}
		}
		
		for (int i = 1; i<= 9; i++){
			for (int j = 0; j<colors.length;j++){
				if(colors[j] != CardColor.BLACK){
					for (int y = 0; y<=1; y++){
						NormalCard card = new NormalCard();
						card.setColor(colors[j]);
						card.setNumber(i);
						this.cards.add(card);
					}
				}
			}
		}
		
		for(int j = 0 ; j<=1; j++){
			for (int i = 0; i<colors.length;i++){
				if(colors[i] != CardColor.BLACK){
					SkipCard skipCard = new SkipCard();
					ChangeDirectionCard changeCard = new ChangeDirectionCard();
					DrawCard drawCard = new DrawCard();
					
					skipCard.setColor(colors[i]);
					changeCard.setColor(colors[i]);
					drawCard.setColor(colors[i]);
					drawCard.setQuantity(2);
					
					this.cards.add(skipCard);
					this.cards.add(changeCard);
					this.cards.add(drawCard);
				}
			}
		}
		
		for(int i = 0; i<=3; i++){
			NormalCard card = new NormalCard();
			DrawCard drawCard = new DrawCard();
			
			card.setColor(CardColor.BLACK);
			drawCard.setColor(CardColor.BLACK);
			drawCard.setQuantity(4);
			
			this.cards.add(card);
			this.cards.add(drawCard);
		}
	}

}
