package de.uno.android;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.util.Log;
import biz.source_code.base64Coder.Base64Coder;
import de.uno.android.lobbymanagement.LobbyGame;
import de.uno.android.usermanagement.User;


public class AsynchronTask extends AsyncTask<Object, Object, Object> {

	private String NAMESPACE;
	private String URL;	 
	private String METHOD_NAME;
	private static final String TAG = AsynchronTask.class.getName();

	
	@Override
	protected String doInBackground(Object... params) {
		String result = null;
	try{
		if (params[0] instanceof Register | params[0] instanceof Login | params[0] instanceof CreateGame){
			Log.d(TAG, "execute SoapAction-3Attr");
			executeSoapAction(params[0], params[1], params[2]);
		}
		if (params[0] instanceof NewGameHost | params[0] instanceof JoinGame){
			Log.d(TAG, "execute SoapAction-2Attr");
			executeSoapAction(params[0], params[1]);
		}
		if (params[0] instanceof NewGamePlayer){
			executeSoapAction(params[0]);
		}
		if (params[0] instanceof FriendList){
			if(METHOD_NAME.equals("AddUserToFriendlist")){
				Log.d(TAG, "execute SoapAction-3Attr");
				executeSoapAction(params[0], params[1], params[2]);
			}
			if(METHOD_NAME.equals("ShowFriendList")){
				Log.d(TAG, "execute SoapAction-2Attr");
				executeSoapAction(params[0], params[1]);
			}
		}
		return result;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return result = e.getMessage();
	   }
	
	}
	public boolean setKsoapAttributes(String NAMESPACE,String URL, String METHOD_NAME){
		this.NAMESPACE = NAMESPACE;
		this.URL = URL;
		this.METHOD_NAME = METHOD_NAME;
		
		return true;
	}
	private SoapPrimitive executeSoapAction(Object... params) throws SoapFault, SocketTimeoutException {
		SoapPrimitive result = null;
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    boolean success = false;
		//GameConnectionManager uno;
		//HighScore highscore;
	    HashMap<User, LobbyGame> possibleGames = null;
	    List<User> userFriendList = null;
	    

	    if (params[0] instanceof Register | params[0] instanceof Login){
	    	request.addProperty("arg0", params[1].toString());
	    	request.addProperty("arg1", params[2].toString());
	    }
	    if (params[0] instanceof FriendList){
	    	if(METHOD_NAME.equals("ShowFriendList")){
	    		request.addProperty("arg0", params[1].toString());
	    	}
	    	if(METHOD_NAME.equals("AddUserToFriendlist")){
	    		request.addProperty("arg0", (User) params[1]);
	    		request.addProperty("arg1", params[2].toString());
	    	}
	    }
	    if (params[0] instanceof JoinGame){
	    	if (params[1].toString().equals("joinLobbyGame")){
	    	request.addProperty("arg0", params[2].toString());
	    	}	
	    }
	    if (params[0] instanceof NewGameHost){
	    	User u = (User) params[1];
	    	request.addProperty("arg0", u.getUsername().toString());
	    }
	    if (params[0] instanceof CreateGame){
	    	request.addProperty("arg0", params[1].toString());
	    	Boolean b = (Boolean) params[2];
	    	request.addProperty("arg1", b);
	    }
	    
	    Log.d(TAG, "requestPropertys added");

	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    Log.d(TAG, "envelope erzeugt");
	    envelope.setOutputSoapObject(request);
	    Log.d(TAG, "request hinzugefügt");;
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    Log.d(TAG, "HttpTransport erstellt");
        try {	        
		    List<HeaderProperty> reqHeaders = null;
		    @SuppressWarnings({"unused", "unchecked"})			
	    	List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);
		    Log.d(TAG, "Serveraufruf getätigt");
		    
		    if(params[0] instanceof JoinGame){
		    	if (params[1].toString().equals("showOpenGames")){	    	

		    		Log.d(TAG, "JoinGameRefresh-response PRE");
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		Log.d(TAG, "JoinGameRefresh-response Post");		    	
		    		possibleGames = (HashMap<User, LobbyGame>) deserialize(response.toString());
		    		Log.d(TAG, "possibleGames deserialized");
		    		success = true;
		    	}
		    	if (params[1].toString().equals("joinLobbyGame")){
		    		success=true;
		    	}
		    }
		    
		    if(params[0] instanceof NewGamePlayer){
		    	Log.d(TAG, "NewGamePlayerRefresh-response PRE");
	    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	    		Log.d(TAG, "NewGamePlayerRefresh-response Post");		    	
	    		possibleGames = (HashMap<User, LobbyGame>) deserialize(response.toString());
	    		Log.d(TAG, "possibleGames deserialized");
	    		success = true;
		    }
		    
		    if(params[0] instanceof CreateGame){		    	
		    	Log.d(TAG, "CreateGame - LOG");
		    	User activeUser = (User) params[1];
		    	Log.d(TAG, activeUser.getUsername());
		    	SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    	success = Boolean.valueOf(response.toString());
		    	
		    }
		    if(params[0] instanceof NewGameHost){		    	
		    	Log.d(TAG, "NewGame - LOG");
		    	
		    	User activeUser = (User) params[1];
		    	User testUser1 = new User("testUser1","test123");
		    	Log.d(TAG, "testUser1 angelegt");
		    	/*
		    	GameConnectionManagerService service = new GameConnectionManagerService();
		    	Log.d(TAG, "service angelegt");
				uno = service.getGameConnectionManagerPort();
				Log.d(TAG, "port zugewiesen");
				uno.createNewGame(serialize(activeUser));
				Log.d(TAG, "spiel mit activeUser erstellt");
				uno.addPlayer(serialize(activeUser), serialize(testUser1));
				Log.d(TAG, "testUSer1 hinzugefügt");
				uno.startGame(serialize(testUser1));
				Log.d(TAG, "Spiel gestartet");
				
				HighScoreService highscoreService = new HighScoreService();
				highscore = highscoreService.getHighScorePort();
				*/
		    	success = true;
		    	
		    	
		    }    
		    if (params[0] instanceof Login){
		    	Log.d(TAG, "Login-Method-response PRE");
		    	SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    	Log.d(TAG, "Login-Method-response POST");
	            success = Boolean.valueOf(response.toString());
	            
		    }	
		    
		    if (params[0] instanceof Register){
		    	System.out.println(envelope.bodyIn.toString());
		    	SoapPrimitive response =  (SoapPrimitive) (envelope.getResponse());
		    	Log.d("GetPointsByUser - Response", response.toString());
		    	success = (boolean) deserialize(response.toString());
		    	Log.d(TAG, "success-cast erfolgreich");
		    	
		    }
		   
		    if (params[0] instanceof FriendList){
		    	if(METHOD_NAME.equals("ShowFriendList")){
		    		Log.d(TAG, "FriendListRefresh-response PRE");
			    	SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			    	Log.d(TAG, "FriendListRefresh-response POST");
			    	Object o = deserialize(response.toString());
			    	userFriendList = (List<User>) o;
			    	success = true;	
	            }
		    	if(METHOD_NAME.equals("AddUserToFriendlist")){
		    		Log.d(TAG, "AddUserToFriendlist-response PRE");
		    		success=true;
		    	}
	            
		    }	
		    
		    
	    }
	    catch(SocketTimeoutException e){
	    	success = false;
	    	e.printStackTrace();	    	
	    }
	    catch (SoapFault e) {
	    	success = false;
	    	e.printStackTrace();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }	      
        
        if(params[0] instanceof JoinGame){
        	if(params[1].toString().equals("showOpenGames")){
        		Log.d(TAG, "JoinGameRefresh Rückruf");
        		JoinGame j = (JoinGame) params[0];
        		j.showOpenGamesCompleted(success, possibleGames);
        	}
        	if(params[1].toString().equals("joinLobbyGame")){
        		Log.d(TAG, "JoinLobbyGame Rückruf");
        		JoinGame j = (JoinGame) params[0];
        		j.joinLobbyGameCompleted(success);
        	}
        	
	    }
        if(params[0] instanceof NewGamePlayer){
        	Log.d(TAG, "NewGamePlayer Rückruf");
        	NewGamePlayer n = (NewGamePlayer) params[0];
        	n.refreshPlayerCompleted(success, possibleGames);
        }
        if(params[0] instanceof CreateGame){
	    	Log.d(TAG, "CreateGame Rückruf");
	    	CreateGame j = (CreateGame) params[0];
	    	j.createGameCompleted(success);
	    }
        if (params[0] instanceof Login){
	    	Log.d(TAG, "Login Rückruf");
	    	Login l = (Login) params[0];
	    	l.loginCompleted(success);
	    }
        if (params[0] instanceof Register){
	    	Log.d(TAG, "Register Rückruf");
	    	Register r = (Register) params[0];
	    	r.registartionCompleted(success);
	    }
        if (params[0] instanceof FriendList){
        	if(METHOD_NAME.equals("ShowFriendList")){
		    	Log.d(TAG, "FriendListRefresh Rückruf");
		    	FriendList f = (FriendList) params[0];
		    	f.showFriendListCompleted(success, userFriendList);
        	}
	    }
        if (params[0] instanceof NewGameHost){
	    	Log.d(TAG, "NewServer Rückruf");
	    	NewGameHost n = (NewGameHost) params[0];
	    	n.createNewGameCompleted(success);
	    }
        
	    return result;
	}
	
	
	
	
	private String serialize(Serializable o){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }
    
	private static Object deserialize(String s){
		byte [] data = Base64Coder.decode( s );
        ObjectInputStream ois;
        Object o = null;
		try {
			Log.d(TAG, "Deserialisiere...");
			ois = new ObjectInputStream( 
			                                new ByteArrayInputStream(  data ) );
	        try {
				o  = ois.readObject();
			} catch (ClassNotFoundException e) {
				Log.d(TAG, "Fehler beim Deserialisieren");
				e.printStackTrace();
			}
	        ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "Deserialisiere...fertig!");
        return o;
	}
	
}