package net.diogosilverio.jms.client.standalone.sync;

import javax.jms.Message;
import javax.jms.TextMessage;

import net.diogosilverio.jms.client.standalone.utils.AbstractJms;

/**
 * Sends a request and expect a response through a temporary queue.
 * 
 * @author diogo
 *
 */
public class SyncProducer extends AbstractJms {
	
	public static final String QUEUE_DESTINATION = "java:jms/queue/sync";

	private static final long RESPONSE_TIMEOUT = 10000;
	
	public SyncProducer() throws Exception {
		super(QUEUE_DESTINATION, Boolean.TRUE);
	}
	
	public static void main(String[] args) {
				
		try(SyncProducer syncProducer = new SyncProducer()){
			syncProducer.sendMessage(syncProducer.createTextMessage("John Doe"));
			syncProducer.listen();
		} catch (Exception e) {
			System.out.println("Error processing message: " + e.getMessage());
		}
		
		System.out.println("Bye :)");
	}

	private void listen() throws Exception {
		
		System.out.println("Listening for a response...");
		
		final Message response = createConsumer().receive(RESPONSE_TIMEOUT);
		
		if(response != null){
			System.out.println("Response received: " + ((TextMessage)response).getText());
		} else {
			System.out.println("Timeout :(");
		}
		
	}

}
