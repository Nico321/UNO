package de.uno.android.tasks;



import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.Hand.Hand;
import de.uno.android.GameActivity;
import de.uno.android.GameApplication;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.card.CardImageButton;
import de.uno.player.Player;
/**
 * @author Dave Kaufmann
 */
public class GetHandTask extends GetDataFromServerTask<Player, String, Hand> {
	
	public GetHandTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	/**
	 * 
	 * @param arg serialized Player object
	 * @return Hand
	 */
	@Override
	protected Hand doInBackground(Player... player) {
		try {
			//der zu übergebende Player nach dem der Server sucht
			String playerString = objectSerializer.serialize(player[0]);
			Hand hand = (Hand) objectSerializer.deserialize(gameApp.getGameStub().getHand(playerString).toString());
			
			if(hand != null){
				gameApp.getLocalPlayer().getHand().addCard(hand.getCards());
			}
			
			return hand;
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
			return null;
		}	
	}
	
	/**
	 * Nimmt die deserialisierte Hand entgegen und mapped die einzelnen Karten an die richtigen Bilder.
	 * Anschließend werden diese dann auf dem Screen angezeigt
	 * @return void
	 */
	@Override
	protected void onPostExecute(Hand result) {
		super.onPostExecute(result);
		if(result != null){
			LinearLayout cardScrollViewLayout = (LinearLayout) gameActivity.findViewById(R.id.cardScollViewLayout);

			for (Card c : gameApp.getLocalPlayer().getHand().getCards()) {
				CardImageButton imgb = new CardImageButton(gameActivity);
				imgb.setOnClickListener(gameActivity);
				imgb.setBackgroundDrawable(null);
				gameActivity.loadBitmap(CardMapper.mapCardToResource(c),gameActivity,imgb,60,90);
				cardScrollViewLayout.addView(imgb);
			}
			
			CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
			lastCard.alterMargin(0, 0, 0, 0);
			
		}	
	}
}
