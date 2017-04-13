package net.diogosilverio.jms.mdb.metrics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Metrics;

/**
 * MDB that publishes this metrics to an external system.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="PublisherMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jboss/exported/jms/topic/metrics")
		}
)
public class MetricsPublisherProcessor implements MessageListener {

	private Logger logger = Logger.getLogger(MetricsPublisherProcessor.class);
	
	@Override
	public void onMessage(Message message) {
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			Metrics metrics = (Metrics) objectMessage.getObject();
			
			logger.info("Publishing metrics to somewhere else: " + metrics);
			
		} catch(Exception e){
			logger.error("Error processing metrics: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
