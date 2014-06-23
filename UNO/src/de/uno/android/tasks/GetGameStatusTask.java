package de.uno.android.tasks;

import java.util.HashMap;

import android.util.Log;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.GameApplication.PlayerPositions;
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
			Log.d(TAG, e.getMessage().toString());
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
				PlayerCardView playerCardView = (PlayerCardView) gameActivity.findViewById(getPlayerView(player));
				playerCardView.setAmountCards(gameApp.getGameStatus().get(player));
			}
		}
	}
	
	
	private int getPlayerView(String playerName){
		if(gameApp.getPlayerPosition(playerName).equals(PlayerPositions.LEFT.toString())){
			return R.id.left;
		}
		if(gameApp.getPlayerPosition(playerName).equals(PlayerPositions.TOP.toString())){
			return R.id.top;
		}
		if(gameApp.getPlayerPosition(playerName).equals(PlayerPositions.RIGHT.toString())){
			return R.id.right;
		}
		return 0;
	}
	
}
