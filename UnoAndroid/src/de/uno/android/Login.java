package de.uno.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{

	private Button loginBtn;
	private ProgressDialog progDailog;
	private AlertDialog.Builder alertDialogBuilder;
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "Login";
	private static final String TAG = Login.class.getName();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		
		
	}
	 @Override
	    protected void onPause() {
	        super.onPause();
	        finish();
	 	}
	
	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.loginBtn){
			EditText etUsername = (EditText)findViewById(R.id.loginUsernameEdittext);
			String username = etUsername.getText().toString();
			EditText etPass = (EditText)findViewById(R.id.loginpassEdittext);
			String pass = etPass.getText().toString();
			AsynchronTask runner = new AsynchronTask();
			
			if(pass.length()<6 & username.isEmpty()){
				alertDialogBuilder = new AlertDialog.Builder(this);
				
			 	alertDialogBuilder.setTitle("Registrierung");	 
				alertDialogBuilder
					.setMessage("Username oder längeres Passwort eingeben")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
							dialog.cancel();
						}
					  });
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
			}
			
			if(pass.length()>=6 & !username.isEmpty()) {
				runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
				progDailog = new ProgressDialog(Login.this);
		        progDailog.setMessage("einloggen...Bitte warten");
		        progDailog.setIndeterminate(false);
		        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        progDailog.setCancelable(true);
		        progDailog.show();
				runner.execute(this,username, pass);
			;
			}
			
		}
		
		if(v.getId() == R.id.loginRegisterTextView){
			Intent intent = new Intent(Login.this, Register.class);
			startActivity(intent);
		}
		if(v.getId() == R.id.skipTextView){
			Intent intent = new Intent(Login.this, MainMenu.class);
			startActivity(intent);
		}
		
	}
	public void loginCompleted(Boolean success){
		Log.d(TAG, "Login Rückruf angekommen");
		if(success){
			Log.d(TAG, "Login success");
			progDailog.cancel();
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Login.this, "Login erfolgreich!",
							Toast.LENGTH_LONG).show();
				}
				});
			Intent intent = new Intent(Login.this, MainMenu.class);
			startActivity(intent);
		}
		else{
			Log.d(TAG, "Login failed");
			progDailog.cancel();
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Login.this, "Login fehlgeschlagen!",
							Toast.LENGTH_LONG).show();
				}
				});
		}
	}

	
	
}
