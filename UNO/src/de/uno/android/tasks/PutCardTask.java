package de.uno.android.tasks;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.common.GameStub;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.android.views.CardImageButton;
import de.uno.android.views.CardImageView;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.player.Player;

public class PutCardTask extends GetDataFromServerTask<Object, Void, Boolean> {
	private Card card;
	private CardImageButton cib;
	
	public PutCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Boolean doInBackground(Object... arg0) {
		boolean result = false;
		try {
			Player player = (Player) arg0[0];
			card  = (Card) arg0[1];
			cib = (CardImageButton) arg0[2];
			String playerString = objectSerializer.serialize(player);
			String cardString = objectSerializer.serialize(card);
			Log.d(TAG, "Try to put Card " + cardString + " from " + player.toString());
			 result = gameApp.getGameStub().putCard(playerString, cardString);
			return result;
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			//neue Karte auf den gespielten Stapel
			gameApp.addPlayedCard(card);
			
			if(gameApp.getLastPlayedCard().getColor()!= CardColor.BLACK){
				TextView wishedColor = (TextView) gameActivity.findViewById(R.id.wishedColor);
				wishedColor.setText(" ");
			}
			
			ImageView newCard = (ImageView) gameActivity.findViewById(R.id.stackCardView);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(card),gameActivity,newCard,40,75);
			
			//Karte lokal entfernen
			Log.d(TAG, "Try to remove " + card.toString() + " from local Hand");
			gameApp.getLocalPlayerHand().removeCard(card);
			((LinearLayout)cib.getParent()).removeView(cib);

			//äußerste Karte anpassen
			LinearLayout cardScrollViewLayout = (LinearLayout) gameActivity.findViewById(R.id.cardScollViewLayout);
			if(gameApp.getLocalPlayerHand().getCards().size() > 0){
				CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
				lastCard.alterMargin(0, 0, 0, 0);
			}
			
			if(gameApp.getLocalPlayerHand().getCards().size() == 0 && !gameApp.getLocalPlayer().calledUno()){
				GetHandTask getHand = new GetHandTask(gameActivity);
				getHand.execute();
			}
			
			//UnoButton aktivieren falls vorletzte Karte
			if(gameApp.getLocalPlayerHand().getCards().size() == 1){
				ImageButton unoButton = (ImageButton) gameActivity.findViewById(R.id.unoButton);
				unoButton.setClickable(true);
			}
			
			//nächsten Spieler aufrufen
			GetCurrentPlayerTask gnpt = new GetCurrentPlayerTask(gameActivity);
			gnpt.execute();
			
		}
		
	}
 
}
