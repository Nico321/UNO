package com.android.uno;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener{

	private Button registerBtn;
	private final String NAMESPACE = "http://usermanagement.uno.de/";
	private final String URL = "http://10.0.2.2:8080/Management/UserManagement";	 
	private static final String METHOD_NAME = "AddUser";
	private static final String TAG = Register.class.getName();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		EditText etusername = (EditText)findViewById(R.id.registerUsernameEdittext);
		String username = etusername.getText().toString();
		EditText etPassword = (EditText)findViewById(R.id.registerUserPassEdittext);
		String password = etPassword.getText().toString();
		EditText etPasswordC = (EditText)findViewById(R.id.registerUserPassConfirmEdittext);
		String passwordC = etPasswordC.getText().toString();
		
		
		if(!password.equals(passwordC) | password.length() < 6){
			System.out.println("Passwort zu kurz oder nicht übereinstimmend!");
		}
		
		else{
			registerBtn.setEnabled(false);
			RegisterTask runner = new RegisterTask();
			AsyncTask<String, String, String> s = runner.execute(username,password);
			//String s = register(username,password);
			System.out.println(s);
		}
	}
	
	
	
	
private SoapObject executeSoapAction(String methodName, Object... args) {
		
		Object result = null;
		
	    /* Create a org.ksoap2.serialization.SoapObject object to build a SOAP request. Specify the namespace of the SOAP object and method
	     * name to be invoked in the SoapObject constructor.
	     */
	    SoapObject request = new SoapObject(NAMESPACE, methodName);
	    
	    /* The array of arguments is copied into properties of the SOAP request using the addProperty method. */
	    for (int i=0; i<args.length; i++) {
		    request.addProperty("arg" + i, args[i]);
	    }
	    
	    /* Next create a SOAP envelop. Use the SoapSerializationEnvelope class, which extends the SoapEnvelop class, with support for SOAP 
	     * Serialization format, which represents the structure of a SOAP serialized message. The main advantage of SOAP serialization is portability.
	     * The constant SoapEnvelope.VER11 indicates SOAP Version 1.1, which is default for a JAX-WS webservice endpoint under JBoss.
	     */
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
	    
	    /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
	    envelope.setOutputSoapObject(request);
	    
	    /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
	     * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
	     */
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    
	    try {
	        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
		    List<HeaderProperty> reqHeaders = null;

		    @SuppressWarnings({"unused", "unchecked"})
			//List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
	    	//führt zu CXF-Fehler! neue Version ohne SOAP-Action funktioniert:
	    	List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);
	
	        /* Get the web service response using the getResponse method of the SoapSerializationEnvelope object.
	         * The result has to be cast to SoapPrimitive, the class used to encapsulate primitive types, or to SoapObject.
	         */
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
	    
	    return (SoapObject) result;
	}

	private class RegisterTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String result;
		try{
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			SoapObject response = executeSoapAction(METHOD_NAME, params[0], params[1]);
			 Log.d(TAG, response.toString());
			result = response.toString();			
			return result;
		}
		catch(Exception e) {
		    e.printStackTrace();
		    return result = e.getMessage();
		   }
		
		}
		
		
	
	}
}
