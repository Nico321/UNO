package de.uno.usermanagement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import biz.source_code.base64Coder.Base64Coder;
/**
 * User Management Class
 *  
 * @author Daniel Reider 734544
 * 
 */


@Stateless
@Remote
@WebService
public class UserManagement implements UserManagementLocal{
	private static final Logger log = Logger.getLogger( UserManagement.class.getName() );
	@EJB
	UserDAOLocal userdao;
		
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
	
	//Neuer User wird mit PW und Usernamen in der Datenbank persistiert
	//Password muss verschl�sselt �bergeben werden
	@Override
	@WebMethod
	public boolean AddUser(String username, String password){
		if(userdao.FindUserByName(username) == null){
			log.info("User Added: " + username);
			return userdao.AddUser(username, password);
		}
		else{
			log.info("Username already in use");
			return false;
		}
	}
	
	//User �ber Usernamen suchen
	@Override
	public User FindUserByName(String username){
		return userdao.FindUserByName(username);
	}
	
	//User zur Freundesliste hinzuf�gen
	@Override
	@WebMethod
	public void AddUserToFriendlist(String actualUsername, String newFriendsUsername){
		userdao.AddUserToFriendlist(actualUsername, newFriendsUsername);
	}
	
	//User von der Freundesliste entfernen
	@Override
	@WebMethod
	public void RemoveUserFromFriendlist(String actualUserName, String OldFriendUsername){
		userdao.RemoveFriend(actualUserName, OldFriendUsername);
	}

	//Login-Funktion
	@Override
	@WebMethod
	public boolean Login(String username, String password){
		return userdao.UserLogin(username, password);
	}
	
	//Freundesliste anzeigen
	@Override
	@WebMethod
	public String ShowFriendList(String username){
		return serialize(userdao.ShowFriends(username));
	}
}