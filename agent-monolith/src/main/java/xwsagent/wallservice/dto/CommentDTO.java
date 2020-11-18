package xwsagent.wallservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommentDTO {
	private Long id;
	private String content;
	private Date date;
	private String userName;
	private Long userId;
	private Long postId;
	private boolean deleted;
}
