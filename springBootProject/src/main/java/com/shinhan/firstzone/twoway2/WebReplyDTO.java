package com.shinhan.firstzone.twoway2;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WebReplyDTO {
	
	private Long rno;
	private String replyText; // 댓글 내용
	private Timestamp regdate;
	private Timestamp updatedate;
	
	// WebReplyEntity와 다른 필드
	private String mid;
	private String mname;
	private Long boardBno;

}






