package de.highscoredao;

import java.util.Collection;

import javax.ejb.Local;

import de.highscore.User;

@Local
public interface UserDAOLocal {
	
	public void addPointsToUser(String username, Integer points);
	public User getUser(String username);
	public Collection<User> getAllUsers();
	
}
