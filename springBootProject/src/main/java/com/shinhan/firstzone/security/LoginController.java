package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;

@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		
	}
	
	@GetMapping("/logout")
	public void logout() {
		
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		
	}
	
	// 회원가입 페이지
	@GetMapping("/signup")
	public String joinForm() {
		return "auth/joinForm";
	}
	
	@ResponseBody
	@PostMapping("/joinProc")
	public String joinProc(MemberEntity member) {
		memberService.joinUser(member);
		
		return "회원가입 성공!";
	}

}
