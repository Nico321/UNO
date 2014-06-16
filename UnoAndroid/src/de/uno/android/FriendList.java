package de.uno.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.uno.android.usermanagement.User;

public class FriendList extends Activity implements OnClickListener{

	private Button refreshbtn;
	private Button addFriendbtn;
	private List<String> valueList;
	private static final String NAMESPACE = "http://usermanagement.uno.de/";
	private static final String URL = "http://192.168.2.104:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "ShowFriendList";
	private static final String TAG = FriendList.class.getName();
	private User activeUser = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist_view);
		
		refreshbtn = (Button) findViewById(R.id.friendListRefreshbtn);
		refreshbtn.setOnClickListener(this);
		addFriendbtn = (Button) findViewById(R.id.friendListAddFriendbtn);
		addFriendbtn.setOnClickListener(this);
		activeUser = ((AppVariables) this.getApplication()).getUser();
		Log.d (TAG,"TestFreindlist");
		
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.friendListRefreshbtn){
			AsynchronTask runner = new AsynchronTask();
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this,((AppVariables) this.getApplication()).getUser().getUsername());
			
		}
		if(v.getId() == R.id.friendListAddFriendbtn){
			final String out;
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
			runner.setKsoapAttributes(NAMESPACE, URL, METHOD_NAME);
			runner.execute(this);
		}
	}
	
	public void showFriendListCompleted(boolean success, List<User> userList){
		Log.d (TAG,"Friendlist- Antwort angekommen");
		if (userList.isEmpty()){
			Log.d (TAG,"keine Freunde");
		}
		else{
			Log.d (TAG,"userList notEmpty");
			valueList = new ArrayList<String>();
			valueList.add("TestFriend");
			 for(int i = 0;i<userList.size();i++){
				 valueList.add(userList.get(i).getUsername());
			 }
			ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, valueList);
			final ListView lv = (ListView)findViewById(R.id.friendListView);
			lv.setAdapter(adapter);
		}
	}
}
