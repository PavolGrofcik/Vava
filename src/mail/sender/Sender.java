package mail.sender;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
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
	
	private static final String USERNAME = "skigosr@gmail.com";
	private static final String PASSWORD = "DBadmin1";
	private static final String DEFAULT_ACCOUNT = "grofcik.pavol@gmail.com";
	private static final String SUBJECT = "New registration in SkiGoSR";

	
	public static void sendGmailMessage(String account, String name, String passwd) {

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", USERNAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", false);

		Session session = Session.getInstance(props, null);
		MimeMessage message = new MimeMessage(session);

		try {
			InternetAddress from = new InternetAddress(USERNAME);
			message.setSubject("New password"); // Name of subject message
			message.setFrom(from);
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(account)); // To


			Multipart multipart = new MimeMultipart("alternative");

			// Create your text message part
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("some text to send");

			// Add the text part to the multipart
			multipart.addBodyPart(messageBodyPart);

			// Create the html part
			messageBodyPart = new MimeBodyPart();
			String htmlMessage = "Hello " + name + ", \n \n " + // Text to send
					"your password has been recently updated.\n" + " \n New Pasword is " + passwd + " . \n "
					+ "If you have any questions, please send us an email.";
			messageBodyPart.setContent(htmlMessage, "text/html");

			// Add html part to multi part
			multipart.addBodyPart(messageBodyPart);

			// Associate multi-part with message
			message.setContent(multipart);

			// Send message
			Transport transport = session.getTransport("smtp");
			// LOGGER.log(Level.INFO, "Transport to is " + transport.toString());

			transport.connect("smtp.gmail.com", USERNAME, PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	// Odoslanie QR kódu do mailu pri registrácii
	public static void generateAndSendEmail(String toEmail) {
		String body = "\"Greetings, <br><br>Your registration email with QR code. Please find here attached Image.\"\n"
				+ "<br><br> Regards, <br>SkiGoSR Team";
       
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
 
        System.out
                .println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");
 
        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        Session session1 = Session.getInstance(props, authentication);

		
        try {
            System.out.println("\n3rd ===> generateAndSendEmail() starts..");
 
            MimeMessage crunchifyMessage = new MimeMessage(session1);
            crunchifyMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            crunchifyMessage.addHeader("format", "flowed");
            crunchifyMessage.addHeader("Content-Transfer-Encoding", "8bit");
 
            crunchifyMessage.setFrom(new InternetAddress(toEmail,
                    "SkiGoSR"));
            crunchifyMessage.setReplyTo(InternetAddress.parse(toEmail, false));
            crunchifyMessage.setSubject(SUBJECT, "UTF-8");
            crunchifyMessage.setSentDate(new Date());
            crunchifyMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail, false));
 
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");
 
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
 
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
 
            messageBodyPart = new MimeBodyPart();
       
            String filename = "./Images/QRCode.png";
            
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            // Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);
 
            System.out.println("\n4th ===> third part for displaying image in the email body..");
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<br><h3>Find below attached image</h3>"
                    + "<img src='cid:image_id'>", "text/html");
            
            multipart.addBodyPart(messageBodyPart);
            crunchifyMessage.setContent(multipart);
 
            System.out.println("\n5th ===> Finally Send message..");
 
            // Finally Send message
            Transport.send(crunchifyMessage);
 
            System.out
                    .println("\n6th ===> Email Sent Successfully With Image Attachment. Check your email now..");
            System.out.println("\n7th ===> generateAndSendEmail() ends..");
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	
}
