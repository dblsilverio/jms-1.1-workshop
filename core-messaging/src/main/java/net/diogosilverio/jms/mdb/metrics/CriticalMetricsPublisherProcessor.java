package net.diogosilverio.jms.mdb.metrics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Metrics;

/**
 * It warns critical alerts for everyone.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="CriticalPublisherMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jboss/exported/jms/topic/metrics"),
				@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "level = 'CRITICAL'")
		}
)
public class CriticalMetricsPublisherProcessor implements MessageListener {

	private Logger logger = Logger.getLogger(CriticalMetricsPublisherProcessor.class);
	
	@Override
	public void onMessage(Message message) {
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			Metrics metrics = (Metrics) objectMessage.getObject();
			
			logger.info("*** ALERT *** \r\n" + metrics + "\r\n**************");
			
		} catch(Exception e){
			logger.error("Error processing metrics: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
