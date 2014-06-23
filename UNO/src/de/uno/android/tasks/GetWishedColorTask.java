package de.uno.android.tasks;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.card.CardColor;

public class GetWishedColorTask extends GetDataFromServerTask<Void, Void, CardColor> {

	public GetWishedColorTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected CardColor doInBackground(Void... params) {
		CardColor result = null;
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			result = (CardColor) objectSerializer.deserialize(gameApp.getGameStub().getWishedColor(playerString));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(CardColor result) {
		super.onPostExecute(result);
		if(result!= null){
			Log.d(TAG,"getWishedColor" + result.toString());
			gameApp.setWishedColor(result);
			TextView wishedColor = (TextView) gameActivity.findViewById(R.id.wishedColor);
			wishedColor.setText(result.toString());
			
			Log.d(TAG, "Got " + result.toString());
		}
	}

}
