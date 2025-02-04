package com.shinhan.firstzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.twoway.FreeBoardRepository;

@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@GetMapping("/list")
	public String f1(Model model) {
		model.addAttribute("boardlist", boardRepo.findAll());
		
		return "freeboard/boardlist";
	}

}
