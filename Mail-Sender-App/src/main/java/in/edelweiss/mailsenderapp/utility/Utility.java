package in.edelweiss.mailsenderapp.utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import in.edelweiss.mailsenderapp.service.MailSenderServiceImpl;

public class Utility {

	@Autowired
	static Environment evn;
	
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	public static Properties getMailProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		return props;

	}

	public static String getMailBody() {
		File msgBodyFile = null;
		log.info("--<<Getting Mail Body form classpath:msgbody.txt ");
		try {
			msgBodyFile = ResourceUtils.getFile("classpath:msgbody.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader = null;
		String msgBodyString = null;
		try {
			reader = new BufferedReader(new FileReader(msgBodyFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			msgBodyString = stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("--<<Returing  Mail Body Text");
		return msgBodyString;
	}

	/*public  File getAttachmentFile(String fileContain) {
		File txtFile = new File("C:/Users/Manya/Desktop/Files/"+fileContain+"/"+"attachment.txt");
		txtFile.mkdir()
		log.info("--<<Creating  Attachment File for "+fileContain);
		try(BufferedWriter output = new BufferedWriter(new FileWriter(txtFile))) {
			output.write(fileContain);
			log.info("--<< Attachment File writing :"+txtFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		log.info("--<<Returing  Attachment File  :"+fileContain);
		return txtFile;
	}*/
	
	
	public  String getAttachFile(String fileContent) throws IOException {
		File theDir = new File("D:/files/"+fileContent);
		if (!theDir.exists()){
		    theDir.mkdirs();
		    log.info("--<< Make File Directory :"+theDir);
		}
		String path = "D:/files/"+fileContent+"/"+"attachment.txt";
	    FileOutputStream outputStream = new FileOutputStream(path);
	    DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(outputStream));
	    dataOutStream.writeUTF(fileContent);
	    dataOutStream.close();
		return path;
	}

}
