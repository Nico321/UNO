package com.android.uno;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.uno.usermanagement.*;

public class Register extends Activity implements OnClickListener{

	private Button registerBtn;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		EditText etMail = (EditText)findViewById(R.id.registerUsernameEdittext);
		String email = etMail.getText().toString();
		EditText etPassword = (EditText)findViewById(R.id.registerUserPassEdittext);
		String password = etPassword.getText().toString();
		EditText etPasswordC = (EditText)findViewById(R.id.registerUserPassConfirmEdittext);
		String passwordC = etPasswordC.getText().toString();
		
		if(etPassword != etPasswordC | etPassword.length() < 6){
			System.out.println("Passwort zu kurz oder nicht übereinstimmend!");
		}
		
		else{
		 UserManagement uman = new UserManagement();
		 uman.AddUser(email, password);
		}
	}
	
}
