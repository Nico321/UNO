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
import de.uno.android.usermanagement.User;

public class NewGameHost extends Activity implements OnClickListener{

	private static final String TAG = NewGameHost.class.getName();
	private static final String NAMESPACE = "http://gameconnection.uno.de/";
	private static final String URL = "http://192.168.1.110:8080/UnoGame/GameConnectionManager";
	private static final String METHOD_NAME = "createNewGame";

	private ProgressDialog progDailog;
	private Button startbtn;
	private ToggleButton togglebtn;
	private Toast toast = null;
	private String activeUsername = null;
	
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
