package de.uno.android.tasks;



import android.util.Log;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.Hand.Hand;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.android.views.CardImageButton;
import de.uno.card.Card;
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
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			Hand hand = (Hand) objectSerializer.deserialize(gameApp.getGameStub().getHand(playerString).toString());
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
			gameApp.setLocalPlayerHand(result);
			Hand localPlayerHand = gameApp.getLocalPlayerHand();
			for (Card card : localPlayerHand.getCards()) {
				CardImageButton imgb = new CardImageButton(gameActivity,gameActivity,gameApp,card);
				imgb.setBackgroundDrawable(null);
				gameActivity.loadBitmap(CardMapper.mapCardToResource(card),gameActivity,imgb,60,90);
				cardScrollViewLayout.addView(imgb);
			}
			
			if(result.getCards().size() >0){
				CardImageButton lastCard = (CardImageButton) cardScrollViewLayout.getChildAt(cardScrollViewLayout.getChildCount()-1);
				lastCard.alterMargin(0, 0, 0, 0);
			}
			
		}	
	}
}
