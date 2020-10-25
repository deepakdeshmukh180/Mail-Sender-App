package in.edelweiss.gmail.dao;

public class MailData {

	private String user;
	private String pass;
	private String sender;
	private String filePath;
	private String subject;

	public MailData(String user, String pass, String sender, String filePath, String subject) {
		super();
		this.user = user;
		this.pass = pass;
		this.sender = sender;
		this.filePath = filePath;
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public MailData() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "MailData [user=" + user + ", pass=" + pass + ", sender=" + sender + ", filePath=" + filePath
				+ ", subject=" + subject + "]";
	}

	
	
}
