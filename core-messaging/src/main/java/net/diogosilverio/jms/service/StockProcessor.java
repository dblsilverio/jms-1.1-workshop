package net.diogosilverio.jms.service;

import java.util.Random;

import javax.inject.Named;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Cart;

/**
 * A stock processor mock.
 * 
 * @author diogo
 *
 */
@Named
public class StockProcessor {

	private Logger logger = Logger.getLogger(ShippingProcessor.class);

	private static final int DELAY = 15000;
	
	public void checkStock(Cart cart, Boolean premium) {
		logger.info("Checking stock for user " + cart.getUserId());
		try {
			Thread.sleep(new Random().nextInt((premium ? DELAY / 2 : DELAY)));
		} catch (InterruptedException e) {
		}
		
		logger.info("Done checking stock for user " + cart.getUserId());
	}

}
