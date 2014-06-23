package de.uno.android.tasks;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import android.util.Log;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.android.views.PlayerCardView;
import de.uno.player.Player;

public class GetGameStatusTask extends GetDataFromServerTask<Player, Void, HashMap<String, Integer>> {
	private HashMap<String, Integer> gameStatus;
	public GetGameStatusTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HashMap<String, Integer> doInBackground(Player... arg0) {
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			gameStatus = (HashMap<String, Integer>) objectSerializer.deserialize(gameApp.getGameStub().getGameStatus(playerString));
		} catch (Exception e) {
			
		}
		return gameStatus;
	}
	
	@Override
	protected void onPostExecute(HashMap<String, Integer> result) {
		super.onPostExecute(result);
		if(result != null){
			Log.d(TAG, "Got "+ result.toString() + " from getPlayerStatus");
			//Lokaler Spieler braucht keine Übersicht über Anzahl der Karten
			result.remove(gameApp.getLocalPlayer().toString());
			if(gameApp.getGameStatus().size() == 0){
				//setzen der Playerpostionen bei Spielbeginn
				gameApp.setPlayerPositions(result);
			}
			//setzen des lokalen gamestatus
			gameApp.setGameStatus(result);
			//Zeichnen der Karten der anderen Mitspieler
			for (String player: gameApp.getGameStatus().keySet()) {
				PlayerCardView playerCardView = (PlayerCardView) gameActivity.findViewById(gameApp.getPlayerView(player));
				playerCardView.setAmountCards(gameApp.getGameStatus().get(player));
			}
		}
	}
	
	
}
