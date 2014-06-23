package de.uno.android.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import de.uno.android.GameActivity;
import de.uno.android.GameApplication;
import de.uno.android.util.ConnectionDetector;
/**
 * 
 * @author Dave Kaufmann
 *
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 * 
 * OberAsynctask Klasse von der alle Asynctasks erben
 */
public abstract class GetDataFromServerTask<Params, Progress, Result>  extends AsyncTask<Params, Progress, Result> {
	protected static String TAG ;
	protected static final GameApplication gameApp = GameApplication.getInstance();
	protected static GameActivity gameActivity;
	private ConnectionDetector connectionManager;
	
	public GetDataFromServerTask(GameActivity gameActivity){
		this.TAG = this.getClass().toString();
		this.gameActivity = gameActivity;
		this.connectionManager = new ConnectionDetector(this.gameApp);
	}

	
	@Override
	protected void onPreExecute() {
		Log.d(TAG, "Starting" + this.getClass().toString());
		if(!connectionManager.isConnectingToInternet()){
			showAlertDialog(gameActivity, "Internet Connection",
                    "You have internet connection", true);
			this.cancel(true);
		}else{
			
		}
		
	}
	
	 private void showAlertDialog(Context context, String title, String message, Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	 
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(message);

	        // Setting OK Button
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }

}
