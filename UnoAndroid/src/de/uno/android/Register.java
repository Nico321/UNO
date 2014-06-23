package de.uno.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.uno.android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{

	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "/Management/UserManagement";	 
	private static final String METHOD_NAME = "AddUser";
	private static final String TAG = Register.class.getName();
	private Button registerBtn;
	private ProgressDialog progDailog;
	private AlertDialog.Builder alertDialogBuilder;
	private Toast toast = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		toast = Toast.makeText(Register.this, "",
				Toast.LENGTH_LONG);
	}
	@Override
    protected void onPause() {
        super.onPause();
        finish();
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
		Log.d(TAG, "Register Rückruf anegkommen");
		if (success){
		Log.d(TAG, "Register success");
		progDailog.cancel();
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toast.setText("Registrierung erfolgreich!");
				toast.show();
			}
			});
		Intent intent = new Intent(Register.this, Login.class);
		startActivity(intent);
		}
		
		else{
			Log.d(TAG, "Register failed");
			progDailog.cancel();
			Register.this.registerBtn.setEnabled(true);
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					toast.setText("Registrierung fehlgeschlagen!");
					toast.show();
				}
				});
		
		}
	}
	public static String toSHA1(byte[] convertme) {
	    MessageDigest md = null;
	    try {
	    	Log.d("decrypt","decrypt");
	        md = MessageDigest.getInstance("SHA");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return new String(md.digest(convertme));
	}
}
