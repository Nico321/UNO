package de.uno.usermanagement;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import biz.source_code.base64Coder.Base64Coder;


@Stateless
@Remote
@WebService
public class UserManagement {
	@EJB
	UserDAOLocal userdao;
	
	//Neuer User wird mit PW und Usernamen in der Datenbank persistiert
	//Password muss verschlüsselt übergeben werden
	
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
			ois = new ObjectInputStream( 
			                                new ByteArrayInputStream(  data ) );
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
	
	@WebMethod
	public String AddUser(String username, String password){
		if(userdao.FindUserByName(username) == null)
			return serialize(userdao.AddUser(username, password));
		else
			return null;
	}
	
	//User über Usernamen suchen
	@WebMethod
	public String FindUserByName(String username){
		return serialize(userdao.FindUserByName(username));
	}
	
	//User zur Freundesliste hinzufügen
	@WebMethod
	public void AddUserToFriendlist(String actualUser, String newFriendsUsername){
		((User)deserialize(actualUser)).setFriend(userdao.FindUserByName(newFriendsUsername));
	}
	
	//User von der Freundesliste entfernen
	@WebMethod
	public void RemoveUserFromFriendlist(String actualUser, String OldFriendUsername){
		userdao.RemoveFriend((User)deserialize(actualUser), OldFriendUsername);
	}
}