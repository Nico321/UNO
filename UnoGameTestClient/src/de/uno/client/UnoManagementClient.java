package de.uno.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;



import java.util.LinkedList;

import biz.source_code.base64Coder.Base64Coder;
import de.uno.lobbymanagement.*;
import de.uno.usermanagement.*;
import de.uno.gameconnection.*;

public class UnoManagementClient {
	private static UserManagement userManagement;
	//private static LobbyManagement lobbyManagement;
	private static User nico, dave;
	
	private static String serialize(Serializable o){
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
			ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
	        try {
				o  = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return o;
	}
    
    public static void main(String[] args){
    	User nico = (User) deserialize(userManagement.addUser("Nico", "passwd123"));
    	User dave = (User) deserialize(userManagement.addUser("Dave", "passwd123"));
    	
    	userManagement.addUserToFriendlist(serialize(nico), "Dave");
    	userManagement.removeUserFromFriendlist(serialize(dave), "Nico");
    	
    }

}
