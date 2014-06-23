package de.uno.android.tasks;

import java.util.LinkedList;

import android.widget.LinearLayout;
import android.widget.Toast;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.android.views.PlayerCardView;
import de.uno.player.Player;

public class GetCurrentPlayerTask extends GetDataFromServerTask<Void, Void, Player> {
	private Player tmpPlayer;
	private LinkedList<String> disconnectedPlayers;
	public GetCurrentPlayerTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@SuppressWarnings("unchecked")
	@Override
	protected Player doInBackground(Void... arg0) {
		Player result = null;
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			tmpPlayer = gameApp.getActualPlayer();
			result = (Player) objectSerializer.deserialize(gameApp.getGameStub().getCurrentPlayer(playerString));
			disconnectedPlayers = (LinkedList<String>)objectSerializer.deserialize(gameApp.getGameStub().getDisconnectedPlayers(playerString));
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Player result) {
		super.onPostExecute(result);
		if(result!= null){
			gameApp.setActualPlayer(result);
			//Abfrage ob der lokale Spieler am Zug ist
			if(result.equals(gameApp.getLocalPlayer())){
				gameActivity.findViewById(R.id.cardStackButton).setClickable(true);
				LinearLayout llPlayerOne =  (LinearLayout) gameActivity.findViewById(R.id.layoutPlayerOne);
				llPlayerOne.setBackgroundColor(gameActivity.getResources().getColor(R.color.lightgreen));
				
				for (String s : gameApp.getGameStatus().keySet()) {
					PlayerCardView playerLayout = (PlayerCardView) gameActivity.findViewById(gameApp.getPlayerView(s));
					playerLayout.setBackgroundColor(gameActivity.getResources().getColor(android.R.color.transparent));	
				}
			//Abfrage ob ein Spielerwechsel stattgefunden hat
			}else if(!gameApp.getActualPlayer().equals(tmpPlayer)){
				gameActivity.findViewById(R.id.cardStackButton).setClickable(false);
				LinearLayout llPlayerOne =  (LinearLayout) gameActivity.findViewById(R.id.layoutPlayerOne);
				llPlayerOne.setBackgroundColor(gameActivity.getResources().getColor(android.R.color.transparent));
				
				//Der Hintergrund der View des aktuellen Spielers wird grün hinterlegt
				for (String s : gameApp.getGameStatus().keySet()) {
					PlayerCardView playerLayout = (PlayerCardView) gameActivity.findViewById(gameApp.getPlayerView(s));
					if(gameApp.getActualPlayer().getUsername().equals(s)){
						playerLayout.setBackgroundColor(gameActivity.getResources().getColor(R.color.lightgreen));
					}else{
						playerLayout.setBackgroundColor(gameActivity.getResources().getColor(android.R.color.transparent));
					}
					
				}	
			}
			
			//Überprüfung ob ein Spieler disconnected wurde(Timeout)
			if(disconnectedPlayers != null){
				if(disconnectedPlayers.size()!=0){
					for (String disconPlayer : disconnectedPlayers) {
						PlayerCardView playerLayout = (PlayerCardView) gameActivity.findViewById(gameApp.getPlayerView(disconPlayer));
						playerLayout.setBackgroundColor(gameActivity.getResources().getColor(R.color.lightred));
					}
				}
			}
		}		
	}		
}


