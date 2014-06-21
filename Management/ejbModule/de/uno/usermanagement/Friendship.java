package de.uno.usermanagement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="friends")
public class Friendship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Integer friendshipid;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User sourceUser;
	
	@ManyToOne()
	@JoinColumn(name = "friend_id")
	private User targetUser;
	
	public Friendship(User sourceUser, User targetUser){
		this.sourceUser = sourceUser;
		this.targetUser = targetUser;
	}
	
	public Friendship (){}

	public User getTargetUser() {
		return targetUser;
	}

}
