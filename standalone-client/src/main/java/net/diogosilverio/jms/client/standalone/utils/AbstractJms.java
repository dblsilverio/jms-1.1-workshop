package net.diogosilverio.jms.client.standalone.utils;

import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Manages a few jms/resources operations.
 * 
 * @author diogo
 *
 */
public abstract class AbstractJms implements AutoCloseable {

	protected InitialContext namingContext;
	protected ConnectionFactory connectionFactory;
	protected Destination destination;
	protected Destination responseDestination;
	protected Connection connection;
	protected Session session;

	public AbstractJms(String destinationJndi, Boolean responseQueue) {
		init(destinationJndi, responseQueue);
	}
	
	protected ObjectMessage createObjectMessage(Serializable body) throws JMSException{
		return session.createObjectMessage(body);
	}
	
	protected TextMessage createTextMessage(String body) throws JMSException{
		TextMessage textMessage = session.createTextMessage(body);
		
		if(responseDestination != null){
			textMessage.setJMSReplyTo(responseDestination);
		}
		
		return textMessage;
	}
	
	protected MessageConsumer createConsumer() throws JMSException{
		if(responseDestination == null){
			throw new InvalidDestinationException("No reply to destination provided");
		}
		
		MessageConsumer consumer = session.createConsumer(responseDestination);
		connection.start();
		
		return consumer;
	}
	
	protected void browse() throws JMSException {
		
		final QueueBrowser browser = session.createBrowser((Queue) destination);
		
		@SuppressWarnings("unchecked")
		Enumeration<ObjectMessage> messages = browser.getEnumeration();
		
		if(messages.hasMoreElements()){
			System.out.println("Messages found: ");
			
			ObjectMessage message = null;
			while(messages.hasMoreElements()){
				message = messages.nextElement();
				System.out.println("- " + message.getObject());
			}
			
		}
		
	}	
	
	protected void sendMessage(Message message) throws JMSException{
		sendBulkMessage(message);
		System.out.println("Message sent");
	}
	
	protected void sendBulkMessage(Message... messages) throws JMSException{
		
		try{
			for(Message message : messages){
				session.createProducer(destination).send(message);
			}
			
			System.out.println("Messages sent");
			session.commit();
		} catch(Exception e){
			session.rollback();
		}
	}

	private void init(String destinationJndi, Boolean responseQueue)  {
		
		try{ 
			namingContext = new InitialContext();
	
			connectionFactory = (ConnectionFactory) namingContext.lookup("java:jms/RemoteConnectionFactory");
			destination = (Destination) namingContext.lookup(destinationJndi);
	
			connection = connectionFactory.createConnection();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			if(responseQueue){
				responseDestination = session.createTemporaryQueue();
			}
			
		}
		catch(Exception e){
			System.out.println("Error initializing abstract jms client: " + e.getMessage());
		}
		
	}
	
	@Override
	public void close() throws Exception {
		System.out.println("Closing resources...");
		session.close();
		connection.close();
		namingContext.close();
		System.out.println("Done.");
	}

}
