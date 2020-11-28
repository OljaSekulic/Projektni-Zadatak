package xwsagent.wallservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RateDTO {
	private Long id;
	private Integer rate;
	private String userName;
	private Long userId;
	private Long postId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
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
	public RateDTO(Long id, Integer rate, String userName, Long userId, Long postId) {
		super();
		this.id = id;
		this.rate = rate;
		this.userName = userName;
		this.userId = userId;
		this.postId = postId;
	}
	public RateDTO() {
	}
	
	
}
