package net.diogosilverio.jms.service;

import java.util.Random;

import javax.inject.Named;

import org.jboss.logging.Logger;

import net.diogosilverio.jms.model.Cart;

/**
 * A Shipping processor mock.
 * 
 * @author diogo
 *
 */
@Named
public class ShippingProcessor {

	private Logger logger = Logger.getLogger(ShippingProcessor.class);

	private static final int DELAY = 30000;
	
	public void shipProducts(Cart cart, Boolean premium) {
		logger.info("Preparing shipping for user " + cart.getUserId());
		try {
			Thread.sleep(new Random().nextInt((premium ? DELAY / 2 : DELAY)));
		} catch (InterruptedException e) {
		}
		logger.info("Done shipping for user " + cart.getUserId());
	}

}
