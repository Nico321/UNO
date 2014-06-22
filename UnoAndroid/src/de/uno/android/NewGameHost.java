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
import de.uno.android.usermanagement.User;

public class NewGameHost extends Activity implements OnClickListener{

	private static final String TAG = NewGameHost.class.getName();
	private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
	private static final String URL = "http://192.168.1.110:8080/Management/Lobby";
	private static final String METHOD_NAME = "createNewGame";

	private ProgressDialog progDailog;
	private Button startbtn;
	private ToggleButton togglebtn;
	private Toast toast = null;
	private String activeUsername = null;
	ArrayList<String> userNames = null;
	private ListAdapter adapter = null;
	
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
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this, activeUsername);
			
			
			
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
			    	Log.d(TAG, "adapter füllen...");
			    	adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, userNames);		    
			    	Log.d(TAG, "adapter gefüllt");			    	
			    	Log.d(TAG, "ListView füllen...");
			    	ListView lv = (ListView)findViewById(R.id.newServerHostListView);
			    	Log.d(TAG, "ListView gefüllt");
			    	lv.setAdapter(adapter);;
			    	Log.d(TAG, "ListView angezeigt");			    
			    }		
			});
			
		progDailog.cancel();
		Log.d("GameLoaded", "Success");
		
		}
		else{
			progDailog.cancel();
			/*runOnUiThread(new Runnable() {
			    public void run(){
			    	toast.setText("Fehler bei Aktualisierung!");
			    	toast.show();
			    }
			});
			*/
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
