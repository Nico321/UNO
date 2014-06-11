package de.uno.android.tasks;

import java.util.HashMap;

import android.util.Log;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.player.Player;

public class GetPlayerStatusTask extends GetDataFromServerTask<Player, Void, HashMap<String, Integer>> {
	private HashMap<String, Integer> gameStatus;
	public GetPlayerStatusTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected HashMap<String, Integer> doInBackground(Player... arg0) {
		try {
			String playerString = objectSerializer.serialize(arg0[0]);
			gameStatus = (HashMap<String, Integer>) objectSerializer.deserialize(gameApp.getGameStub().getPlayerStatus(playerString));
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
		}
		return gameStatus;
	}
	
	@Override
	protected void onPostExecute(HashMap<String, Integer> result) {
		super.onPostExecute(result);
		
		if(result != null){
			
			if(!result.equals(gameApp.getGameStatus())){
				gameApp.setGameStatus(result);
				
				for (String player : gameApp.getGameStatus().keySet()) {
					Log.d(TAG,player);
				}
				for (Integer amountOfCards : gameApp.getGameStatus().values()) {
					Log.d(TAG, amountOfCards.toString());
				}
			}
		}
	}
}
