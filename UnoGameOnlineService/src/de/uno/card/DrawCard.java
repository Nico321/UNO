package de.uno.card;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class DrawCard extends Card
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private int quantity;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
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
}

