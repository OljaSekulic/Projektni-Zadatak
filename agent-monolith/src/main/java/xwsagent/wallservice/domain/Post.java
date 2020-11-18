package xwsagent.wallservice.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import xwsagent.wallservice.dto.PostDTO;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="text")
	private String text;
	

	@Column(name="user")
	private String user;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "post")
	private Set<Rate> rates;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date postDate;
	
	public Post() {
		
	}
	
	public Post(String text, String user) {
		super();
		this.text = text;
		this.user = user;
	}

	public Post(String text, String user, boolean deleted) {
		super();
		this.text = text;
		this.user = user;
		this.deleted = deleted;
	}
	
	public Post(PostDTO postDTO) {
		super();
		this.text = postDTO.getText();
		this.user = postDTO.getUser();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	
	
	
	
}
