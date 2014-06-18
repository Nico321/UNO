package de.uno.android;

import de.uno.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener{

	private Button registerBtn;
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "AddUser";
	private ProgressDialog progDailog;
	private AlertDialog.Builder alertDialogBuilder;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		
		EditText etusername = (EditText)findViewById(R.id.registerUsernameEdittext);
		String username = etusername.getText().toString();
		EditText etPassword = (EditText)findViewById(R.id.registerUserPassEdittext);
		String password = etPassword.getText().toString();
		EditText etPasswordC = (EditText)findViewById(R.id.registerUserPassConfirmEdittext);
		String passwordC = etPasswordC.getText().toString();
		
		
		if(!password.equals(passwordC) | password.length() < 6 | username.isEmpty()){	 
			 alertDialogBuilder = new AlertDialog.Builder(this);
	
			 	alertDialogBuilder.setTitle("Registrierung");	 
				alertDialogBuilder
					.setMessage("Username zu kurz, Passwort zu kurz oder nicht übereinstimmend!")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
							dialog.cancel();
						}
					  });
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
		
		else{
			
			registerBtn.setEnabled(false);
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			progDailog = new ProgressDialog(Register.this);
	        progDailog.setMessage("registriere...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(false);
	        progDailog.show();
				runner.execute(this,username, password);
				
			
			
			
			
			}
		}
	
	public void registartionCompleted(boolean success){
		if (success){			
		progDailog.cancel();
		Intent intent = new Intent(Register.this, Login.class);
		startActivity(intent);
		//alertDialogBuilder
		//.setMessage("Registrierung erfolgreich!")
		//.setCancelable(true);
		}
		else{
			progDailog.cancel();
			
			alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setTitle("Registrierung");	 
			alertDialogBuilder
				.setMessage("Registrierung fehlgeschlagen")
				.setCancelable(true)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						
						dialog.cancel();
					}
				  });
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
		
		}
	}
}
