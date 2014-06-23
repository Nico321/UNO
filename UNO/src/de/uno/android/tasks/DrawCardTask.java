package de.uno.android.tasks;

import java.util.LinkedList;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.android.views.CardImageButton;
import de.uno.card.Card;

public class DrawCardTask extends GetDataFromServerTask<Integer, String, LinkedList<Card>> {
	
	
	public DrawCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		gameActivity.findViewById(R.id.cardScollViewLayout).setClickable(false);
	}
	@Override
	protected LinkedList<Card> doInBackground(Integer... quantity) {
		try{
		int drawQuantity = quantity[0];
		String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
		LinkedList<Card> drawedCards = (LinkedList<Card>) objectSerializer.deserialize(gameApp.getGameStub().drawCard(playerString, drawQuantity).toString());
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
		
		gameApp.getLocalPlayerHand().addCard(result);
		//Den rechten Rand der momentanen ganz rechten Karte entfernen
		CardImageButton tmpCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
		tmpCard.alterMargin(0, 0, -80, 0);
		
		//Die gezogenen Karten der View hinzufügen
		for (Card c:result){
			CardImageButton newCard = new CardImageButton(gameActivity,gameActivity,gameApp,c);
			newCard.setBackgroundDrawable(null);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(c),gameActivity,newCard,60,90);
			cardScrollViewLayout.addView(newCard);
		}
		CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
		lastCard.alterMargin(0, 0, 0, 0);

		//UnoButton deaktivieren
		ImageButton unoButton = (ImageButton) gameActivity.findViewById(R.id.unoButton);
		unoButton.setClickable(false);
		
		//nächsten Spieler aufrufen
		GetCurrentPlayerTask gnpt = new GetCurrentPlayerTask(gameActivity);
		gnpt.execute();
	}



}
