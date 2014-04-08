package de.uno.card;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class SkipCard extends Card
{
	private final int cardValue = 20;
	private static final long serialVersionUID = 1L;


	public SkipCard(){
		super();
	}
	
	@Override
	public int getCardvalue() {
		return cardValue;
	}
}

