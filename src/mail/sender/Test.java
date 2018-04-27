package mail.sender;
import java.util.Properties;


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

public class Test {
	
	private static final String USERNAME = "skigosr@gmail.com";		//Prerobi� na char!(Security)
	private static final String PASSWORD = "DBadmin1";
	
	public static void main(String[] args) {
		  Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", true); 
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.user", USERNAME);
		    props.put("mail.smtp.password", PASSWORD);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", false);



		    Session session = Session.getInstance(props,null);
		    MimeMessage message = new MimeMessage(session);

		    System.out.println("Port: "+session.getProperty("mail.smtp.port"));

		    // Create the email addresses involved
		    try {
		        InternetAddress from = new InternetAddress(USERNAME);
		        message.setSubject("Test");	// Name of subject message
		        message.setFrom(from);
		        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("grofcik.pavol@gmail.com"));	//To

		        // Create a multi-part to combine the parts
		        Multipart multipart = new MimeMultipart("alternative");

		        // Create your text message part
		        BodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart.setText("some text to send");

		        // Add the text part to the multipart
		        multipart.addBodyPart(messageBodyPart);

		        // Create the html part
		        messageBodyPart = new MimeBodyPart();
		        String htmlMessage = "Hello World!";		//Text to send
		        messageBodyPart.setContent(htmlMessage, "text/html");


		        // Add html part to multi part
		        multipart.addBodyPart(messageBodyPart);

		        // Associate multi-part with message
		        message.setContent(multipart);

		        // Send message
		        Transport transport = session.getTransport("smtp");
		        transport.connect("smtp.gmail.com",USERNAME , PASSWORD);
		        System.out.println("Transport: "+transport.toString());
		        transport.sendMessage(message, message.getAllRecipients());


		    } catch (AddressException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (MessagingException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
	}