package de.uno.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.uno.android.usermanagement.User;

public class FriendList extends Activity implements OnClickListener{

	
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://192.168.1.109:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "ShowFriendList";
	private static final String TAG = FriendList.class.getName();
	private User activeUser = null;
	private Button addFriendbtn;
	private ProgressDialog progDailog = null;
	private List<String> valueList;
	private MenuItem refresh = null;
	protected String result = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist_view);
		
		addFriendbtn = (Button) findViewById(R.id.friendListAddFriendbtn);
		addFriendbtn.setOnClickListener(this);
		activeUser = ((AppVariables) this.getApplication()).getUser();

		
		
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
	        // progDailog.show();
			Log.d (TAG,activeUser.getUsername());
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
			
 	        
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Title");
	        Log.d (TAG,"Builder-setTitle");
	        // Set up the input
	        final EditText input = new EditText(this);
	        builder.setTitle("Freund hinzufügen!");
	        builder.setMessage("Username eingeben:");
	        input.setInputType(InputType.TYPE_CLASS_TEXT);
	        builder.setView(input);
	        Log.d (TAG,"ButtonsPre");

	        // Set up the buttons
	        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	result  = input.getText().toString();
	            	Log.d (TAG,"ButtonsOK");
	            	AddFriendMethodCall();
	     	        progDailog = new ProgressDialog(FriendList.this);
	     	        progDailog.setMessage("Freund wird hinzugefügt...Bitte warten");
	     	        progDailog.setIndeterminate(false);
	     	        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	     	        progDailog.setCancelable(true);
	     	        progDailog.show();
	            	
	            }
	        });
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	Log.d (TAG,"ButtonsCancel");
	                dialog.cancel();
	            }
	        });
	        builder.create().show();
	    
	       
	        
			
		}
		
		
	}
	
	private void AddFriendMethodCall(){
		final AsynchronTask runner = new AsynchronTask();
		runner.setKsoapAttributes(NAMESPACE, URL, "AddUserToFriendlist");
	    runner.execute(this,activeUser.getUsername(),result.toString());
	}
	
	public void showFriendListCompleted(boolean success, ArrayList<String> userList){
		Log.d ("showFriendListCompleted","Friendlist- Antwort angekommen");
		progDailog.cancel();
		Log.d (TAG,"Progdialog canceled!");
		valueList = new ArrayList<String>();
		Log.d (TAG,"valueList created");
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
			for(int i = 0;i<userList.size();i++){
				 valueList.add(userList.get(i));
			}
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
	public void addUserToFriendlistCompleted(boolean success){
		Log.d ("addUserToFriendlistCompleted","Friendlist- Antwort angekommen");
		progDailog.cancel();	
		
		
	}
}
