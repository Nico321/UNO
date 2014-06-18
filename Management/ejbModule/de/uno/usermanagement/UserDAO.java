package de.uno.usermanagement;
import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.*;
/**
 * UserDAO
 *  
 * @author Daniel Reider 734544
 * 
 */
//Beinhaltet alle Datenbankabfragen/Verbindungen fï¿½r die Userklasse
@Stateless
public class UserDAO implements UserDAOLocal{
	
	private @PersistenceContext EntityManager em;
	
	//Methode zur Persistierung des Users durch EntityManager
	@Override
	public boolean AddUser(String username, String password){
		if(FindUserByName(username) == null){
		User user = new User(username, password);
		em.persist(user);
		return true;
		}
		else{
			return false;
		}
	}
	
	//Methode mit Datenbankabfrage zur Usersuche
	@Override
	public User FindUserByName(String username){
		return em.find(User.class, username);
	}
	
	//Methode mir Datenbankabfrage zur Freundlï¿½schung
	@Override
	public void RemoveFriend(String username, String OldFriendUsername){
		User oldFriend = em.find(User.class, OldFriendUsername);
		User actualUser = em.find(User.class, username);
		oldFriend.getFriends().remove(actualUser);
		actualUser.getFriendOf().remove(oldFriend);
		em.refresh(User.class);
	}
	
	//Methode mit Datenbankabfrage zum Freund adden
	@Override
	public void AddUserToFriendlist(String username, String newFriendsUsername){
		User actualUser = em.find(User.class, username);
		User newFriend = em.find(User.class, newFriendsUsername);
		for( User wannabe : actualUser.getWannabeFriends()){
			if(wannabe.getUsername() == newFriendsUsername){
				actualUser.getFriends().add(newFriend);
				newFriend.getFriendOf().add(actualUser);
			}
			else{
				newFriend.setNewWannabeeFriend(actualUser);
			}
		}

	}
	
	@Override
	public boolean UserLogin(String username, String password){
		//Direkte DB-Abfrage um das Password nicht in dem Objekt zu haben
		List<String> dbpw = em.createQuery("SELECT password from User where username like ?0").
		setParameter(0, username).
		getResultList();
		if(password.equals(dbpw.get(0)))
			return true;
		else
			return false;	
	}
	
	//Freunde eines Users werden mit Usernamen zurückgegeben
	@Override
	public List<String> ShowFriends(String username){
		List<User> users = em.find(User.class, username).getFriends();
		List<String> userNames = new ArrayList<String>();
		for (User user : users){
			userNames.add(user.getUsername());
		}
		return userNames;
	}
	
	//Leute ausgeben, die mit einem User befreundet sein möchten.
	@Override
	public List<String> ShowWannabeFriends(String username){
		List<User> users = em.find(User.class, username).getWannabeFriends();
		List<String> userNames = new ArrayList<String>();
		for (User user : users){
			userNames.add(user.getUsername());
		}
		return userNames;
	}
	
	@Override
	public boolean AddNewWannabeFriend(String username, String wantToBeUsername){
		User wantToBe = em.find(User.class, wantToBeUsername);
		wantToBe.setNewWannabeeFriend(FindUserByName(username));
		return true;
	}
}