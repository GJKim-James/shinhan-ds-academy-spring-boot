package com.shinhan.firstzone.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebReplyDTO;
import com.shinhan.firstzone.twoway2.WebReplyService;

@RestController
@RequestMapping("/replies")
public class WebReplyRestController {
	
	@Autowired
	WebReplyService replyService;
	
	// 특정 board의 reply 정보 조회
	@GetMapping("/list/{bno}")
	public List<WebReplyDTO> selectRepliesByBoard(@PathVariable Long bno) {
		WebBoardEntity board = WebBoardEntity.builder().bno(bno).build();
		
		List<WebReplyDTO> dtoList = replyService.selectReplyByBoard(board);
		
		return dtoList;
	}
	
	// 댓글 등록
	@PostMapping(value = "/register", consumes = "application/json")
	public Long insertReply(@RequestBody WebReplyDTO dto) {
		System.out.println("등록할 댓글 내용 : " + dto);
		WebReplyDTO newReplyDTO = replyService.insertReply(dto);
		
		return newReplyDTO.getRno();
	}
	
	// 댓글 수정
	@PutMapping(value = "/update", consumes = "application/json")
	public Long updateReply(@RequestBody WebReplyDTO dto) {
		System.out.println("수정할 댓글 내용 : " + dto);
		WebReplyDTO updatedReplyDTO = replyService.updateReply(dto);
		
		return updatedReplyDTO.getRno();
	}

}
