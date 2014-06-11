package de.uno.card;


/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public class DrawCard extends Card
{
	private static final long serialVersionUID = 1L;
	private final int cardValue = 20;
	private int quantity;
	
	public DrawCard(){
		super();
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	@Override
	public String toString(){
		return this.getClass().getName() + " " + this.getColor().toString() + " +" + quantity;
	}
	
	@Override
	public boolean equals(Object o){
		if (DrawCard.class == o.getClass()){
			if (this.getColor() == ((DrawCard) o).getColor() && this.getQuantity() == ((DrawCard) o).getQuantity()) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
		
	}
	
	@Override
	public int getCardvalue() {
		if(this.getColor() == CardColor.BLACK)
			return 50;
		else
			return cardValue;
	}
}

