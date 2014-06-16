package de.highscore.jms;

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
	
    @Override
	public void onMessage(Message message) {
       try {
    	  TextMessage msg = (TextMessage) message;
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
          System.out.println("Received message from queue/HighscoreJMS: " + msg.getText());
       }
       catch (JMSException e) {
            throw new EJBException(e);
       }
    }

}
