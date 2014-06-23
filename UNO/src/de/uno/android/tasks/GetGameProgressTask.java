package de.uno.android.tasks;

import android.util.Log;
import de.uno.android.GameActivity;
import de.uno.android.util.objectSerializer;

public class GetGameProgressTask extends GetDataFromServerTask<Void, Void, Integer> {

	public GetGameProgressTask(GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Integer doInBackground(Void... params) {
		try {
			String playerString = objectSerializer.serialize(gameApp.getLocalPlayer());
			int result = gameApp.getGameStub().getGameProgress(playerString);
			return result;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage().toString());
		}
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	protected void onPostExecute(Integer result) {
		if(result != null){
			if(gameApp.getGameProgress() != result){
				gameApp.setGameProgress(result);
				if(!gameApp.isGameFinished()){
					if(!gameApp.getLocalPlayer().equals(gameApp.getActualPlayer())){
	            		GetCurrentPlayerTask gnpt = new GetCurrentPlayerTask(gameActivity);
	                     gnpt.execute();
	            	}
					GetGameStatusTask ggst = new GetGameStatusTask(gameActivity);
					ggst.execute();
					GetStackCardTask gsct = new GetStackCardTask(gameActivity);
					gsct.execute();
					IsGameFinishedTask isGameFinished = new IsGameFinishedTask(gameActivity);
					isGameFinished.execute();
					}
				}
		}
		
	};

}
