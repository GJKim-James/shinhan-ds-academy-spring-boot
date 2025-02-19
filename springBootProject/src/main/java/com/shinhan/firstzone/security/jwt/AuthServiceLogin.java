package com.shinhan.firstzone.security.jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceLogin {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	// 로그인 시 토큰 발급
//	@Transactional
	public String login(MemberEntity dto) {
		String mid = dto.getMid();
		String password = dto.getMpassword();
		MemberEntity member = memberRepository.findById(mid).orElse(null);
		if (member == null) {
			throw new UsernameNotFoundException("Mid가 존재하지 않습니다.");
		}

		// 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
		if (!encoder.matches(password, member.getMpassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		// ID, Password 확인되면 토큰 발급
		String accessToken = jwtUtil.createAccessToken(member);
		
		return accessToken;
	}
}
