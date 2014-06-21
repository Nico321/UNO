package de.highscore.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import de.highscore.common.HighScoreLocal;

/**
 * Message-Driven Bean implementation class for: HighScoreJMS
 */
@MessageDriven(
		activationConfig = {  
			 @ActivationConfigProperty(
			      propertyName = "destinationType",
			      propertyValue = "javax.jms.Queue"),
			 @ActivationConfigProperty(
			      propertyName = "destination",
			      propertyValue = "queue/HighscoreJMS"),
			 @ActivationConfigProperty(
			      propertyName = "messageSelector",
			      propertyValue = "DocType LIKE 'Letter'") })
public class HighScoreJMS implements MessageListener {
	
	private static final Logger log = Logger.getLogger(HighScoreJMS.class.getName());
	
	//Bei Erhalt einer Nachricht wird die Punktzahl auf dem Highscore Server erhöht
    @Override
	public void onMessage(Message message) {
    	
       try {
    	  TextMessage msg = (TextMessage) message;
    	  log.info("received new message, trying to handle it: " + msg.getText());
    	  
    	  	InitialContext context;
	  		HighScoreLocal highscore = null;
	  		try {
	  			context = new InitialContext();
	  			String lookupString = "ejb:HighScoreEAR/HighScore/HighScore!de.highscore.common.HighScoreLocal";
	  			highscore = (HighScoreLocal) context.lookup(lookupString);
	  		}catch (Exception e){
	  			e.printStackTrace();
	  		}
	  		
	  	  String[] userpoints = msg.getText().split(":");
	  	  highscore.addPointsToUser(userpoints[0], Integer.parseInt(userpoints[1]));
       }
       catch (JMSException e) {
    	   log.log(Level.SEVERE, "error while processing message", e);
           throw new EJBException(e);
       }
    }

}
