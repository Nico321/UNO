package de.uno.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.uno.android.lobbymanagement.LobbyGame;
import de.uno.android.usermanagement.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class JoinGame extends Activity implements OnClickListener{

	private static final String TAG = JoinGame.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "http://192.168.1.110:8080/Management/Lobby";	 
	private static final String METHOD_NAME = "showOpenGames";
	private Button prevbtn;
	private ProgressDialog progDailog;
	private List<String> valueList;
	private HashMap<User, LobbyGame> possibleGames = null;
	private ListAdapter adapter = null;
	private MenuItem refresh = null;
	private String activeUsername = null;
	private ListView lv = null;
	private String selectedGame = null;
	private String gameUsername = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joingame_view);
		
		activeUsername = ((AppVariables) this.getApplication()).getUsername();
		
		prevbtn = (Button) findViewById(R.id.JoinServerjoinbtn);
		prevbtn.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.joinServerListView);
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
			String item = (String) lv.getItemAtPosition(position);
			Log.d(TAG, "Selected Game => " + item);
			selectedGame = item;
			}
		});
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.joingamemenu, menu);	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.joinGameAction_refresh) {
			refresh = item;
			progDailog = new ProgressDialog(JoinGame.this);
	        progDailog.setMessage("Spielsuche...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this);			
			}
		return true;
	}

	
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.JoinServerjoinbtn){
			
			gameUsername = selectedGame.replace("s Spiel", "");
			
			Log.d(TAG, "Join Game of " + gameUsername + "_Aufruf");
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, "joinLobbyGame");
			runner.execute(this,activeUsername, gameUsername);
			
		}
	}
	
	
	public void showOpenGamesCompleted(boolean success, HashMap<User, LobbyGame> pGames){
		if (success){
			possibleGames = pGames;
			Log.d(TAG, "showOpenGames_success");			
			
			valueList = new ArrayList<String>();
			Log.d(TAG, "valueList erzeugt!");
			if(pGames == null){
				Log.d(TAG, "keine offnen Spiele!");
				valueList.add("TestUsers Spiel");
			}

			if(!(pGames == null)){
				Log.d(TAG, "Offene Spiele!");
				for (HashMap.Entry<User, LobbyGame> entry : pGames.entrySet()) {
					User user = entry.getKey();			  
					Log.d(TAG, user.getUsername()+ " zur valuelist hingegefügt!");
					valueList.add(user.getUsername() + "s Spiel");
					}	
			}
			
			Log.d(TAG, "Verarbeitung auf UI-Tread beginnt...");
			runOnUiThread(new Runnable() {
			    public void run(){			    	
			    	Log.d(TAG, "adapter füllen...");
			    	adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, valueList);		    
			    	Log.d(TAG, "adapter gefüllt");			    	
			    	Log.d(TAG, "ListView füllen...");
			    	ListView lv = (ListView)findViewById(R.id.joinServerListView);
			    	Log.d(TAG, "ListView gefüllt");
			    	lv.setAdapter(adapter);;
			    	Log.d(TAG, "ListView angezeigt");			    
			    }		
			});
			
		progDailog.cancel();
		Log.d("GameLoaded", "Success");
		
		
		
		//alertDialogBuilder
		//.setMessage("Registrierung erfolgreich!")
		//.setCancelable(true);
		}
		else{
			progDailog.cancel();
			Log.d("ServerLoaded", "failed");
		
		//	alertDialogBuilder
		//	.setMessage("Registrierung fehlgeschlagen!")
		//	.setCancelable(true);
		}
	}
	
	public void joinLobbyGameCompleted(Boolean success){
		if (success){
			LobbyGame g = possibleGames.get(gameUsername);
			
			HashMap<Integer,User> playerInLobby = g.getPlayer();
			ArrayList<String> playerNames = new ArrayList<String>();
			for(User player : playerInLobby.values()) {
				playerNames.add(player.getUsername());
				Log.d("JoinGame_PlayerNames:", player.getUsername());
			    
			}
			Intent intent = new Intent(JoinGame.this, NewGamePlayer.class);
			intent.putStringArrayListExtra("playerNames", playerNames);
			intent.putExtra("gameOwner", gameUsername);
			startActivity(intent);
		}
		else{
			
		}
	}
	
}
