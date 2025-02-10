package com.shinhan.firstzone.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	// 필수 메소드(로그인 시 Spring이 사용, 우리가 호출하는 것이 아님)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("[loadUserByUsername] username : " + username);
		Optional<MemberEntity> memberOp = memberRepo.findById(username);
		MemberEntity member = memberOp.orElse(null);
		
		// MemberEntity를 SecurityUser로 변경
		UserDetails user = memberOp.filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		// 세션에 저장
		httpSession.setAttribute("loginMember", member);
		
		return user;
	}
	
	// 사용자 등록 로직 추가하기
	public MemberEntity joinUser(MemberEntity member) {
		member.setMpassword(passwordEncoder.encode(member.getMpassword())); // 비밀번호 암호화
		
		return memberRepo.save(member);
	}

}
