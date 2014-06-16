package de.uno.android.tasks;

import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.card.CardImageButton;
import de.uno.player.Player;
/**
 * Task der die getStackCard Methode auf dem Server aufruft und die erhaltene Karte anschließend auf dem Screen platziert
 * @author Dave Kaufmann 737472
 *
 */
public class GetStackCardTask extends GetDataFromServerTask<Player, String, Card> {
	
	public GetStackCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected Card doInBackground(Player... player) {
		try{
		String playerString = objectSerializer.serialize(player[0]);
		Card card = (Card) objectSerializer.deserialize(gameApp.getGameStub().getStackCard(playerString).toString());

		if(card != null){
			gameApp.addPlayedCard(card);
		}
		return card;
		
		}catch (Exception e){
			Log.d(TAG, e.getMessage());
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Card result) {
		super.onPostExecute(result);
		if(result != null){
			ImageView newCard = (ImageView) gameActivity.findViewById(R.id.cardStackView);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(result),gameActivity,newCard,40,75);
		}

	}
	

	
	

}
