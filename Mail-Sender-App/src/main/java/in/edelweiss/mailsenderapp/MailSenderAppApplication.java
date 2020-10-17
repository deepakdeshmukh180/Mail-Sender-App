package in.edelweiss.mailsenderapp;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MailSenderAppApplication {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(MailSenderAppApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MailSenderAppApplication.class, args);
		log.info("MailSenderAppApplication Started");
	}

}
