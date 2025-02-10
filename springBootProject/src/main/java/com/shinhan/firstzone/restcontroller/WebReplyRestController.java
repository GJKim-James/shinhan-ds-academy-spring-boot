package com.shinhan.firstzone.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/replies")
@Tag(name="Reply API") // http://localhost:7777/shinhan/swagger-ui/index.html로 접근하면 Swagger UI 확인 가능
public class WebReplyRestController {
	
	@Autowired
	WebReplyService replyService;
	
	// 특정 board의 reply 정보 조회
	@Operation(summary = "댓글 조회", description = "댓글 조회 시 Board 번호가 필수") // Swagger API
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
	
	// 댓글 삭제
	@DeleteMapping(value = "/delete/{rno}")
	public Long delete(@PathVariable Long rno) {
		replyService.deleteReplyByRno(rno);
		
		return rno;
	}

}
