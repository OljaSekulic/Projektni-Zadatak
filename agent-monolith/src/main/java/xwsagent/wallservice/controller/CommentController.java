package xwsagent.wallservice.controller;

import java.util.ArrayList;
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
import xwsagent.wallservice.dto.CommentDTO;
import xwsagent.wallservice.repository.CommentRepository;
import xwsagent.wallservice.service.CommentService;
import xwsagent.wallservice.service.PostService;

@RestController
@RequestMapping(value = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired 
	PostService postService;
	
	@GetMapping(value = "/all/{id}", produces = "application/json")
	public ResponseEntity<?> allComments(@PathVariable Long id) {
		List<Comment> comments = commentRepository.findAll();
		List<Comment> list = new ArrayList<Comment>();
		for(Comment comment : comments) {
			if(comment.getId() == id) {
				if(!comment.isDeleted())
					list.add(comment);
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add/{id}")
	public ResponseEntity<?> add(@PathVariable("id")Long id, @RequestBody CommentDTO commentDTO) {
		Comment comment = commentService.addComment(commentDTO, id);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody(required = false)  CommentDTO commentDTO, @PathVariable("id") Long id){
		Comment comment = commentService.findById(id);
		Comment c = commentService.update(comment, commentDTO);
			
		return new ResponseEntity<>(c, HttpStatus.OK);
		
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		commentService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
