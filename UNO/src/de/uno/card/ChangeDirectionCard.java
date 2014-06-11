package de.uno.card;


/**
 *  
 * @author Nico Lindmeyer 737045
 * 
 */

public class ChangeDirectionCard extends Card
{
	private final int cardValue = 20;
	private static final long serialVersionUID = 1L;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public ChangeDirectionCard(){
		super();
	}
	
	@Override
	public int getCardvalue() {
		return cardValue;
	}
}

