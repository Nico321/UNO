package de.uno.android.tasks;

import android.os.AsyncTask;
import android.util.Log;
import de.uno.android.GameActivity;
import de.uno.android.GameApplication;

public abstract class GetDataFromServerTask<Params, Progress, Result>  extends AsyncTask<Params, Progress, Result> {
	protected static String TAG ;
	protected static final GameApplication gameApp = GameApplication.getInstance();
	protected static GameActivity gameActivity;
	
	public GetDataFromServerTask(GameActivity gameActivity){
		this.TAG = this.getClass().toString();
		this.gameActivity = gameActivity;
	}

	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.d(TAG, "Starting" + this.getClass().toString());
	}

}
