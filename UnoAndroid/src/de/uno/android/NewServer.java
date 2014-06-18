package de.uno.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import de.highscore.HighScore;
import de.highscore.HighScoreService;
import de.uno.android.usermanagement.User;
import de.uno.gameconnection.GameConnectionManager;
import de.uno.gameconnection.GameConnectionManagerService;

public class NewServer extends Activity implements OnClickListener{

	private static final String TAG = NewServer.class.getName();
	private static final String NAMESPACE = "http://gameconnection.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/UnoGame/GameConnectionManager";
	private static final String METHOD_NAME = "createNewGame";
	private static GameConnectionManager uno;
	private static HighScore highscore;
	private ProgressDialog progDailog;
	private Button startbtn;
	private ToggleButton togglebtn;
	private Toast toast = null;
	private User activeUser = null;
	private GameConnectionManager gcm = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newserver_view);
		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
		activeUser = ((AppVariables) this.getApplication()).getUser();
		
		startbtn = (Button) findViewById(R.id.newServerStartGamebtn);
		startbtn.setOnClickListener(this);
		togglebtn = (ToggleButton) findViewById(R.id.newServerRdybtn);
		togglebtn.setOnClickListener(this);
	
		
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.newServerStartGamebtn){
			
			progDailog = new ProgressDialog(NewServer.this);
	        progDailog.setMessage("Spiel wird erstellt...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this, activeUser.getUsername());
			
			
			
		}
		if(v.getId() == R.id.newServerRdybtn){
			ToggleButton tb = (ToggleButton) findViewById (R.id.newServerRdybtn);
			if(tb.isChecked()){
				this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						toast.setText("Bereit!");
						toast.show();
					}
					});
			}
			else{
				this.runOnUiThread(new Runnable() {
					@Override
					public void run() {						
						toast.setText("Nicht Bereit!");
						toast.show();
					}
					});
			}
		}
		
	}

	public void createNewGameCompleted(Boolean success){
		Log.d(TAG, "NewServer Rückruf - Angekommen");
		if (success){
			Log.d(TAG, "NewServer success");
			progDailog.cancel();
			//startbtn.setEnabled(false);
			
			toast.setText("Spiel erstellt!");
			toast.show();
		}
		else{
			Log.d(TAG, "NewServer failed");
			progDailog.cancel();
			toast.setText("Spiel erstellen fehlgeschlagen!");
			toast.show();
		}
	}
	
}
