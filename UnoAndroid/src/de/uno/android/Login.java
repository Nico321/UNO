package de.uno.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener{

	private Button loginBtn;
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://10.0.2.2:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "Login";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.loginBtn){
			EditText etUsername = (EditText)findViewById(R.id.loginUsernameEdittext);
			String username = etUsername.getText().toString();
			EditText etPass = (EditText)findViewById(R.id.loginpassEdittext);
			String pass = etPass.getText().toString();
			AsynchronTask runner = new AsynchronTask();
			
			
			if(pass.length()>=6) {
				runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
				ProgressDialog progDailog = new ProgressDialog(Login.this);
		        progDailog.setMessage("einloggen...Bitte warten");
		        progDailog.setIndeterminate(false);
		        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        progDailog.setCancelable(true);
		        //progDailog.show();
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
	public void loginCompleted(){
		//progDailog.cancel();
		Intent intent = new Intent(Login.this, MainMenu.class);
		startActivity(intent);
	}

	
	
}
