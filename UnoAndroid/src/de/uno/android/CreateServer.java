package de.uno.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import de.uno.android.usermanagement.User;

public class CreateServer extends Activity implements OnClickListener{


private static final String NAMESPACE = "http://lobbymanagement.uno.de/";
private static final String URL = "http://192.168.2.104:8080/Management/Lobby";	 
private static final String METHOD_NAME = "createNewGame";
private static final String TAG = CreateServer.class.getName();
private String CLASSNAME = this.getClass().getSimpleName();
private ProgressDialog progDailog = null;
private Button createbtn;
private User activeUser = null;

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createserver_view);
		
		activeUser = ((AppVariables) this.getApplication()).getUser();
		
		createbtn = (Button) findViewById(R.id.createServerCreatebtn);
		createbtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.createServerCreatebtn){
			createbtn.setEnabled(false);
			progDailog = new ProgressDialog(CreateServer.this);
			progDailog.setMessage("Server wird gestartet...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
	        AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			Log.d("AvtiveUser_Username", activeUser.getUsername());
			ToggleButton tb = (ToggleButton) findViewById (R.id.publicSwitch);
			runner.execute(this, activeUser.getUsername(), tb.isChecked());
		}
	}
	
	public void createServerCompleted(boolean success){
		Log.d(TAG, "CreateServer Rückruf angekommen");
		if (success){
			Log.d(TAG, "createServer_success");
			progDailog.cancel();
			Intent intent = new Intent(CreateServer.this, NewServer.class);
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
