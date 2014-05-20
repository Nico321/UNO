package com.android.uno;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateServer extends Activity implements OnClickListener{

private Button connectbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createserver_view);
		
		connectbtn = (Button) findViewById(R.id.createServerConnectbtn);
		connectbtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.createServerConnectbtn){
			finish();
		}
	}
	
	
}
