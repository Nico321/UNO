package de.uno.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	@Column
	private String password;
	
	@ManyToMany
	@JoinTable(name="tbl_friends",
	 joinColumns=@JoinColumn(name="personId"),
	 inverseJoinColumns=@JoinColumn(name="friendId")
	)
	private List<User> friends;

	@ManyToMany
	@JoinTable(name="tbl_friends",
	 joinColumns=@JoinColumn(name="friendId"),
	 inverseJoinColumns=@JoinColumn(name="personId")
	)
	private List<User> friendOf;
	
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
		friends  = new ArrayList<User>();
		friendOf = new ArrayList<User>();
	}
	
	public List<User> getFriends(){
		return friends;
	}

	public void setFriend(User newFriend){
		
	}

	public String getUsername(){
		return username;
	}
	public List<User> getFriendOf(){
		return friendOf;
	}
}