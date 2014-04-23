package de.uno.usermanagement;

public interface UserDAOLocal {
	boolean UserLogin(String username, String password);
	User FindUserByName(String username);
	User AddUser(String username, String password);
	void RemoveFriend(User actualUser, String OldFriendUsername);
	void AddUserToFriendlist(User actualUser, String newFriendsUsername);
}
