package de.uno.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NewGamePlayer extends Activity implements OnClickListener{

	private static final String TAG = NewGamePlayer.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "/Management/Lobby";	 
	private static final String METHOD_NAME = "showParticipatingPlayer";
	private ArrayList<String> pNames = new ArrayList<String>();
	private ProgressDialog progDailog = null;
	ArrayList<String> userNames = null;
	private ListAdapter adapter = null;
	private String gameOwner = null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newgameplayer_view);
		gameOwner = getIntent().getStringExtra("gOwner");
		Log.d("Spielbesitzer:",gameOwner);
		refreshPlayer();
		
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.NewGamePlayerAction_refresh) {
	        refreshPlayer();
			
					
			}
		
		
		return true;
	}
	
	
	private void refreshPlayer(){	
		progDailog = new ProgressDialog(NewGamePlayer.this);
        progDailog.setMessage("Mitspieler werden aktualisiert...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
		AsynchronTask runner = new AsynchronTask();
		runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
		runner.execute(this, gameOwner);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.newgameplayermenu, menu);	
		return true;
	}
	
	@Override
	public void onClick(View v) {
		
		
	}
	
	public void participatingPlayersCompleted(boolean success, ArrayList<String> participatingPlayers) {
		if (success){
			userNames = participatingPlayers;
			Log.d(TAG, "showOpenGames_success");			
			
			if(participatingPlayers.isEmpty()){
				Log.d(TAG, "keine offnen Spiele!");
			}

			if(!(participatingPlayers.isEmpty())){
				Log.d(TAG, "Offene Spiele!");
				
			}		
			
			Log.d(TAG, "Verarbeitung auf UI-Tread beginnt...");
			runOnUiThread(new Runnable() {
			    public void run(){			    	
			    	adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, userNames);		    
			    	ListView lv = (ListView)findViewById(R.id.newGamePlayerListView);
			    	lv.setAdapter(adapter);;		    
			    }		
			});
			
		progDailog.cancel();
		Log.d("GameLoaded", "Success");
		
		}
		else{
			progDailog.cancel();
			Log.d("GameLoaded", "Failed");
			/*runOnUiThread(new Runnable() {
			    public void run(){
			    	toast.setText("Fehler bei Aktualisierung!");
			    	toast.show();
			    }
			});
			*/
		}

	}

}
