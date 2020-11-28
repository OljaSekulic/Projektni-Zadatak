package xwsagent.wallservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDTO {
	private Long id;
	private String content;
	private Date date;
	private String userName;
	private Long userId;
	private Long postId;
	private boolean deleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public CommentDTO(Long id, String content, Date date, String userName, Long userId, Long postId, boolean deleted) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
		this.userName = userName;
		this.userId = userId;
		this.postId = postId;
		this.deleted = deleted;
	}
	public CommentDTO() {
	}
	
	
}
