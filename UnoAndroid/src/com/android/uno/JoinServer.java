package com.android.uno;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JoinServer extends Activity implements OnClickListener{

	private Button prevbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinserver_view);
		
		prevbtn = (Button) findViewById(R.id.JoinServerjoinbtn);
		prevbtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.JoinServerjoinbtn){
			finish();
		}
	}
}
