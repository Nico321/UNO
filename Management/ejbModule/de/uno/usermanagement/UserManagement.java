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
public class UserManagement {
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
	//Password muss verschlüsselt übergeben werden
	@WebMethod
	public String AddUser(String username, String password){
		if(userdao.FindUserByName(username) == null){
			log.info("User Added: " + username);
			return serialize(userdao.AddUser(username, password));
		}
		else{
			log.info("Username already in use");
			return null;
		}
	}
	
	//User über Usernamen suchen
	@WebMethod
	public String FindUserByName(String username){
		return serialize(userdao.FindUserByName(username));
	}
	
	//User zur Freundesliste hinzufügen
	@WebMethod
	public void AddUserToFriendlist(User actualUser, String newFriendsUsername){
		((User)deserialize(actualUser.getUsername())).setFriend(actualUser, userdao.FindUserByName(newFriendsUsername));
	}
	
	//User von der Freundesliste entfernen
	@WebMethod
	public void RemoveUserFromFriendlist(String actualUserName, String OldFriendUsername){
		userdao.RemoveFriend(actualUserName, OldFriendUsername);
	}

	//Login-Funktion
	@WebMethod
	public boolean Login(String username, String password){
		return userdao.UserLogin(username, password);
	}
	
	//Freundesliste anzeigen
	@WebMethod
	public String ShowFriendList(String username){
		return serialize((Serializable) userdao.ShowFriends(username));
	}
	
	//Nächsten möglichen Freunde anzeigen
	@WebMethod
	public String ShowWannabeFriends(String actualUserName){
		return serialize((Serializable) userdao.ShowWannabeFriends(actualUserName));
	}
	
	//User zur Freundesliste hinzufügen
	@WebMethod
	public void AddNewWannabeFriend(String actualUserName, String wantToBeName){
		userdao.AddNewWannabeFriend(actualUserName, wantToBeName);
	}
	
	@WebMethod
	public void UserLogin(String username, String password){
		userdao.UserLogin(username, password);
	}
}