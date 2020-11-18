package xwsagent.wallservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Rate {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column
	 private Integer rate;
	 
	 @Column
	 private String userName;
	 
	 @Column(name="post")
	 private Long post;
	 
	 public Rate() {
		 
	 }
	 
	 public Rate(Long id, Integer rate, String userName, Long post) {
		 this.id = id;
		 this.rate = rate;
		 this.userName = userName;
		 this.post = post;
	 }

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

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}
	 
	 
}
