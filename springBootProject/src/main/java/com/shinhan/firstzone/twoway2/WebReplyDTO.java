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
	private String reply; // 댓글내용
	private String mid;
	private String mname;
	private Timestamp regdate;
	private Timestamp updatedate;
	private Long boardBno;

}






