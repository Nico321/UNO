package de.highscore;

import javax.persistence.Entity;
import javax.persistence.Id;

/**  
* @author Nico Lindmeyer 737045
*/ 
@Entity
public class User {
	
	@Id
	private String username;
	
	private int points;
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
