package in.edelweiss.mailsenderapp.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import in.edelweiss.mailsenderapp.dao.EmailReceiver;
import in.edelweiss.mailsenderapp.dao.EmailSender;
import in.edelweiss.mailsenderapp.utility.Utility;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Autowired
	Environment evn;

	@Override
	public void sendEmailToMultipleUser(EmailSender emailSender, List<EmailReceiver> receiverList,Map<String ,String> attchement)
			throws AddressException, MessagingException, IOException {
		log.info("--<<Getting Sender Object  :" + emailSender.toString());
		String msgbody = Utility.getMailBody();
		Session session = Session.getInstance(Utility.getMailProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				log.info("--<<Getting Session for : " + emailSender.getEmailId()+"-"+emailSender.getPassword());
				return new PasswordAuthentication(emailSender.getEmailId().trim(), emailSender.getPassword().trim());
			}
		});
		receiverList.parallelStream().forEach(emailReceiver ->{
			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress(emailReceiver.getEmailId(), false));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver.getEmailId()));
				msg.setSubject("Edelweiss : DEEPAK DESHMUKH");
				msg.setContent("Edelweiss", "text/html");
				msg.setSentDate(new Date());
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(msgbody, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				MimeBodyPart attachPart = new MimeBodyPart();
				log.info("--<<Attaching the attchement  :" + emailReceiver.getEmailId());
				attachPart.attachFile(attchement.get(emailSender.getSerialId()+emailReceiver.getSerialId()));
				log.info("--<<Attached the attchement  "+attchement.get(emailSender.getSerialId()+emailReceiver.getSerialId()));
				multipart.addBodyPart(attachPart);
				msg.setContent(multipart);
				
				Transport.send(msg);
				log.info("Email sent  sucessfully from [ " + emailSender.getEmailId() + " ]  to  [ "+emailReceiver.getEmailId() + " ]");

			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}

		});
	}

}
