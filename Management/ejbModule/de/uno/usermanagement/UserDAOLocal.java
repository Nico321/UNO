package de.uno.usermanagement;

import java.util.List;

public interface UserDAOLocal {
	boolean UserLogin(String username, String password);
	User FindUserByName(String username);
	User AddUser(String username, String password);
	void RemoveFriend(User actualUser, String OldFriendUsername);
	void AddUserToFriendlist(User actualUser, String newFriendsUsername);
	List<User> ShowFriends(String username);
	List<User> ShowWannabeFriends(User actualUser);
	void AddNewWannabeFriend(User actualUser, User wantToBe);
}
