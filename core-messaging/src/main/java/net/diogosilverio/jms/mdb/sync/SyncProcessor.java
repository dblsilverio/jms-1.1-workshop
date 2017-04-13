package net.diogosilverio.jms.mdb.sync;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.InvalidDestinationException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Handles sync request/response messages.
 * 
 * @author diogo
 *
 */
@MessageDriven(
		name="SyncMDB",
		activationConfig={
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jboss/exported/jms/queue/sync"),
				@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "300")
		}
)
public class SyncProcessor implements MessageListener {

	@Resource(lookup = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Override
	public void onMessage(Message message) {
		
		try{
			
			TextMessage textMessage = (TextMessage) message;
			
			System.out.println("Received a message from " + textMessage.getText() + "!");
			
			Thread.sleep(5000);
			sayHi(textMessage);
			
		} catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			throw new RuntimeException(e);
		}
		
	}

	private void sayHi(TextMessage textMessage) throws Exception {
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		try{
			MessageProducer producer = session.createProducer(textMessage.getJMSReplyTo());
			producer.send(session.createTextMessage("Hello, " + textMessage.getText() + "!"));
			session.commit();
			session.close();
			connection.close();
		} catch(InvalidDestinationException e){
			System.out.println("Destionation is gone: " + e.getMessage());
		}
		
	}

}
