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

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.twoway2.WebBoardDTO;
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping("/api/webboard")
public class WebBoardRestController {
	
	@Autowired
	WebBoardService boardService;
	
	// tbl_webboards 목록 조회
	@GetMapping("/list")
	public List<WebBoardDTO> selectBoardAll() {
		return boardService.selectBoardAll();
	}
	
	// 페이징이 포함된 tbl_webboards 목록 조회
	@GetMapping("/list2")
	public PageResultDTO<WebBoardDTO, WebBoardEntity> selectBoardAllPaging(@RequestBody PageRequestDTO pageRequestDTO) {
		System.out.println("[pageRequestDTO] : " + pageRequestDTO); // PageRequestDTO(page=0, size=0, type=null, keyword=null)
		
		// 처음 조회했을 경우
		if (pageRequestDTO.getPage() == 0) {
			pageRequestDTO = new PageRequestDTO(1, 10);
		}
		
		return boardService.selectBoardAllPaging(pageRequestDTO);
	}
	
	// tbl_webboards 특정 게시글 상세보기(@PathVariable 이용)
	@GetMapping(value = "/detail/{bno}", produces = "application/json")
	public WebBoardDTO selectBoardById(@PathVariable Long bno) {
		WebBoardEntity entity = boardService.selectBoardById(bno);
		
		// Entity를 DTO로 변환
		WebBoardDTO dto = boardService.entityToDTO(entity);
		
		return dto;
	}
	
	// tbl_webboards 수정
	@PutMapping("/update")
	public String updateBoard(@RequestBody WebBoardDTO board) {
		// DTO를 Entity로 변환
		WebBoardEntity entity = boardService.dtoToEntity(board);
		
		boardService.updateBoard(entity);
		
		return "게시글 수정 성공!";
	}
	
	// tbl_webboards에 등록
	@PostMapping("/register")
	public String insertBoard(@RequestBody WebBoardDTO board) {
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.insertBoard(entity);
		
		return "게시글 등록 성공!";
	}
	
	// 게시글 삭제
	@DeleteMapping("/delete/{bno}")
	public String deleteBoard(@PathVariable Long bno) {
		boardService.deleteBoardByBno(bno);
		
		return "게시글 삭제 성공!";
	}

}
