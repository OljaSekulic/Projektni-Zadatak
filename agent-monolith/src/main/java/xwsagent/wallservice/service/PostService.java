package xwsagent.wallservice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wallservice.domain.Comment;
import xwsagent.wallservice.domain.Post;
import xwsagent.wallservice.dto.PostDTO;
import xwsagent.wallservice.repository.CommentRepository;
import xwsagent.wallservice.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired 
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	public Post addPost(PostDTO post) {
		Post p = new Post(post.getText(), post.getUser());
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		p.setPostDate(date);
		p.setDeleted(false);
		postRepository.save(p);
		return p;
	}
	
	public Post findById(Long id){
		Post p = postRepository.findById(id).orElseGet(null);
		return p;
	}
	
	public Post update(Post p, PostDTO postDTO) {

		p.setText(postDTO.getText());
		p.setDeleted(false);
		if(p.getUser().equals(postDTO.getUser())){
			this.postRepository.save(p);
			return p;
		}else {
			return null;
		}
		
	}
	
	public void delete(Long id) {
		Post post = findById(id);
		post.setDeleted(true);
		List<Comment> comments = commentRepository.findAll();
		for(Comment c : comments) {
			if(c.getPost() == id) {
				c.setDeleted(true);
			}
		}
		postRepository.save(post);
			
	}

}
