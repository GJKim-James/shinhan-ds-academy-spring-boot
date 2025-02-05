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
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardService;

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
		model.addAttribute("board", boardService.selectBoardById(bno));
	}
	
	// tbl_webboards 특정 게시글 상세보기(@PathVariable 이용)
	@GetMapping("/detail2/{bno}")
	public String selectBoardById2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", boardService.selectBoardById(bno));
		
		return "webboard/detail";
	}
	
	// tbl_webboards 수정
	@PostMapping("/update")
	public String updateBoard(WebBoardEntity board, String mid) {
		MemberEntity member = MemberEntity.builder().mid(mid).build();
		board.setWriter(member);
		
		log.info(board.toString());
		boardService.updateBoard(board);
		
		return "redirect:list";
	}

}
