package de.uno.android.tasks;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.CardMapper;
import de.uno.android.util.objectSerializer;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.DrawCard;
import de.uno.player.Player;
/**
 * Task der die getStackCard Methode auf dem Server aufruft und die 
 * erhaltene Karte anschließend auf dem Screen platziert
 * @author Dave Kaufmann 737472
 *
 */
public class GetStackCardTask extends GetDataFromServerTask<Player, String, Card> {
	
	public GetStackCardTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected Card doInBackground(Player... player) {
		try{
		String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
		Card newCard = (Card) objectSerializer.deserialize(gameApp.getGameStub().getStackCard(playerString).toString());
		return newCard;
		}catch (Exception e){
			Log.d(TAG, e.getMessage());
			return null;
		}
		
	}
	
	@Override
	protected void onPostExecute(Card result) {
		super.onPostExecute(result);
		if(result != null){
			Log.d(TAG, result.toString());
			gameApp.addPlayedCard(result);
			ImageView newCard = (ImageView) gameActivity.findViewById(R.id.stackCardView);
			gameActivity.loadBitmap(CardMapper.mapCardToResource(result),gameActivity,newCard,40,75);
			if(gameApp.getActualPlayer().equals(gameApp.getLocalPlayer())){
				if(result instanceof DrawCard){
					if(!((DrawCard)result).isDrawed()){
						DrawCardTask drawCard = new DrawCardTask(gameActivity);
						drawCard.execute(((DrawCard) result).getQuantity());
					}
				}

			}
			if(result.getColor() == CardColor.BLACK){
				GetWishedColorTask getWishedColor = new GetWishedColorTask(gameActivity);
				getWishedColor.execute();
			}else{
				TextView wishedColor = (TextView) gameActivity.findViewById(R.id.wishedColor);
				wishedColor.setText(" ");
			}
		}

	}	
}
