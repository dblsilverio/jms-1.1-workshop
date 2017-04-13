package net.diogosilverio.jms.client.standalone.browser;

import net.diogosilverio.jms.client.standalone.utils.AbstractJms;

/**
 * Browses checkout queue for pending messages.
 * 
 * @author diogo
 *
 */
public class CheckoutBrowser extends AbstractJms {

	public static final String QUEUE_DESTINATION = "java:jms/queue/checkout";
	
	public CheckoutBrowser() {
		super(QUEUE_DESTINATION, Boolean.FALSE);
	}
	
	public static void main(String[] args) throws Exception {
		
		try(CheckoutBrowser checkoutBrowser = new CheckoutBrowser()){
			checkoutBrowser.browse();
		} catch(Exception e){
			System.out.println("Error browsing queue: " + e.getMessage());e.printStackTrace();
		}
		
	}

}