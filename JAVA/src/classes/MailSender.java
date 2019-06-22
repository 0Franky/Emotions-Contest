package classes;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Useful classe to send emails
 * 
 * @author chris
 *
 */
public class MailSender {

	/**
	 * Sends an email to receiver from sender
	 * 
	 * @param sender
	 * @param password
	 * @param reciver
	 * @param oggetto
	 * @param content
	 */
	public static void sendMail(String sender, String password, String receiver, String oggetto, String content) {

		String testo = ""; // Non necessario se si usa il content html in input //

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(oggetto);
			message.setText(testo);
			message.setContent(content, "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	// MAIN TEST //
	public static void main(String[] args) {

		final String sender = "howappyou@gmail.com";
		final String password = "Qwerty#123";

		final String receiver = "c.miccolis3@studenti.uniba.it";

		String oggetto = " Oggetto ";
		String testo = "";

		String content = "<h1> Intestazione H1 </h1>" + "<p> paragrafo 1 </p>" + "<p> paragrafo 2 </p>";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(oggetto);
			message.setText(testo);
			message.setContent(content, "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}