package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.security.jwt.AuthServiceLogin;
import com.shinhan.firstzone.security.jwt.TokenDTO;

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
	
	/*
	 * ------------------------------ JWT 이용한 로그인 및 회원 가입 ------------------------------
	 */
	
	@Autowired
	AuthServiceLogin authService;
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<TokenDTO> getMemberProfile(@RequestBody MemberEntity request) {
		System.out.println(request);
		String token = authService.login(request);
		TokenDTO dto = TokenDTO.builder().login(request.getMid()).token(token).build();
		
		return new ResponseEntity<>(dto, HttpStatus.OK); // ResponseEntity : 데이터 + 응답 상태 함께 전달
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<MemberEntity> f7(@RequestBody MemberEntity member) {
		System.out.println("회원가입 한 사용자 정보 : " + member);
		
//		member.setMrole(MemberRole.USER);
		MemberEntity newMember = memberService.joinUser(member);
		
		return new ResponseEntity<>(newMember, HttpStatus.OK);
	}

}
