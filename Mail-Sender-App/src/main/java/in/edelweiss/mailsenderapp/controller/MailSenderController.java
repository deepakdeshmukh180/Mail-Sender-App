package in.edelweiss.mailsenderapp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.edelweiss.mailsenderapp.dao.EmailReceiver;
import in.edelweiss.mailsenderapp.dao.EmailSender;
import in.edelweiss.mailsenderapp.service.MailSenderService;
import in.edelweiss.mailsenderapp.utility.Utility;

@RestController
public class MailSenderController {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(MailSenderController.class);

	@Autowired
	MailSenderService mailSenderService;

	@Autowired
	Environment evn;

	@RequestMapping(value = "/sendemail")
	public String sendEmail() {
		log.info("-->> MailSender Start Started");
		List<EmailReceiver> receiverList = getEmailReceiverList();
		List<EmailSender> senderList = getEmailSenderList();
		Map<String, String> allAttachement = loadAllAttachement(senderList, receiverList);
		senderList.parallelStream().forEach(sender -> {
			try {
				mailSenderService.sendEmailToMultipleUser(sender, receiverList, allAttachement);
			} catch (MessagingException | IOException e1) {
				e1.printStackTrace();
			}
		});
		return "All mails published successfully";
	}

	private List<EmailSender> getEmailSenderList() {
		List<EmailSender> emailSenderList = new LinkedList<EmailSender>();
		List<String> senderListStr = Arrays.asList(evn.getProperty("edelweiss.sender").split("&&"));
		senderListStr.stream().forEach(sender -> {
			List<String> senderStr = Arrays.asList(sender.split(","));
			log.info("-->>Getting Sender from Properties File : " + senderStr.get(1));
			emailSenderList.add(new EmailSender(senderStr.get(0), senderStr.get(1), senderStr.get(2)));
		});
		return emailSenderList;
	}

	private List<EmailReceiver> getEmailReceiverList() {
		List<EmailReceiver> emailReceiverList = new LinkedList<EmailReceiver>();
		List<String> receiverListStr = Arrays.asList(evn.getProperty("edelweiss.receiver").split("&&"));
		receiverListStr.stream().forEach(receiver -> {
			List<String> reciverStr = Arrays.asList(receiver.split(","));
			log.info("-->>Getting Receiver from Properties File : " + reciverStr.get(1));
			emailReceiverList.add(new EmailReceiver(reciverStr.get(0), reciverStr.get(1)));
		});
		return emailReceiverList;
	}

	private Map<String, String> loadAllAttachement(List<EmailSender> emailSenders, List<EmailReceiver> emailReceivers) {
		Map<String, String> map = new HashMap<>();
		for (EmailSender emailSender : emailSenders) {
			for (EmailReceiver emailReceiver : emailReceivers) {
				String file = null;
				try {
					file = new Utility()
							.getAttachFile(emailSender.getSerialId() + " DEEPAK-DESHMUKH " + emailReceiver.getSerialId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				map.put(emailSender.getSerialId() + emailReceiver.getSerialId(), file);
			}
		}
		return map;

	}

}
