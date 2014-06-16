package de.uno.android;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.uno.android.lobbymanagement.LobbyGame;
import de.uno.android.usermanagement.User;

public class CreateServer extends Activity implements OnClickListener{

private Button createbtn;
private static final String NAMESPACE = "http://usermanagement.uno.de/";
private static final String URL = "http://192.168.2.104:8080/Management/UserManagement";	 
private static final String METHOD_NAME = "createNewGame";
private static final String TAG = CreateServer.class.getName();
private String CLASSNAME = this.getClass().getSimpleName();
private ProgressDialog progDailog = null;

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createserver_view);
		
		createbtn = (Button) findViewById(R.id.createServerCreatebtn);
		createbtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.createServerCreatebtn){
			createbtn.setEnabled(false);
			final ProgressDialog progDailog = new ProgressDialog(CreateServer.this);
			progDailog.setMessage("Server wird gestartet...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(false);
	        progDailog.show();
	        AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			 
			runner.execute(this);
		}
	}
	
	public void createServerCompleted(boolean success){
		progDailog = new ProgressDialog(CreateServer.this);
		if (success){
			Log.d(TAG, "createServer_success");
			progDailog.cancel();
			}
			
		
		
		else{
			Log.d(TAG, "createServer_failed");
			progDailog.cancel();
		//	alertDialogBuilder
		//	.setMessage("Registrierung fehlgeschlagen!")
		//	.setCancelable(true);
		}
	}
	
	
}
