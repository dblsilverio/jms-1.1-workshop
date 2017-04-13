package net.diogosilverio.jms.mdb.checkout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Cart;

/**
 * Responsible for logging and warning checkout dead messages.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="CheckoutDLQMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/checkoutDLQ"),
				@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "10")
		}
)
public class CheckoutProcessorDLQ implements MessageListener {

	private Logger logger = Logger.getLogger(CheckoutProcessorDLQ.class);

	@Override
	public void onMessage(Message message) {
		
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			Cart cart = (Cart) objectMessage.getObject();
			
			logger.warn("Cart from client #" + cart.getUserId() + " found on DLQ =/");
			
		} catch(Exception e){
			logger.error("Error processing message from dead queue: " + e.getMessage());
			throw new RuntimeException(e);
		}
		
	}

}
