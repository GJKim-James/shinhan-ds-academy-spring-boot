package com.shinhan.firstzone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.twoway.FreeBoardEntity;
import com.shinhan.firstzone.twoway.FreeBoardRepository;

import jakarta.servlet.http.HttpSession;

//@RestController
@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@GetMapping("/list")
	public String boardList(Model model, HttpSession session) {
		model.addAttribute("boardlist", boardRepo.findAll());
		
		MemberEntity member = memberRepo.findById("gjkim10").orElse(null);
		session.setAttribute("loginUser", member);
		
//		return boardRepo.findAll(); // @RestController
		return "freeboard/boardlist";
	}
	
	// 상세보기(@PathVariable 이용하기)
	@GetMapping("/detail/{bno}")
	public String boardDetail(@PathVariable Long bno, Model model) {
		Optional<FreeBoardEntity> optionalBoard = boardRepo.findById(bno);
		FreeBoardEntity board = optionalBoard.orElse(null);
		
		model.addAttribute("board", board);
		
		return "freeboard/boarddetail";
	}
	
	// 상세보기(@RequestParam 이용하기; @RequestParam 생략 가능)
	@GetMapping("/detail2")
	public String boardDetail2(Long bno, Model model) {
		Optional<FreeBoardEntity> optionalBoard = boardRepo.findById(bno);
		FreeBoardEntity board = optionalBoard.orElse(null);
		
		model.addAttribute("board", board);
		
		return "freeboard/boarddetail";
	}

}
