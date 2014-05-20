package com.android.uno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.uno.usermanagement.*;

public class Login extends Activity implements OnClickListener{

	private Button loginBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO prüfe emial/passwort
		
		if(v.getId() == R.id.loginBtn){
			EditText etUsername = (EditText)findViewById(R.id.loginUsernameEdittext);
			String username = etUsername.getText().toString();
			EditText etPass = (EditText)findViewById(R.id.loginpassEdittext);
			String pass = etPass.getText().toString();
			
			UserDAO udao = new UserDAO();
			if(udao.UserLogin(username,pass) == true) {
				Intent intent = new Intent(Login.this, MainMenu.class);
				startActivity(intent);
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

	
	
}
