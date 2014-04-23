package de.uno.usermanagement;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.*;

//Beinhaltet alle Datenbankabfragen/Verbindungen für die Userklasse
@Stateless
public class UserDAO implements UserDAOLocal{
	
	private @PersistenceContext EntityManager em;
	
	//Methode zur Persistierung des Users durch EntityManager
	@Override
	public User AddUser(String username, String password){
		User user = new User(username, password);
		em.persist(user);
		return user;
	}
	
	//Methode mit Datenbankabfrage zur Usersuche
	@Override
	public User FindUserByName(String username){
		return em.find(User.class, username);
	}
	
	//Methode mir Datenbankabfrage zur Freundlöschung
	@Override
	public void RemoveFriend(User actualUser, String OldFriendUsername){
		User oldFriend = em.find(User.class, OldFriendUsername);
		User actualuser = em.find(User.class, actualUser.getUsername());
		oldFriend.getFriends().remove(actualuser);
		FindUserByName(actualUser.getUsername()).getFriendOf().remove(actualuser);
		em.refresh(User.class);
	}
	
	//Methode mit Datenbankabfrage zum Freund adden
	@Override
	public void AddUserToFriendlist(User actualUser, String newFriendsUsername){
		User newFriend = em.find(User.class, newFriendsUsername);
		actualUser.getFriends().add(newFriend);
		newFriend.getFriendOf().add(actualUser);
		em.refresh(actualUser);
		em.refresh(newFriend);
	}
	
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
}