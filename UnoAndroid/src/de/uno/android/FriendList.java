package de.uno.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.uno.android.usermanagement.User;

public class FriendList extends Activity implements OnClickListener{

	
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "ShowFriendList";
	private static final String TAG = FriendList.class.getName();
	private User activeUser = null;
	private Button addFriendbtn;
	private ProgressDialog progDailog = null;
	private List<String> valueList;
	private MenuItem refresh = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist_view);
		
		addFriendbtn = (Button) findViewById(R.id.friendListAddFriendbtn);
		addFriendbtn.setOnClickListener(this);
		activeUser = ((AppVariables) this.getApplication()).getUser();
		Log.d (TAG,"TestFreindlist");
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.friendlistmenu, menu);	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.FriendListAction_refresh) {
			refresh = item;
			progDailog = new ProgressDialog(FriendList.this);
	        progDailog.setMessage("Freunde werden aktualisiert...Bitte warten");
	        progDailog.setIndeterminate(false);
	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDailog.setCancelable(true);
	        progDailog.show();
			
	        AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this,((AppVariables) this.getApplication()).getUser().getUsername());			
			}
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.friendListAddFriendbtn){
			Log.d (TAG,activeUser.getUsername());
			AsynchronTask runner = new AsynchronTask();
			
			/**
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
		    EditText input = new EditText(this);      
		    alert.setView(input);

		    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        //@Override
		        public void onClick(DialogInterface dialog, int which) {
		            EditText input = (EditText) dialog.findViewById("myInput");
		            Editable value = input.getText();
		            out = value.toString();               

		        }
		    });
		    **/
			runner.setKsoapAttributes(NAMESPACE, URL, "AddUserToFriendlist");
			runner.execute(this,activeUser,"test005");
		}
	}
	
	public void showFriendListCompleted(boolean success, List<User> userList){
		Log.d (TAG,"Friendlist- Antwort angekommen");
		progDailog.cancel();
		valueList = new ArrayList<String>();
		if (userList.isEmpty()){
			Log.d (TAG,"keine Freunde");
			valueList.add("TestFriend1");
			valueList.add("TestFriend2");
			valueList.add("TestFriend3");
			valueList.add("TestFriend4");
			valueList.add("TestFriend5");
			valueList.add("TestFriend6");
			valueList.add("TestFriend7");
			valueList.add("TestFriend8");
			valueList.add("TestFriend9");
			valueList.add("TestFriend10");
			valueList.add("TestFriend11");
			valueList.add("TestFriend12");	
		}
		else{
			Log.d (TAG,"userList notEmpty");
		}
			
					
		for(int i = 0;i<userList.size();i++){
			 valueList.add(userList.get(i).getUsername());
		}
		Log.d (TAG,"ListView füllen...");
		runOnUiThread(new Runnable() {
				    public void run(){   
				    	ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, valueList);
						final ListView lv = (ListView)findViewById(R.id.friendListView);
						lv.setAdapter(adapter);
			    }
			});
		
		Log.d (TAG,"...abgeschlossen");
		
	}
}
