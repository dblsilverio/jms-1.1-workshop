package net.diogosilverio.jms.mdb.checkout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Cart;
import net.diogosilverio.jms.service.PaymentProcessor;
import net.diogosilverio.jms.service.ShippingProcessor;
import net.diogosilverio.jms.service.StockProcessor;
/**
 * Default MDB for default profile costumers.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="CheckoutMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jboss/exported/jms/queue/checkout"),
				@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type <> 'PREMIUM' "),
				@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "30")
		}
)
public class CheckoutProcessor implements MessageListener {

	private Logger logger = Logger.getLogger(CheckoutProcessor.class);
	
	@Inject
	private StockProcessor stockProcessor;
	
	@Inject
	private PaymentProcessor paymentProcessor;
	
	@Inject
	private ShippingProcessor shippingProcessor;
	
	@Override
	public void onMessage(Message message) {
		
		try{
			if(message instanceof ObjectMessage){
				
				ObjectMessage objectMessage = (ObjectMessage) message;

				Cart cart = (Cart) objectMessage.getObject();
				
				logger.info("Processing cart for user id# " + cart.getUserId());
				
				stockProcessor.checkStock(cart, Boolean.FALSE);
				paymentProcessor.chargeUser(cart.getUserId(), Boolean.FALSE);
				shippingProcessor.shipProducts(cart, Boolean.FALSE);
				logger.info("Processing cart for user id# " + cart.getUserId() + " is done! Total checkout amount: $" + cart.cartTotal().doubleValue());
			}
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
	}

}