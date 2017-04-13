package net.diogosilverio.jms.mdb.metrics;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Metrics;

/**
 * A simple metrics logger.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="MetricsMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jboss/exported/jms/topic/metrics")
		}
)
public class MetricsProcessor implements MessageListener {

	private Logger logger = Logger.getLogger(MetricsProcessor.class);
	
	@Override
	public void onMessage(Message message) {
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			Metrics metrics = (Metrics) objectMessage.getObject();
			
			logger.info("Metrics received: " + metrics);
			
		} catch(Exception e){
			logger.error("Error processing metrics: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
