package com.shinhan.firstzone.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO(Data Transfer Object)
// Browser 요청 DTO -> Controller DTO -> Service Entity -> Repository(DAO) -> DB
// 응답 <- Controller DTO <- Service DTO <- Repository Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookDTO {
	
	private Long gno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regDate;
	private LocalDateTime modDate;

}
