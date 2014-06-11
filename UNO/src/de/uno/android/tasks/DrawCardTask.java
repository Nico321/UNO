package de.uno.android.tasks;

import java.util.LinkedList;

import android.util.Log;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.card.CardImageButton;
import de.uno.card.DrawCard;
import de.uno.player.Player;

public class DrawCardTask extends GetDataFromServerTask<Player, String, LinkedList<Card>> {
	
	
	public DrawCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected LinkedList<Card> doInBackground(Player... player) {
		try{
		String playerString = objectSerializer.serialize(player[0]);
		LinkedList<Card> drawedCards = (LinkedList<Card>) objectSerializer.deserialize(gameApp.getGameStub().drawCard(playerString, checkQuantity()).toString());
		if(drawedCards != null){
			gameApp.getLocalPlayer().getHand().addCard(drawedCards);
		}
		return drawedCards;
		
		}catch (Exception e){
			Log.d(TAG, e.getMessage());
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(LinkedList<Card> result) {
		super.onPostExecute(result);
		LinearLayout cardScrollViewLayout = (LinearLayout) gameActivity.findViewById(R.id.cardScollViewLayout);
		
		//Den rechten Rand der momentanen ganz rechten Karte entfernen
		CardImageButton tmpCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
		tmpCard.alterMargin(0, 0, -80, 0);
		
		//Die gezogenen Karten der View hinzufügen
		for (Card c:result){
			CardImageButton newCard = new CardImageButton(gameApp.getApplicationContext());
			newCard.setOnClickListener(gameActivity);
			newCard.setBackgroundDrawable(null);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(c),gameActivity,newCard,60,90);
			cardScrollViewLayout.addView(newCard);
		}
		CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
		lastCard.alterMargin(0, 0, 0, 0);
		
	}

	/**
	 * TODO!
	 * @param none
	 * @return int
	 */
	private int checkQuantity(){
		return 1;
	}
	

}
