package com.shinhan.firstzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.twoway2.WebBoardDTO;
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/webboard")
public class WebBoardController {
	
	@Autowired
	WebBoardService boardService;
	
	// tbl_webboards 목록 조회
	@GetMapping("/list")
	public void selectBoardAll(Model model) {
		model.addAttribute("boardlist", boardService.selectBoardAll());
	}
	
	// tbl_webboards 특정 게시글 상세보기(@RequestParam 이용)
	@GetMapping("/detail")
	public void selectBoardById(@RequestParam Long bno, Model model) {
		model.addAttribute("board", detail(bno));
	}
	
	// tbl_webboards 특정 게시글 상세보기(@PathVariable 이용)
	@GetMapping("/detail2/{bno}")
	public String selectBoardById2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", detail(bno));
		
		return "webboard/detail";
	}
	
	// Entity를 DTO로 변환하는 함수
	private WebBoardDTO detail(Long bno) {
		WebBoardEntity entity = boardService.selectBoardById(bno);
		
		// Entity를 DTO로 변환
		WebBoardDTO dto = boardService.entityToDTO(entity);
		
		return dto;
	}
	
	// tbl_webboards 수정
	@PostMapping("/update")
	public String updateBoard(WebBoardDTO board) {
		// DTO를 Entity로 변환
		WebBoardEntity entity = boardService.dtoToEntity(board);
		
		boardService.updateBoard(entity);
		
		return "redirect:list";
	}
	
	// tbl_webboards에 등록하는 화면
	@GetMapping("/register")
	public void insertPage() {
		
	}
	
	// tbl_webboards에 등록
	@PostMapping("/register")
	public String insertBoard(WebBoardDTO board, HttpSession session) {
//		MemberEntity member = (MemberEntity) session.getAttribute("loginMember");
		
		String mid = "gjkim1"; // 나중에 login member의 id로 변경 예정
		board.setMid(mid);
		
		WebBoardEntity entity = boardService.dtoToEntity(board);
		
		boardService.insertBoard(entity);
		
		return "redirect:list";
	}

}
