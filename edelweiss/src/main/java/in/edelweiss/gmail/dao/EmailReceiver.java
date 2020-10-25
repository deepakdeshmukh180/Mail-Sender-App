package in.edelweiss.gmail.dao;

public class EmailReceiver {

	String serialId;
	String emailId;

	public EmailReceiver() {
		super();
	}

	public EmailReceiver(String serialId, String emailId) {
		super();
		this.serialId = serialId;
		this.emailId = emailId;
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

}
