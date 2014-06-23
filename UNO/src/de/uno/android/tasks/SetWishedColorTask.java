package de.uno.android.tasks;

import android.widget.TextView;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.card.CardColor;

public class SetWishedColorTask extends GetDataFromServerTask<Object, Void, Void> {

	public SetWishedColorTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Void doInBackground(Object... arg0) {
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			String wishedColor = objectSerializer.serialize(gameApp.getWishedColor());
			gameApp.getGameStub().setWishedColor(playerString, wishedColor);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		TextView wishedColor = (TextView) gameActivity.findViewById(R.id.wishedColor);
		wishedColor.setText(gameApp.getWishedColor().toString());
	}


}
