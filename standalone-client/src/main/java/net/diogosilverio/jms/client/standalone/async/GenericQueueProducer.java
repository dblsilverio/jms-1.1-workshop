package net.diogosilverio.jms.client.standalone.async;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import net.diogosilverio.jms.client.standalone.utils.AbstractJms;
import net.diogosilverio.jms.client.standalone.utils.Generator;

/**
 * Produces ObjectMessages carrying carts for checkout.
 * @author diogo
 *
 */
public class GenericQueueProducer extends AbstractJms {

	public static final String QUEUE_DESTINATION = "java:jms/queue/checkout";
	public static final Integer BATCH_SIZE = 10;
	
	public GenericQueueProducer() {
		super(QUEUE_DESTINATION, Boolean.FALSE);
	}

	public static void main(String[] args) {
		
		try(GenericQueueProducer gQueueProducer = new GenericQueueProducer()){
			
			Message[] messages = gQueueProducer.buildMessageBulk();
			gQueueProducer.sendBulkMessage(messages);
			
		} catch (Exception e) {
			System.out.println("Error producing carts messages: " + e.getMessage());e.printStackTrace();
		}
		
	}

	private ObjectMessage[] buildMessageBulk() {
		
		return IntStream
				.range(0, BATCH_SIZE)
					.mapToObj(i -> {
						try {
							return setType(createObjectMessage(Generator.generateCart()));
						} catch (JMSException e) {
							return null;
						}
					})
					.collect(Collectors.toList()).toArray(new ObjectMessage[]{});
	}
	
	private static ObjectMessage setType(ObjectMessage message) throws JMSException {

		String type = "NORMAL";
		
		if(new Random().nextInt(100) < 20) type = "PREMIUM"; 
		message.setStringProperty("type", type);
		
		return message;
	}

}