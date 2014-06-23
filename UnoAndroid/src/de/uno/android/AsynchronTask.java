package de.uno.android;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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
	private String URL = "http://192.168.1.101:8080";	 
	private String METHOD_NAME;
	private static final String TAG = AsynchronTask.class.getName();

	
	@Override
	protected String doInBackground(Object... params) {
		String result = null;
	try{
		if (params[0] instanceof Register | params[0] instanceof Login){
			Log.d(TAG, "execute SoapAction-3Attr");
			executeSoapAction(params[0], params[1], params[2]);
		}
		if(params[0] instanceof CreateGame){
			if(METHOD_NAME.equals("createNewGame")){
				Log.d(TAG, "execute SoapAction-3Attr - createNewGame");
				executeSoapAction(params[0], params[1], params[2]);	
			}
			
		}
		if (params[0] instanceof NewGameHost | params[0] instanceof NewGamePlayer){
				executeSoapAction(params[0], params[1]);
					
		}
		
		if (params[0] instanceof Highscore){
			executeSoapAction(params[0]);
		}
		if(params[0] instanceof JoinGame){
			if(METHOD_NAME.equals("showOpenGames")){
				executeSoapAction(params[0]);
			}
			if(METHOD_NAME.equals("joinLobbyGame")){
				executeSoapAction(params[0], params[1], params[2]);
			}
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
		this.URL = this.URL.concat(URL);
		this.METHOD_NAME = METHOD_NAME;
		Log.d(TAG,this.URL);
		
		return true;
	}
	private SoapPrimitive executeSoapAction(Object... params) throws SoapFault, SocketTimeoutException {
		SoapPrimitive result = null;
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    boolean success = false;
		//GameConnectionManager uno;
		//HighScore highscore;
	    ArrayList<String> userFriendList = null;
	    ArrayList<String> participatingPlayers = null;
	    HashMap<String, Integer> highscore = null;
	    

	    if (params[0] instanceof Register | params[0] instanceof Login){
	    	request.addProperty("arg0", params[1].toString());
	    	request.addProperty("arg1", params[2].toString());
	    }
	    if (params[0] instanceof FriendList){
	    	if(METHOD_NAME.equals("ShowFriendList")){
	    		request.addProperty("arg0", params[1].toString());
	    	}
	    	if(METHOD_NAME.equals("AddUserToFriendlist")){
	    		request.addProperty("arg0", params[1].toString());
	    		request.addProperty("arg1", params[2].toString());
	    	}
	    }
	    if (params[0] instanceof JoinGame){	
	    	if (METHOD_NAME.equals("joinLobbyGame")){
	    		Log.d(TAG,"joinLobbyGame- addProperty");
		    	request.addProperty("arg0", params[1].toString());
		    	request.addProperty("arg1", params[2].toString());
		    	}	
	    }
	    if (params[0] instanceof NewGameHost | params[0] instanceof NewGamePlayer){
	    	request.addProperty("arg0", params[1].toString());
	    }
	    if (params[0] instanceof CreateGame){
	    	if(METHOD_NAME.equals("createNewGame")){
	    		Log.d("CreateGame","addProperty");
	    		request.addProperty("arg0", params[1].toString());
	    		request.addProperty("arg1", true);
	    	}
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
		    Log.d(TAG, "=================================");
		    
		    if(params[0] instanceof JoinGame){
		    	if (METHOD_NAME.equals("showOpenGames")){	    	
		    		participatingPlayers = null;
		    		Log.d(TAG, "JoinGameRefresh-response PRE");
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		Log.d(TAG, "JoinGameRefresh-response Post");		    	
		    		Object ul = deserialize(response.toString());
		    		Log.d(TAG, "response deserialized");
		    		participatingPlayers = (ArrayList<String>) ul;
		    		Log.d(TAG, "possibleGames casted");
		    		success = true;
		    	}
		    	if (METHOD_NAME.equals("joinLobbyGame")){
		    		Log.d(TAG, "JoinLobbyGame-response PRE");
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		Log.d(TAG, "JoinLobbyGame-response PRE");
		    		success = Boolean.valueOf(response.toString());
		    		Log.d(TAG, "JoinLobbyGame-success set " + success);
		    	}
		    }
		    if(params[0] instanceof Highscore){
		    	Log.d(TAG, "HighscoreRefresh-response PRE");
	    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	    		Log.d(TAG, "HighscoreRefresh-response Post");		    	
	    		Object ul = deserialize(response.toString());
	    		Log.d(TAG, "response deserialized");
			    highscore = (HashMap<String, Integer>) ul;
			    success = true;
		    }
		    

		    
		    if(params[0] instanceof NewGamePlayer){
		    	participatingPlayers = null;
		    	Log.d(TAG, "showParticipatingPlayer - LOG");
	    		Log.d(TAG, "showParticipatingPlayer-response PRE");
	    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	    		Log.d(TAG, "showParticipatingPlayer-response Post");		    	
	    		Object ul = deserialize(response.toString());
	    		Log.d(TAG, "response deserialized");
	    		participatingPlayers = (ArrayList<String>) ul;
			    success = true;
		    }
		    
		    if(params[0] instanceof CreateGame){	
		    	if(METHOD_NAME.equals("createNewGame")){
		    		Log.d(TAG, "CreateNewGame - LOG");
		    		String username = params[1].toString();
		    		Log.d(TAG, username);
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		success = Boolean.valueOf(response.toString());
		    	}
		    	
		    }
		    if(params[0] instanceof NewGameHost){		    	
		    	if(METHOD_NAME.equals("showParticipatingPlayer")){
		    		participatingPlayers = null;
		    		Log.d(TAG, "showParticipatingPlayer - LOG");
		    		Log.d(TAG, "showParticipatingPlayer-response PRE");
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		Log.d(TAG, "showParticipatingPlayer-response Post");		    	
		    		Object ul = deserialize(response.toString());
		    		Log.d(TAG, "response deserialized");
		    		participatingPlayers = (ArrayList<String>) ul;
				    success = true;
		    		
		    	}
		    	if(METHOD_NAME.equals("startGame")){		    		
				    success = true;
		    	}
		    	if(METHOD_NAME.equals("deleteLobbyGame")){	
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		    		success = Boolean.valueOf(response.toString());
		    		Log.d(""," " + success);
		    	}  				    	
		    	
		    }    
		    if (params[0] instanceof Login){
		    	SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	    		success = Boolean.valueOf(response.toString());
	            Log.d(TAG, "Login_success_booleanOf erfolgreich");
	            
		    }	
		    
		    if (params[0] instanceof Register){
		    	SoapPrimitive response =  (SoapPrimitive) (envelope.getResponse());
		    	Log.d("GetPointsByUser - Response", response.toString());
		    	success = Boolean.valueOf(response.toString());
		    	Log.d(TAG, "Register_success_booleanOf erfolgreich");
		    	
		    }
		   
		    if (params[0] instanceof FriendList){
		    	if(METHOD_NAME.equals("ShowFriendList")){
		    		Log.d(TAG, "FriendListRefresh-response PRE");
		    		SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			    	Log.d(TAG, "FriendListRefresh-response POST");
			    	userFriendList = (ArrayList<String>) deserialize(response.toString());
			    	
			    	success = true;	
	            }
		    	if(METHOD_NAME.equals("AddUserToFriendlist")){
		    		Log.d(TAG, "AddUserToFriendlist-LOG");
		    		success=true;
		    	}
	            
		    }	
		    
		    
	    }
	    catch(SocketTimeoutException e){
	    	success = false;
	    	Log.d(TAG, "Zeitüberschreitung");
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
        	if(METHOD_NAME.equals("showOpenGames")){
        		Log.d(TAG, "JoinGameRefresh Rückruf");
        		JoinGame j = (JoinGame) params[0];
        		j.showOpenGamesCompleted(success, participatingPlayers);
        	}
        	if(METHOD_NAME.equals("joinLobbyGame")){
        		Log.d(TAG, "JoinLobbyGame Rückruf");
        		JoinGame j = (JoinGame) params[0];
        		j.joinLobbyGameCompleted(success);
        	}
        	
	    }
        if(params[0] instanceof Highscore){
        	Log.d(TAG, "HighscoreRefresh Rückruf");
        	Highscore h = (Highscore) params[0];
        	h.HighScoreListCompleted(success, highscore);
        }
        if(params[0] instanceof NewGamePlayer){
        	Log.d(TAG, "NewGamePlayer Rückruf");
        	NewGamePlayer n = (NewGamePlayer) params[0];
        	n.participatingPlayersCompleted(success,participatingPlayers);
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
        	if(METHOD_NAME.equals("AddUserToFriendlist")){
        		Log.d(TAG, "AddUserToFriendlist Rückruf");
        		FriendList f = (FriendList) params[0];
        		f.addUserToFriendlistCompleted(success);
        	}
        	
	    }
        if (params[0] instanceof NewGameHost){
        	if(METHOD_NAME.equals("showParticipatingPlayer")){
        		Log.d(TAG, "NewServerHost Rückruf");
        		NewGameHost n = (NewGameHost) params[0];
        		n.participatingPlayersCompleted(success, participatingPlayers);
        	}
        	if(METHOD_NAME.equals("startGame")){
        		Log.d(TAG, "NewServerHost Rückruf");
        		NewGameHost n = (NewGameHost) params[0];
        		n.createNewGameCompleted(success);
        		
        	}
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