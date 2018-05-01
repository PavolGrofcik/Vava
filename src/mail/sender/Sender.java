package mail.sender;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Trieda odosiela request na obnovu hesla cez mail
 * @author grofc
 *
 */

public class Sender {
	
	/*private static final Logger LOGGER = Logger.getLogger(Sender.class.getName());		// Not working??
	private static final String PATH = "Logger/senderLogfile.txt";
	*/
	private static final String USERNAME = "skigosr@gmail.com";
	private static final String PASSWORD = "DBadmin1";
	private static final String DEFAULT_ACCOUNT = "grofcik.pavol@gmail.com";

	
	/*private static void setLogger(){
		try {
			FileHandler handler = new FileHandler(PATH);

			InputStream configFile = Sender.class.getResourceAsStream("p.properties");
			//InputStream configFile = new FileInputStream("src/resources/p.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			handler.setFormatter(new SimpleFormatter());
			
			LOGGER.addHandler(handler);
			LOGGER.setUseParentHandlers(false);
			LOGGER.log(Level.INFO, "info");	
			LOGGER.setLevel(Level.INFO);

			configFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.CONFIG, "Log config", e);
		}
		System.out.println("SenderLog level is: " + LOGGER.getLevel());
	}*/
	
	public static void sendGmailMessage(String account, String name,  String passwd) {
	
		 Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", true); 
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.user", USERNAME);
		    props.put("mail.smtp.password", PASSWORD);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", false);


		    Session session = Session.getInstance(props,null);
		    MimeMessage message = new MimeMessage(session);

		   // LOGGER.log(Level.INFO, "Port is " + session.getProperty("mail.smtp.port"));

		    // Create the email addresses involved
		    try {
		        InternetAddress from = new InternetAddress(USERNAME);
		        message.setSubject("New password");	// Name of subject message
		        message.setFrom(from);
		        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(account));	//	To

		       // LOGGER.log(Level.INFO, "Recipient is " + account);
		        
		        // Create a multi-part to combine the parts
		        Multipart multipart = new MimeMultipart("alternative");

		        // Create your text message part
		        BodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart.setText("some text to send");

		        // Add the text part to the multipart
		        multipart.addBodyPart(messageBodyPart);

		        // Create the html part
		        messageBodyPart = new MimeBodyPart();
		        String htmlMessage = "Hello " + name + ", \n \n " + 					//	Text to send
		        		"your password has been recently updated.\n" +
		        		" \n New Pasword is " + passwd + " . \n " + 
		        		"If you have any questions, please send us an email.";
		        messageBodyPart.setContent(htmlMessage, "text/html");


		        // Add html part to multi part
		        multipart.addBodyPart(messageBodyPart);

		        // Associate multi-part with message
		        message.setContent(multipart);

		        // Send message
		        Transport transport = session.getTransport("smtp");
		       //LOGGER.log(Level.INFO, "Transport to is " + transport.toString());
		        
		        transport.connect("smtp.gmail.com", USERNAME , PASSWORD);
		        transport.sendMessage(message, message.getAllRecipients());


		    } catch (AddressException e) {
		       // LOGGER.log(Level.SEVERE, "Addres Exception", e);
		        
		    } catch (MessagingException e) {
		    	//LOGGER.log(Level.SEVERE, "Message Exception", e);
		    }
	}
	}
