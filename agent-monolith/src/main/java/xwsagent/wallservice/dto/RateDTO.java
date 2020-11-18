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
}
