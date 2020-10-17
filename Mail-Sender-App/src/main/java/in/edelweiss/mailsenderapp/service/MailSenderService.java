package in.edelweiss.mailsenderapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import in.edelweiss.mailsenderapp.dao.EmailReceiver;
import in.edelweiss.mailsenderapp.dao.EmailSender;

public interface MailSenderService  {

	void sendEmailToMultipleUser(EmailSender emailSender, List<EmailReceiver> receiverList, Map<String, String> allAttachement) throws AddressException, MessagingException, IOException;

}
