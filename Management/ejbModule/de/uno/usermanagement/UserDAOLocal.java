package de.uno.usermanagement;
import java.util.List;
/**
 * UserDAO Interface
 *  
 * @author Daniel Reider 734544
 * 
 */
public interface UserDAOLocal {
	boolean UserLogin(String username, String password);
	User FindUserByName(String username);
	boolean AddUser(String username, String password);
	void RemoveFriend(String username, String OldFriendUsername);
	void AddUserToFriendlist(String username, String newFriendsUsername);
	List<User> ShowFriends(String username);
	List<User> ShowWannabeFriends(String username);
	void AddNewWannabeFriend(String username, String wantToBeUsername);
}
