package net.diogosilverio.jms.client.standalone.async;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import net.diogosilverio.jms.client.standalone.utils.AbstractJms;
import net.diogosilverio.jms.model.Metrics;

/**
 * Produces ObjectMessages carrying metrics for logging.
 * @author diogo
 *
 */
public class GenericTopicPublisher extends AbstractJms {
	
	public static final String TOPIC_DESTINATION = "java:jms/topic/metrics";
	public static final Integer BATCH_SIZE = 100;
	
	public GenericTopicPublisher() {
		super(TOPIC_DESTINATION, Boolean.FALSE);
	}

	public static void main(String[] args) {
		
		try(GenericTopicPublisher gTopicPublisher = new GenericTopicPublisher()){
			
			Message[] messages = gTopicPublisher.buildMessageBulk();
			gTopicPublisher.sendBulkMessage(messages);
		} catch(Exception e){
			System.out.println("Error producing metrics messages: " + e.getMessage());e.printStackTrace();
		}
		
	}

	private ObjectMessage[] buildMessageBulk() {
		
		return IntStream
				.range(0, BATCH_SIZE)
					.mapToObj(i -> {
						try {
							return setType(createObjectMessage(new Metrics()));
						} catch (JMSException e) {
							return null;
						}
					})
					.collect(Collectors.toList()).toArray(new ObjectMessage[]{});
	}
	
	private static ObjectMessage setType(ObjectMessage message) throws JMSException {

		String type = "NORMAL";
		
		if(new Random().nextInt(100) < 5) type = "CRITICAL"; 
		message.setStringProperty("level", type);
		
		return message;
	}
	
}