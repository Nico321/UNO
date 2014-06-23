package de.uno.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NewGameHost extends Activity implements OnClickListener{

	private static final String TAG = NewGameHost.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "/Management/Lobby";
	private static final String METHOD_NAME = "createNewGame";

	private ProgressDialog progDailog;
	private Button startbtn;
	private ToggleButton togglebtn;
	private Toast toast = null;
	private String activeUsername = null;
	ArrayList<String> userNames = null;
	private ListAdapter adapter = null;
	
	
	//public void onFinish() {
	//	AsynchronTask runner = new AsynchronTask();
	//	runner.setKsoapAttributes(NAMESPACE, URL, "deleteLobbyGame");
	//	runner.execute(this, activeUsername);	
	//}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newgamehost_view);
		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
		activeUsername = ((AppVariables) this.getApplication()).getUsername();
		
		startbtn = (Button) findViewById(R.id.newServerHostStartGamebtn);
		startbtn.setOnClickListener(this);
		togglebtn = (ToggleButton) findViewById(R.id.newServerRdybtn);
		togglebtn.setOnClickListener(this);

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.newgamehostmenu, menu);	
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.NewGameHostAction_refresh) {
			progDailog = new ProgressDialog(NewGameHost.this);
	        progDailog.setMessage("Spieler werden aktualisiert...Bitte warten");
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
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.newServerHostStartGamebtn){
			
			progDailog = new ProgressDialog(NewGameHost.this);
	        progDailog.setMessage("Spiel wird erstellt...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, "startGame");
			runner.execute(this, activeUsername);
			
			
			
		}
		
	}
	
	public void participatingPlayersCompleted(boolean success, ArrayList<String> participatingPlayers) {
		if (success){
			userNames = participatingPlayers;
			Log.d(TAG, "showPlayer in Games - success");			
			
			for(String s : participatingPlayers){
				Log.d("ArrayList-participatingPlayers-Ausgabe", s);
			}
			
			if(participatingPlayers.isEmpty()){
				Log.d(TAG, "keine offnen Spiele!");
			}
			for(int i=0;i<participatingPlayers.size();i++){
				if(participatingPlayers.get(i).equals(userNames.get(i))){
					userNames.set(i, participatingPlayers.get(i) + " (Du)");
				}
			}
			
			Log.d(TAG, "Verarbeitung auf UI-Tread beginnt...");
			runOnUiThread(new Runnable() {
			    public void run(){			    	
			    	adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, userNames);		    
			    	ListView lv = (ListView)findViewById(R.id.newServerHostListView);
			    	lv.setAdapter(adapter);;			    
			    }		
			});
			
		progDailog.cancel();
		Log.d("GameLoaded", "Success");		
		}
		else{
			progDailog.cancel();
		}

	}
	

	public void createNewGameCompleted(Boolean success){
		Log.d(TAG, "NewServer Rückruf - Angekommen");
		if (success){
			Log.d(TAG, "NewServer success");
			progDailog.cancel();
			//startbtn.setEnabled(false);
			
			toast.setText("Partie gestartet!");
			toast.show();
		}
		else{
			Log.d(TAG, "NewServer failed");
			progDailog.cancel();
			toast.setText("Spiel starten fehlgeschlagen!");
			toast.show();
		}
	}
	
}
