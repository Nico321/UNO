package de.uno.usermanagement;

public interface UserManagementLocal {

	boolean AddUser(String username, String password);
	User FindUserByName(String username);
	void RemoveUserFromFriendlist(String actualUserName, String OldFriendUsername);
	boolean Login(String username, String password);
	String ShowFriendList(String username);
	void AddUserToFriendlist(String actualUsername, String newFriendsUsername);

}
