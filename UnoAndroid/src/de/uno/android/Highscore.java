package de.uno.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.uno.android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Highscore extends Activity implements OnClickListener{

	private static final String NAMESPACE = "http://highscore.de/";
	private static final String URL = "/HighScore/HighScore";	 
	private static final String METHOD_NAME = "getHighscore";
	private static final String TAG = Highscore.class.getName();
	private String activeUsername = null;
	private ProgressDialog progDailog = null;
	private List<String> valueList;
	private Toast toast = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore_view);
		activeUsername = ((AppVariables) this.getApplication()).getUsername();

		toast = Toast.makeText(Highscore.this, "Testhighscoreliste generiert!",
				Toast.LENGTH_LONG);
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.highscoremenu, menu);	
		return true;
	}
	//HighscoreAction_refresh
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.HighscoreAction_refresh) {
			progDailog = new ProgressDialog(Highscore.this);
	        progDailog.setMessage("Highscoreliste wird aktualisiert...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			Log.d (TAG,activeUsername);
	        AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this,((AppVariables) this.getApplication()).getUsername());			
			}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
	public void HighScoreListCompleted(boolean success, HashMap<String, Integer> highscoreMap){
		Log.d ("HighScoreListCompleted","highscoreList- Antwort angekommen");
		progDailog.cancel();
		Log.d (TAG,"Progdialog canceled!");
		valueList = new ArrayList<String>();
		Log.d (TAG,"valueList created");
		if (highscoreMap.isEmpty()){
			Log.d (TAG,"kein Highscore vorhanden!");
			highscoreMap.put("Rüdiger", 1000);
			highscoreMap.put("Hansi", 900);
			highscoreMap.put("Peter", 800);
			highscoreMap.put("Becci", 700);
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					toast.show();
				}
				});
		}
		
		Log.d (TAG,"highscoreList notEmpty");
		Set<String> s = highscoreMap.keySet();
		Collection<Integer> i = highscoreMap.values();
		 for (Entry<String, Integer> entry  : highscoreMap.entrySet()){ 
			Log.d(TAG,entry.getKey() + " - " + entry.getValue());
			valueList.add(entry.getKey() + ": " + entry.getValue() + " Punkte");
		 }
			
					
		
		Log.d (TAG,"ListView füllen...");
		runOnUiThread(new Runnable() {
				    public void run(){   
				    	ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, valueList);
						final ListView lv = (ListView)findViewById(R.id.highscoreListView);
						lv.setAdapter(adapter);
			    }
			});
		
		Log.d (TAG,"...abgeschlossen");
		
	}	
	
}
