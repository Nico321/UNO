package de.uno.usermanagement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
/**
 * Session Bean implementation class User
 *  
 * @author Daniel Reider 734544
 * 
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;	
	@Column
	private String password;
	
	@OneToMany(mappedBy="sourceUser", cascade=CascadeType.ALL)
	private List<Friendship> friendships = new ArrayList<Friendship>();
	
	public String getUsername(){
		return username;
	}
	
	public void addFriend(User user) {
		friendships.add(new Friendship(this, user));
	}
	
	public void removeFriend(User user){
		int puffer = 0;
		for(Friendship f:friendships){
			puffer++;
			if(f.getTargetUser().equals(user)){
				friendships.remove(puffer);
				break;
			}				
		}	
	}
	
	public ArrayList<String> getFriends(){
		ArrayList<String> friends = new ArrayList<String>();
		for(Friendship f:friendships){
			friends.add(f.getTargetUser().getUsername());
		}		
		return friends;
	}
	
	public User(String username, String password){
			this.username = username;
			this.password = password;
	}

	public User(){};
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() == User.class){
			return ((User) o ).getUsername().equals(this.username);
		}
		else
			return false;
	}
}