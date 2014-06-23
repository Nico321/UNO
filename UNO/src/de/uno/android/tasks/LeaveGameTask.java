package de.uno.android.tasks;

import android.widget.Toast;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;

public class LeaveGameTask extends GetDataFromServerTask<Void, Void, Boolean> {

	public LeaveGameTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		boolean result =false;
			try {
				String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
				result = gameApp.getGameStub().leaveGame(playerString);
				return result;
			} catch (Exception e) {
				// TODO: handle exception
			}
		return null;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			Toast.makeText(gameActivity, "Left game!", Toast.LENGTH_SHORT).show();
		}
	}

}
