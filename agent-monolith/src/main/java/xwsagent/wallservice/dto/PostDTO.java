package xwsagent.wallservice.dto;

public class PostDTO {

	private String text;
	private String user;
	
	public PostDTO() {
		
	}

	public PostDTO(String text, String user) {
		super();
		this.text = text;
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
