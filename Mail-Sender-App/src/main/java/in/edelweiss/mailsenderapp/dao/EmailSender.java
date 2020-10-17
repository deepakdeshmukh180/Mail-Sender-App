package in.edelweiss.mailsenderapp.dao;

public class EmailSender {

	String serialId;
	String emailId;
	String password;

	public EmailSender() {
		super();
	}

	public EmailSender(String serialId, String emailId, String password) {
		super();
		this.serialId = serialId;
		this.emailId = emailId;
		this.password = password;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "EmailSender [serialId=" + serialId + ", emailId=" + emailId + ", password=" + password + "]";
	}
	
	

}
