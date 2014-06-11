package de.uno.android.tasks;

import android.util.Log;
import android.widget.ImageView;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.player.Player;

public class PutCardTask extends GetDataFromServerTask<Object, Void, Boolean> {
	private Card card;
	public PutCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Boolean doInBackground(Object... arg0) {
		try {
			Player player = (Player) arg0[0];
			card  = (Card) arg0[1];
			String playerString = objectSerializer.serialize(player);
			String cardString = objectSerializer.serialize(card);
			boolean result = gameApp.getGameStub().putCard(playerString, cardString);
			
			
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
			return null;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			ImageView newCard = (ImageView) gameActivity.findViewById(R.id.cardStackView);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(card),gameActivity,newCard,40,75);
		}
	}
 
}
