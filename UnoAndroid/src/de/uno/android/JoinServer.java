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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class JoinServer extends Activity implements OnClickListener{

	private static final String TAG = JoinServer.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/Lobby";	 
	private static final String METHOD_NAME = "showOpenGames";
	private Button prevbtn;
	private ProgressDialog progDailog;
	private List<String> valueList;
	HashMap<User, LobbyGame> possibleGames = null;
	ListAdapter adapter = null;
	MenuItem refresh = null;
	private User activeUser = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinserver_view);
		
		activeUser = ((AppVariables) this.getApplication()).getUser();
		
		prevbtn = (Button) findViewById(R.id.JoinServerjoinbtn);
		prevbtn.setOnClickListener(this);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			refresh = item;
			progDailog = new ProgressDialog(JoinServer.this);
	        progDailog.setMessage("Serversuche...Bitte warten");
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
			
			
		}
	}
	
	
	public void showOpenGamesCompleted(boolean success, HashMap<User, LobbyGame> pGames){
		if (success){
			Log.d(TAG, "showOpenGames_success");
			possibleGames = pGames;
			runOnUiThread(new Runnable() {
			    public void run(){
			    	
			valueList = new ArrayList<String>();
			valueList.add("TestUser");
			for (HashMap.Entry<User, LobbyGame> entry : possibleGames.entrySet()) {
				  User user = entry.getKey();
				  LobbyGame lobbyGame = entry.getValue();				  
				  	
					valueList.add(user.getUsername());
					}	
			
					adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, valueList);
			 
			    	if(adapter.isEmpty()){
			    	Log.d(TAG, "ListView leer");
			    	}
			    	Log.d(TAG, "ListView gefüllt");
			    	
					Log.d(TAG, "ListView 1");
					ListView lv = (ListView)findViewById(R.id.joinServerListView);
					Log.d(TAG, "ListView 2");
					lv.setAdapter(adapter);;
					Log.d(TAG, "ListView angezeigt");
			    	
			    }
			});
			
		progDailog.cancel();
		Log.d("ServerLoaded", "Success");
		
		
		
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
	
}
