package com.android.uno;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{
	
	private Button createServer;
	private Button joinServer;
	private Button settings;
	private Button quit;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		createServer = (Button) findViewById(R.id.createServerbtn);
		createServer.setOnClickListener(this);
		
		joinServer = (Button) findViewById(R.id.joinServerbtn);
		joinServer.setOnClickListener(this);
		
		settings = (Button) findViewById(R.id.settingsbtn);
		settings.setOnClickListener(this);
		
		quit = (Button) findViewById(R.id.quitbtn);
		quit.setOnClickListener(this);
		System.out.println("blaaa");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.settings) {
			Intent intent = new Intent(MainMenu.this, Settings.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		int ce = v.getId();
		
		
		if(ce == R.id.createServerbtn){
			Intent intent = new Intent(MainMenu.this, CreateServer.class);
			startActivity(intent);
		}
		if(ce == R.id.joinServerbtn){
			Intent intent = new Intent(MainMenu.this, JoinServer.class);
			startActivity(intent);
		}		
		if(ce == R.id.settingsbtn){
			Intent intent = new Intent(MainMenu.this, Settings.class);
			startActivity(intent);
		}		
		if(ce == R.id.quitbtn){
			finish();
			System.exit(0);
			
		}
	}

}
