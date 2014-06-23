package de.uno.usermanagement;
import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.*;
import java.util.logging.Logger;
/**
 * UserDAO
 *  
 * @author Daniel Reider 734544
 * 
 */
//Beinhaltet alle Datenbankabfragen/Verbindungen f�r die Userklasse
@Stateless
public class UserDAO implements UserDAOLocal{
	private static final Logger log = Logger.getLogger( UserDAO.class.getName() );
	
	private @PersistenceContext EntityManager em;
	
	//Methode zur Persistierung des Users durch EntityManager
	@Override
	public boolean AddUser(String username, String password){
		if(FindUserByName(username) == null){
			User user = new User(username, password);
			em.persist(user);
			log.info("Added new Player: " + username);
			return true;
		}
		else{
			log.info("Username already existing");
			return false;
		}
	}
	
	//Methode mit Datenbankabfrage zur Usersuche
	@Override
	public User FindUserByName(String username){
		log.info("Found User: " + em.find(User.class, username));
		return em.find(User.class, username);
	}
	
	//Methode mir Datenbankabfrage zur Freundl�schung
	@Override
	public void RemoveFriend(String username, String OldFriendUsername){
		log.info("Remove friend: " + username);
		User oldFriend = em.find(User.class, OldFriendUsername);
		User actualUser = em.find(User.class, username);
		actualUser.removeFriend(oldFriend);
		em.persist(actualUser);
	}
	
	//Methode mit Datenbankabfrage zum Freund adden
	@Override
	public void AddUserToFriendlist(String username, String newFriendsUsername){
		User actualUser = em.find(User.class, username);
		User newFriend = em.find(User.class, newFriendsUsername);
		actualUser.addFriend(newFriend);
		em.persist(actualUser);
		log.info("Added "+ newFriendsUsername + " to "+ username+ "'s friendslist.");
	}
	
	@Override
	public boolean UserLogin(String username, String password){
		log.info("Userlogin: " + username);
		//Direkte DB-Abfrage um das Password nicht in dem Objekt zu haben
		List<String> dbpw = em.createQuery("SELECT password from User where username like ?0").
		setParameter(0, username).
		getResultList();
		log.info("Password: " + dbpw.get(0));
		if(password.equals(dbpw.get(0)))
			return true;
		else
			return false;	
	}
	
	//Freunde eines Users werden mit Usernamen zurueckgegeben
	@Override
	public ArrayList<String> ShowFriends(String username){
		User user = em.find(User.class, username);
		return user.getFriends();
	}
}