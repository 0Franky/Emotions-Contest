package classes;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Title.Title;

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
	 * @param subject
	 * @param body
	 */
	public static void sendMail(String sender, String password, String receiver, String subject, String body) {

		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {

			message.setFrom(new InternetAddress(sender));
			InternetAddress toAddress = new InternetAddress(receiver);

			message.addRecipient(Message.RecipientType.TO, toAddress);

			message.setSubject(subject);
			// message.setText(body);
			message.setText("");
			message.setContent(body, "text/html");

			Transport transport = session.getTransport("smtp");

			transport.connect(host, sender, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("Done");

		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	// MAIN TEST //
	public static void main(String[] args) {
		MailSender.sendMail(Title.EMAILS_SENDER, Title.PASSWORD_EMAILS_SENDER, "f.scarati2@studenti.uniba.it",
				"00SPID test", "myspid00");
	}
}