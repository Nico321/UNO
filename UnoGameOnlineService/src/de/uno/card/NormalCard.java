package de.uno.card;


/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public class NormalCard extends Card
{
	private final int cardValue = 0;
	private static final long serialVersionUID = 1L;
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private int number;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public NormalCard(){
		super();
	}
	
	@Override
	public String toString(){
		return this.getClass().getName() + " " + this.getColor().toString() + " " + number;
	}
	
	@Override
	public boolean equals(Object o){
		if (NormalCard.class == o.getClass()){
			if (this.getColor() == ((NormalCard) o).getColor() && this.getNumber() == ((NormalCard) o).getNumber()) {
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

