package de.uno.android.tasks;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.player.Player;

public class GetPlayerStatusTask extends GetDataFromServerTask<Player, Void, HashMap<String, Integer>> {
	private HashMap<String, Integer> gameStatus;
	public GetPlayerStatusTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@SuppressWarnings("unchecked")
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
			Log.d(TAG, "Got "+ result.toString() + " from getPlayerStatus");
			//Lokaler Spieler braucht keine Übersicht über anzahl der Karten
			result.remove(gameApp.getLocalPlayer().toString());
			//Prüfe ob gameStatus bereits geholt wurde
			if(gameApp.getGameStatus().size() == 0){
				gameApp.setGameStatus(result);
				this.drawOtherPlayersCards();
			}else{
				//Wenn ja wird geprüft ob änderungen vorlieren
				if(!result.equals(gameApp.getGameStatus())){
					gameApp.setGameStatus(result);
					this.drawOtherPlayersCards();
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
	
	private void drawOtherPlayersCards(){
		Bitmap card = BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.rueckseite);
		for (String player : gameApp.getGameStatus().keySet()) {
			for(int i = 0; i< gameApp.getGameStatus().get(player); i++){
				Canvas canvas = new Canvas();
			}
		}
	}
}
