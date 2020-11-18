package xwsagent.wallservice.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wallservice.domain.Comment;
import xwsagent.wallservice.domain.Post;
import xwsagent.wallservice.dto.CommentDTO;
import xwsagent.wallservice.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostService postService;
	
	public Comment addComment(CommentDTO dto, Long id) {
		Post post = postService.findById(id);
		Comment comment = new Comment();
		comment.setContent(dto.getContent());
		comment.setDeleted(false);
		comment.setUserName(dto.getUserName());
		comment.setPost(post.getId());
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		comment.setCommentDate(date);
		commentRepository.save(comment);
		return comment;
	}
	
	public Comment findById(Long id){
		Comment c = commentRepository.findById(id).orElseGet(null);
		return c;
	}
	
	public Comment update(Comment c, CommentDTO commentDTO) {

		c.setContent(commentDTO.getContent());
		this.commentRepository.save(c);
		return c;
		
		
	}
	
	public void delete(Long id) {
		Comment comment = findById(id);
		comment.setDeleted(true);
		commentRepository.save(comment);
			
	}
}
