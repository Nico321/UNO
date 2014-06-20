package de.uno.usermanagement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.*;
/**
 * Session Bean implementation class User
 *  
 * @author Daniel Reider 734544
 * 
 */
@Entity
public class User implements Serializable {
	private static final Logger log = Logger.getLogger( User.class.getName() );
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

	@ManyToMany
	@JoinTable(name="add_friends",
	 joinColumns=@JoinColumn(name="personId"),
	 inverseJoinColumns=@JoinColumn(name="addableUser")
	)
	private List<User> wannabeFriends;

	@ManyToMany
	@JoinTable(name="add_friends",
	 joinColumns=@JoinColumn(name="addableUser"),
	 inverseJoinColumns=@JoinColumn(name="personId")
	)
	private List<User> wannabeFriendsOf;
	
	
	
	
	public List<User> getFriends(){
		log.info("User:Friendlist");
		return friends;
	}
	
	public void setFriend(User actualUser, User newFriend){
		friendOf.add(actualUser);
		friends.add(newFriend);
		log.info("User:setFriend" + actualUser +"; " + newFriend);
	}
	
	public String getUsername(){
		return username;
	}
	public List<User> getFriendOf(){
		return friendOf;
	}
	
	public List<User> getWannabeFriends(){
		return wannabeFriends;
	}
	
	public void setNewWannabeFriend(User wannabe){
		wannabeFriends.add(wannabe);
		wannabeFriendsOf.add(this);
	}


public User(String username, String password){
		this.username = username;
		this.password = password;
		friends  = new ArrayList<User>();
		friendOf = new ArrayList<User>();
	}

public User(){};
}