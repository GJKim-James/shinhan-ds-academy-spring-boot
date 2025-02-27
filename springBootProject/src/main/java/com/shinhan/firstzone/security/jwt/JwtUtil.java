package com.shinhan.firstzone.security.jwt;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shinhan.firstzone.entity.MemberEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final Key key;
	private final long accessTokenExpTime; // 만료 시간

	// application.properties에서 읽어옴
	public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration_time}") long accessTokenExpTime) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey); // 암호화
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpTime = accessTokenExpTime;
	}

	/**
	 * Access Token 생성
	 * 
	 * @param member
	 * @return Access Token String
	 */
	public String createAccessToken(MemberEntity member) {
		return createToken(member, accessTokenExpTime);
	}

	/**
	 * JWT 생성
	 * 
	 * @param member
	 * @param expireTime
	 * @return JWT String
	 */
	private String createToken(MemberEntity member, long expireTime) {
		Claims claims = Jwts.claims();
		claims.put("memberId", member.getMid());
		claims.put("mname", member.getMname());
		claims.put("mrole", member.getMrole());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

		System.out.println(Date.from(now.toInstant()));
		System.out.println(Date.from(tokenValidity.toInstant()));
		return Jwts.builder()
				.setClaims(claims) // 정보 저장
				.setIssuedAt(Date.from(now.toInstant())) // 발급 시간
				.setExpiration(Date.from(tokenValidity.toInstant())) // set Expire Time
				.signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 값 세팅
				.compact();
	}

	/**
	 * Token에서 User ID 추출
	 * 
	 * @param token
	 * @return User ID
	 */
	public String getUserId(String token) {
		return parseClaims(token).get("memberId", String.class);
	}

	/**
	 * JWT 유효성 검증
	 * 
	 * @param token
	 * @return IsValidate
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	/**
	 * JWT Claims 추출(memberId, mname, mrole)
	 * 
	 * @param accessToken
	 * @return JWT Claims
	 */
	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
