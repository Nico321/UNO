package de.uno.usermanagement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
	
	/**
	* Neuer User wird mit Password und Usernamen erstellt und in der DB persistiert
	* @param username Usernamen des Users übergeben
	* @param password Password des Users uebergeben (verschluesselt)
	* @return Liefert ein boolean mit true=user erstellt oder false=user vergeben
	*/
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
	/**
	 * User ueber Usernamen finden 
	 * @param username Username uebergeben, nach dem gesucht werden soll
	 * @return User Object
	 */
	@Override
	public User FindUserByName(String username){
		return userdao.FindUserByName(username);
	}
	
	/**
	 * User zur Freundesliste/Favoritenliste hinzufügen
	 * @param actualUsername Username des aktuellen Users
	 * @param newFriendsUsername Username des Users, den man hinzufuegen moechte
	 */
	@Override
	@WebMethod
	public void AddUserToFriendlist(String actualUsername, String newFriendsUsername){
		userdao.AddUserToFriendlist(actualUsername, newFriendsUsername);
	}
	
	/**
	 * User von der Freundesliste entfernen
	 * @param actualUserName Username des aktuellen Users
	 * @param OldFriendUsername Username des Users, den man von der Liste loeschen moechte
	 */
	@Override
	@WebMethod
	public void RemoveUserFromFriendlist(String actualUserName, String OldFriendUsername){
		userdao.RemoveFriend(actualUserName, OldFriendUsername);
	}

	/**
	 * Login für User
	 * @param username Username des Users, der sich einloggen moechte
	 * @param password Password des Users
	 * @return true=einloggen successfull false=login failed
	 */
	@Override
	@WebMethod
	public boolean Login(String username, String password){
		return userdao.UserLogin(username, password);
	}
	/**
	 * Freundesliste anzeigen
	 * @param username Username, des Users, dessen Freunde angezeigt werden sollen
	 * @return Serialisierte ArrayList<String> mit den Namen der Freunde
	 */
	//Freundesliste anzeigen
	@Override
	@WebMethod
	public String ShowFriendList(String username){
		return serialize(userdao.ShowFriends(username));
	}
}