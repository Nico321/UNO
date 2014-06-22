package de.highscoredao;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.highscore.User;

/**
 * Session Bean implementation class UserDAO
 *  
 * @author Nico Lindmeyer 737045
 * 
 */
@Stateless
public class UserDAO implements UserDAOLocal {

	private @PersistenceContext EntityManager em;
	private static final Logger log = Logger.getLogger(UserDAO.class.getName());
	
	private void addUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPoints(0);
		em.persist(user);
		log.info("wrote new Player to Database: " + username);
	}

	@Override
	public void addPointsToUser(String username, Integer points) {
		User user = getUser(username);
		if(user == null){
			addUser(username);
			user = getUser(username);
		}
		user.setPoints(points + user.getPoints());
	}

	@Override
	public User getUser(String username) {
		return em.find(User.class, username);
	}

	@Override
	public Collection<User> getAllUsers() {
		Query query = em.createQuery("SELECT e FROM User e ORDER BY e.points DESC");
		
		return (Collection<User>) query.getResultList();
	}

}
