package de.uno.android.tasks;

import android.widget.Toast;
import de.android.uno.R;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;
import de.uno.player.Player;

public class GetCurrentPlayerTask extends GetDataFromServerTask<Void, Void, Player> {
	private Player tmpPlayer;
	public GetCurrentPlayerTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected Player doInBackground(Void... arg0) {
		Player result = null;
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			tmpPlayer = gameApp.getActualPlayer();
			result = (Player) objectSerializer.deserialize(gameApp.getGameStub().getCurrentPlayer(playerString));
		
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
			if(result.equals(gameApp.getLocalPlayer())){
				//TODO eigene Karten "unclickbar" machen
				gameActivity.findViewById(R.id.cardStackButton).setClickable(true);
				Toast.makeText(gameActivity, "Du bist dran!", Toast.LENGTH_SHORT).show();
				
			}else if(!gameApp.getActualPlayer().equals(tmpPlayer)){
				////TODO eigene Karten "clickbar" machen
				gameActivity.findViewById(R.id.cardStackButton).setClickable(false);
			}
		}
				
		}
			
	}


