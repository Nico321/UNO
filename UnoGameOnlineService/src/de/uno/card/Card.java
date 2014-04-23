package de.uno.card;

import java.io.Serializable;


/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public abstract class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private CardColor color;
	
	public Card(){
		super();
	}
	
	public CardColor getColor(){
		return color;
	}
	
	public void setColor(CardColor color){
		this.color = color;
	}
	
	@Override
	public boolean equals(Object o){
		if (this.getClass() == o.getClass()){
			if (this.getColor() == ((Card) o).getColor()) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
		
	}
	
	@Override
	public String toString(){
		return this.getClass().getName() + " " + this.color.toString();
	}
	
	public int getCardvalue(){return 0;};
}

