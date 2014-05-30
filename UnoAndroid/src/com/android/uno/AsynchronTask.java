package com.android.uno;

import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class AsynchronTask extends AsyncTask<Object, Object, Object> {

	private String NAMESPACE;
	private String URL;	 
	private String METHOD_NAME;

	
	@Override
	protected String doInBackground(Object... params) {
		String result = null;
	try{
		System.out.println("Error0");
			executeSoapAction(params[0], params[1], params[2]);		
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
	private void executeSoapAction(Object... params) {
		//SoapObject result = null;
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    
	    //for (int i=0; i<params.length; i++) {
		//    request.addProperty("arg" + i, params[i]);
	    //}

	    if (params[0] instanceof Register){
	    	request.addProperty("arg0", params[1].toString());
	    	request.addProperty("arg1", params[2].toString());
	    }
	    if (params[0] instanceof Login){
	    	request.addProperty("arg0", params[1].toString());
	    	request.addProperty("arg1", params[2].toString());
	    }
	    
	    System.out.println("Error1");

	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    System.out.println("Error2");
	    envelope.setOutputSoapObject(request);
	    System.out.println("Error3");
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    System.out.println("Error4");
	    try {	        
		    List<HeaderProperty> reqHeaders = null;
		    @SuppressWarnings({"unused", "unchecked"})			
	    	List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);
	        System.out.println("error5");
		    //result = (SoapObject) envelope.getResponse();  
	    }
	    catch (SoapFault e) {
	    	e.printStackTrace();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }	    
	    //String s = getResult(result);
	    //System.out.println(s);
	    if(params[0] instanceof Register){
	    	Register r2 = (Register) params[0];
	    	r2.registartionCompleted();
	    }	   
	}
}