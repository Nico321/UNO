package com.android.uno;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateServer extends Activity implements OnClickListener{

private Button createbtn;
private static final String NAMESPACE = "http://usermanagement.uno.de/";
private static final String URL = "http://10.0.2.2:8080/Management/UserManagement";	 
private static final String METHOD_NAME = "AddUser";
private String CLASSNAME = this.getClass().getSimpleName();
private String response;
	

	private String getResponse(){
		return response;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createserver_view);
		
		createbtn = (Button) findViewById(R.id.createServerCreatebtn);
		createbtn.setOnClickListener(this);
		System.out.println(CLASSNAME);
		

		
		createbtn.setEnabled(false);
		AsynchronTask runner = new AsynchronTask();
		runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
		final ProgressDialog progDailog = new ProgressDialog(CreateServer.this);
        progDailog.setMessage("registriere...Bitte warten");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();
		//	response = runner.execute(username, password);
		//	System.out.println(response);
		 //TODO
		
		new Thread(new Runnable() {
			int timer = 0;
	        public void run() {
	        	while(timer < 20){
					 if(!getResponse().isEmpty()){ 
						 progDailog.cancel(); 
						 Intent intent = new Intent(CreateServer.this, Login.class);
						 startActivity(intent);
						 break;
					 }
					 else{
						 timer++;
						 try {
							Thread.currentThread().wait(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					 }
				
				 }
	        return;		        
	        }
	    }).start();
		 
		
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.createServerCreatebtn){
			finish();
		}
	}
	
	
}
