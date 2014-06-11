package de.uno.android.common;

import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import de.uno.Hand.Hand;
import de.uno.common.GameConnectionRemote;


public class GameStub implements GameConnectionRemote {
	 
	private static final String NAMESPACE = "http://gameconnection.uno.de/";
	
	private static final String URL = "http://192.168.2.107:8080/UnoGame/GameConnectionManager";
	
	private static final String TAG = GameStub.class.getName();

	@Override
	public String getNextPlayer(String player) {
		Log.d(TAG, "getNextPlayer called");
		String METHOD_NAME = "getNextPlayer";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		Log.d(TAG, response.toString());
		return response.toString();
	}

	@Override
	public String drawCard(String player, int quantity) {
		Log.d(TAG, "drawCard called");
		String METHOD_NAME = "drawCard";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player, quantity);
		Log.d(TAG, response.toString());
		return response.toString();
	}

	@Override
	public boolean putCard(String player, String card) {
		Log.d(TAG,"putCard called with Player:" + player + " Card:" + card);
		boolean result = false;
		String METHOD_NAME ="putCard";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player, card);
		return Boolean.parseBoolean(response.toString());
	}

	@Override
	public String getPlayerStatus(String player) {
		Log.d(TAG,"getPlayerStatus called with Player:" + player);
		String METHOD_NAME = "getPlayerStatus";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		return response.toString();
	}

	@Override
	public String getStackCard(String player) {
		Log.d(TAG,"getStackCard called with Player:" + player);
		String METHOD_NAME ="getStackCard";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		return response.toString();
	}

	@Override
	public void startGame(String player) {
		Log.d(TAG, "startGame method called with" + "Player: "+ player);
		String METHOD_NAME ="startGame";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		Log.d(TAG, response.toString());
	}

	@Override
	public String getHand(String player) {
		Log.d(TAG, "getHand method called with" + "Player: "+ player);
		String METHOD_NAME ="getHand";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		Log.d(TAG, response.toString());
		
		return response.toString();
	}

	@Override
	public void setWishedColor(String player, String wishedColor) {
		Log.d(TAG, "setWishedColor method called with" + "Player: "+ player + " wishedColor:" + wishedColor);
		String METHOD_NAME ="setWishedColor";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player,wishedColor);
		Log.d(TAG, response.toString());	
	}

	@Override
	public String getWishedColor(String player) {
		Log.d(TAG, "getWishedColor method called with" + "Player: "+ player);
		String METHOD_NAME ="getWishedColor";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		Log.d(TAG, response.toString());
		return response.toString();
	}

	@Override
	public void createNewGame(String player) {
		Log.d(TAG, "createNewGame method called with" + "Player: "+ player);
		String METHOD_NAME ="createNewGame";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, player);
		Log.d(TAG, response.toString());
	}

	@Override
	public void addPlayer(String creator, String member) {
		Log.d(TAG, "addPlayer method called with" + "Creator: "+ creator + " member:" + member);
		String METHOD_NAME ="addPlayer";
		SoapPrimitive response = executeSoapAction(METHOD_NAME, creator,member);
		Log.d(TAG, response.toString());
		
	}
	/**
	 * Diese Methode delegiert einen Methodenaufruf an den hinterlegten WebService.
	 * @param methodName
	 * @return SoapPrimitive
	 */
	private SoapPrimitive executeSoapAction(String methodName, Object... args) {
		Object result = null;
	    SoapObject request = new SoapObject(NAMESPACE, methodName);
	    
	    for (int i=0; i<args.length; i++) {
		    request.addProperty("arg" + i, args[i]);
	    }
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
	    envelope.setOutputSoapObject(request);
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    
	    try {
		    List<HeaderProperty> reqHeaders = null;

		    @SuppressWarnings({"unused", "unchecked"})
	    	List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);
	        result = envelope.getResponse();
	        if (result instanceof SoapFault) {
	        	throw (SoapFault) result;
	        }
	    }
	    catch (SoapFault e) {
	    	e.printStackTrace();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
		return (SoapPrimitive) result;	    
	   
	}
	
 
}
