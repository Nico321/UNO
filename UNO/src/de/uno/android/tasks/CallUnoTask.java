package de.uno.android.tasks;

import android.util.Log;
import android.widget.Toast;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;

public class CallUnoTask extends GetDataFromServerTask<Void, Void, Boolean> {

	public CallUnoTask(GameActivity gameActivity) {
		super(gameActivity);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		boolean result= false;
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			result = gameApp.getGameStub().callUno(playerString);
			Log.w(TAG, String.valueOf(result));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result){
			super.onPostExecute(result);
			gameApp.getLocalPlayer().callUno(true);
			Toast.makeText(gameActivity, "UNO!", Toast.LENGTH_SHORT).show();
		}
		
	}
	

}
