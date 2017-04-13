package net.diogosilverio.jms.service;

import java.util.Random;

import javax.inject.Named;

import org.jboss.logging.Logger;

/**
 * A payment processor mock.
 * @author diogo
 *
 */
@Named
public class PaymentProcessor {

	private Logger logger = Logger.getLogger(PaymentProcessor.class);
	
	private static final int DELAY = 3000;
	
	public void chargeUser(String userId, Boolean premium){
		logger.info("Charging user " + userId);
		try {
			Thread.sleep(new Random().nextInt((premium ? DELAY / 2 : DELAY)));
		} catch (InterruptedException e) {
		}
		
		logger.info("Done charging for user " + userId);
	}
	
}
