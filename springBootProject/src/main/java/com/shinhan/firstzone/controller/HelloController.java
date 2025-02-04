package com.shinhan.firstzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.twoway.FreeBoardEntity;

@Controller
@RequestMapping("/section1")
public class HelloController {
	
	@GetMapping("/hello1")
	public void f1(Model model) {
		model.addAttribute("myname", "James");
		model.addAttribute("myscore", "100");
		
		FreeBoardEntity board = FreeBoardEntity.builder()
				.title("스프링 부트")
				.content("타임리프 사용하기")
				.writer("James")
				.build();
		model.addAttribute("board", board);
	}
	
	@GetMapping("/hello2")
	public String f2() {
		return "section1/hello1"; // templates/section1/hello1.html
	}

}
