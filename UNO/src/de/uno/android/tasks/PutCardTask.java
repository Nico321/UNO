package de.uno.android.tasks;

import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.card.CardImageButton;
import de.uno.player.Player;

public class PutCardTask extends GetDataFromServerTask<Object, Void, Boolean> {
	private Card card;
	private int cardPosition;
	private CardImageButton cib;
	
	public PutCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Boolean doInBackground(Object... arg0) {
		try {
			Player player = (Player) arg0[0];
			card  = (Card) arg0[1];
			cardPosition = (int) arg0[2];
			cib = (CardImageButton) arg0[3];
			String playerString = objectSerializer.serialize(player);
			String cardString = objectSerializer.serialize(card);
			boolean result = gameApp.getGameStub().putCard(playerString, cardString);
			return result;
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			//neue Karte auf den gespielten Stapel
			gameApp.addPlayedCard(card);
			ImageView newCard = (ImageView) gameActivity.findViewById(R.id.cardStackView);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(card),gameActivity,newCard,40,75);
			
			//Karte lokal entfernen
			gameApp.getLocalPlayerHand().removeCard(card);
			((LinearLayout)cib.getParent()).removeView(cib);
			
			
			
			//äußerste Karte anpassen
			LinearLayout cardScrollViewLayout = (LinearLayout) gameActivity.findViewById(R.id.cardScollViewLayout);
			CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
			lastCard.alterMargin(0, 0, 0, 0);
		}
	}
 
}
