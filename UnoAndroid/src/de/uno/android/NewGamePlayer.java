package de.uno.android;

import java.util.ArrayList;
import java.util.HashMap;

import de.uno.android.lobbymanagement.LobbyGame;
import de.uno.android.usermanagement.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NewGamePlayer extends Activity implements OnClickListener{

	private static final String TAG = NewGamePlayer.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/Lobby";	 
	private static final String METHOD_NAME = "showOpenGames";
	private ArrayList<String> pNames = new ArrayList<String>();
	private ProgressDialog progDailog = null;
	private MenuItem refresh = null;
	private ListAdapter adapter = null;
	private String gameOwner = null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newgameplayer_view);
		pNames = getIntent().getStringArrayListExtra("playerNames");
		gameOwner = getIntent().getStringExtra("gameOwner");
		//refreshPlayer();
		
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.NewGamePlayerAction_refresh) {
			refresh = item;
			progDailog = new ProgressDialog(NewGamePlayer.this);
	        progDailog.setMessage("Mitspieler werden aktualisiert...");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
	        refreshPlayer();
			
					
			}
		
		
		return true;
	}
	
	
	private void refreshPlayer(){		
		AsynchronTask runner = new AsynchronTask();
		runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
		runner.execute(this);	
	}
	
	public void refreshPlayerCompleted(Boolean success, final HashMap<User, LobbyGame> possibleGames){
		runOnUiThread(new Runnable() {
		    public void run(){	
		    	ArrayList<String> playerNames = null;
		    	LobbyGame lg = possibleGames.get(gameOwner);
		    	HashMap<Integer,User> playerInLobby = lg.getPlayer();
		    	for(User player : playerInLobby.values()) {
		    		playerNames.add(player.getUsername());
					Log.d("JoinGame_PlayerNames:", player.getUsername());
				    
				}
		    	adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, playerNames);
		 
		    	if(pNames.isEmpty()){
		    		Log.d(TAG, "keine Spieler vorhanden!");
		    	}
		    	Log.d(TAG, "ListView gefüllt");		    			
		    	ListView lv = (ListView)findViewById(R.id.newGamePlayerListView);
		    	lv.setAdapter(adapter);;
		    	Log.d(TAG, "ListView angezeigt");
		    	
		    }
		});
	}
	
	@Override
	public void onClick(View v) {
		
		
	}

}
