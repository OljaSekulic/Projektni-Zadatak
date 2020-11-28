package xwsagent.wallservice.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wallservice.domain.Comment;
import xwsagent.wallservice.domain.Post;
import xwsagent.wallservice.domain.User;
import xwsagent.wallservice.dto.PostDTO;
import xwsagent.wallservice.repository.CommentRepository;
import xwsagent.wallservice.repository.PostRepository;
import xwsagent.wallservice.service.PostService;
import xwsagent.wallservice.service.UserService;

@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;

	@PostMapping(value = "/add", consumes = "application/json")
	public ResponseEntity<Post> addPost(@RequestBody PostDTO post){
		Post p = postService.addPost(post);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all", produces = "application/json")
	public ResponseEntity<?> allPosts() {
		List<Post> posts = postService.getAll();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody PostDTO postDTO, @PathVariable("id") Long id){
		Post post = postService.findById(id);
		if(postService.update(post, postDTO) == null) {
			return new ResponseEntity<>(postService.update(post, postDTO), HttpStatus.FORBIDDEN);
		}else {
			return new ResponseEntity<>(postService.update(post, postDTO), HttpStatus.OK);
		}
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		postService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/newPosts/{id}", produces = "application/json")
	public ResponseEntity<?> newPosts(@PathVariable("id") Long id) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		List<Post> newPosts = new ArrayList<Post>();
		List<Post> list = postRepository.findAll();
		
		for(Post p : list) {
			if((date.getMinutes() > p.getPostDate().getMinutes()) && ( p.getPostDate().getMinutes() > date.getMinutes() - 3)) {
				newPosts.add(p);
			}
		}
		
		return new ResponseEntity<>(newPosts, HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/newComments/{id}", produces = "application/json")
	public ResponseEntity<?> newComments(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		List<Comment> comments = new ArrayList<Comment>();
		List<Comment> newComments = new ArrayList<Comment>();
		List<Comment> list = commentRepository.findAll();
		for(Comment c: list) {
			if(!(c.getUserName().equals(user.getUsername()))) {
				comments.add(c);
			}
		}
		for(Comment c : comments) {
			if((date.getMinutes() > c.getCommentDate().getMinutes()) && ( c.getCommentDate().getMinutes() > date.getMinutes() - 3)) {
				newComments.add(c);
			}
		}
		
		return new ResponseEntity<>(newComments, HttpStatus.OK);
	}
	
	
}
