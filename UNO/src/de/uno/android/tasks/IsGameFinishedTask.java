package de.uno.android.tasks;

import java.util.HashMap;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;

public class IsGameFinishedTask extends GetDataFromServerTask<Void, Void, Boolean> {
	
	private HashMap<String, Integer> ranks;

	public IsGameFinishedTask(GameActivity gameActivity) {
		super(gameActivity);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Boolean doInBackground(Void... arg0) {
		boolean result = false;
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			result = gameApp.getGameStub().isGameFinished(playerString);
			if(result){
				ranks = (HashMap<String, Integer>) objectSerializer.deserialize(gameApp.getGameStub().getWinners(playerString));
				
			}
			return result;
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			gameApp.setGameFinished(true);
			//Dialog 
			AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                    gameActivity);
            builderSingle.setTitle("Spiel beendet!");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(gameActivity,
                    R.layout.simple_list_item_1);
            
            for (String s : ranks.keySet()) {
            	arrayAdapter.add(s + " bekommt " + ranks.get(s).toString() + " Punkte");
			}
            builderSingle.setAdapter(arrayAdapter, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
            
            builderSingle.setPositiveButton("Spiel Verlassen", new OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LeaveGameTask leaveGame = new LeaveGameTask(gameActivity);
					leaveGame.execute();	
				}
			});
            AlertDialog dialog = builderSingle.create();
            dialog.setCancelable(false);
            dialog.show();
		}
		
	}
 
}
