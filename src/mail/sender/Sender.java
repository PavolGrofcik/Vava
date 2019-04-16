package mail.sender;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
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
 * Trieda odosiela request na obnovu hesla cez mail,
 * zároveň odošle QR kód pri registrácii nového zákazníka
 * @author grofc
 *
 */

public class Sender {
	
	private static final String USERNAME = "skigosr@gmail.com";
	private static final String PASSWORD = "DBadmin1";
	private static final String DEFAULT_ACCOUNT = "grofcik.pavol@gmail.com";
	private static final String SUBJECT = "New registration in SkiGoSR";

	// Odoslanie nového hesla používateľovi
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

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("New password");

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String htmlMessage = "Hello " + name + ", \n \n " + // Text to send
					"your password has been recently updated.\n" + " \n New Pasword is " + passwd + " . \n "
					+ "If you have any questions, please send us an email.";
			messageBodyPart.setContent(htmlMessage, "text/html");

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");

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
 
        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        Session session1 = Session.getInstance(props, authentication);

        try {
            MimeMessage message = new MimeMessage(session1);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
 
            message.setFrom(new InternetAddress(toEmail,
                    "SkiGoSR"));
            message.setReplyTo(InternetAddress.parse(toEmail, false));
            message.setSubject(SUBJECT, "UTF-8");
            message.setSentDate(new Date());
            message.setRecipients(Message.RecipientType.TO,
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
  
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);
 
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<br><h3>Find below attached image</h3>"
                    + "<img src='cid:image_id'>", "text/html");
            
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
 
            // Finally Send message
            Transport.send(message);

 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	
}
