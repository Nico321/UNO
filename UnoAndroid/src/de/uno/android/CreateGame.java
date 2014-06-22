package de.uno.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateGame extends Activity implements OnClickListener{


private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
private static final String URL = "http://192.168.1.110:8080/Management/Lobby";	 
private static final String METHOD_NAME = "createNewGame";
private static final String TAG = CreateGame.class.getName();
private String CLASSNAME = this.getClass().getSimpleName();
private ProgressDialog progDailog = null;
private Button createbtn;
private String activeUsername = null;

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creategame_view);
		
		activeUsername = ((AppVariables) this.getApplication()).getUsername();
		
		createbtn = (Button) findViewById(R.id.createGameCreatebtn);
		createbtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.createGameCreatebtn){
			createbtn.setEnabled(false);
			progDailog = new ProgressDialog(CreateGame.this);
			progDailog.setMessage("Server wird gestartet...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
	        AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			Log.d("AvtiveUser_Username", activeUsername);
			ToggleButton tb = (ToggleButton) findViewById (R.id.publicSwitch);
			runner.execute(this, activeUsername, tb.isChecked());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.creategamemenu, menu);	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.joinGameAction_refresh) {
			progDailog = new ProgressDialog(CreateGame.this);
	        progDailog.setMessage("Spielsuche...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, "showParticipatingPlayer");
			runner.execute(this, activeUsername);			
			}
		return true;
	}
	
	public void createGameCompleted(boolean success){
		Log.d(TAG, "CreateGame Rückruf angekommen");
		if (success){
			Log.d(TAG, "createGame_success");
			progDailog.cancel();
			Intent intent = new Intent(CreateGame.this, NewGameHost.class);
			startActivity(intent);
			}
			
		
		
		else{
			Log.d(TAG, "createServer_failed");
			progDailog.cancel();
			createbtn.setEnabled(true);
			Toast.makeText(getApplicationContext(), "Fehler beim Servererstellen",
					   Toast.LENGTH_LONG).show();
		
		}
	}
	
	
}
