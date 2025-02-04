package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.entity.ProfileEntity;
import com.shinhan.firstzone.entity.QProfileEntity;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
//	@Test
	public void memberInsert() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			MemberEntity member = MemberEntity.builder()
					.mid("gjkim" + i)
					.mpassword("1234")
					.mname("김광진" + (11 - i))
					.mrole(i % 5 == 0 ? MemberRole.MANAGER : i == 9 ? MemberRole.ADMIN : MemberRole.USER) // manager(2), amdmin(1), user(7)
					.build();
			
			memberRepository.save(member);
		});
	}
	
	// 1번, 10번 2명의 profile을 등록
//	@Test
	public void insertProfile() {
		MemberEntity member1 = MemberEntity.builder().mid("gjkim7").build();
		MemberEntity member9 = memberRepository.findById("gjkim8").orElse(null);
		
		IntStream.rangeClosed(11, 13).forEach(i -> {
			ProfileEntity profile = ProfileEntity.builder()
					.fname("foot-" + i + ".jpg")
					.currentYn(i == 13 ? true : false)
					.member(member1)
					.build();
			profileRepository.save(profile);
		});
		
		IntStream.rangeClosed(20, 23).forEach(i -> {
			ProfileEntity profile = ProfileEntity.builder()
					.fname("cup-" + i + ".jpg")
					.currentYn(i == 23 ? true : false)
					.member(member9)
					.build();
			profileRepository.save(profile);
		});
	}
	
	// [Quiz1] profile의 fno=7인 data 조회
	// Primary Key로 조회함으로 default 제공 함수를 사용한다.
//	@Test
	public void quiz1() {
		Long fno = 7L;
		profileRepository.findById(fno).ifPresentOrElse(pro -> {
			log.info(pro.toString());
		}, () -> {
			log.info("data가 존재하지 않음");
		});
	}
	
	// [Quiz2] profile의 fname like '%face%'인 data 조회
	// ProfileRepository에 규칙에 맞는 함수를 추가한다.
//	@Test
	public void quiz2() {
		profileRepository.findByFnameContaining("face").forEach(pro -> {
			log.info(pro.toString());
		});
	}
	
	// [Quiz3] profile의 currentYn이 true인 data 조회
//	@Test
	public void quiz3() {
		profileRepository.findByCurrentYnTrue().forEach(pro -> {
			log.info(pro.toString());
		});
	}
	
//	@Test
	public void quiz3_2() {
		profileRepository.findByCurrentYn(true).forEach(pro -> {
			log.info(pro.toString());
		});
	}
	
	// [Quiz4] profile의 'gjkim9'의 profile data 조회
//	@Test
	public void quiz4() {
		MemberEntity member = MemberEntity.builder().mid("gjkim9").build();
		
		profileRepository.findByMember(member).forEach(pro -> {
			log.info(pro.toString());
			log.info("파일 이름 : " + pro.getFname());
			log.info("현재 profile 여부 : " + pro.isCurrentYn());
			log.info("이름 : " + pro.getMember().getMname());
		});
	}
	
	// [Quiz5] 특정 member 'gjkim9'의 currentYn이 true인 profile data 조회
//	@Test
	public void quiz5() {
		MemberEntity member = MemberEntity.builder().mid("gjkim9").build();
		
		profileRepository.findByMemberAndCurrentYn(member, true).forEach(pro -> {
			log.info(pro.toString());
			log.info("파일 이름 : " + pro.getFname());
			log.info("현재 profile 여부 : " + pro.isCurrentYn());
			log.info("이름 : " + pro.getMember().getMname());
		});
	}
	
	// [Quiz6] Quiz5 내용을 JPQL 사용하여 data 조회하기
//	@Test
	public void quiz6() {
		MemberEntity member = MemberEntity.builder().mid("gjkim9").build();
		
		profileRepository.findByMemberAndCurrentYn(member, false).forEach(pro -> {
			log.info(pro.toString());
		});
		
		profileRepository.quiz6_1(member.getMid(), true).forEach(pro -> {
			log.info(pro.toString());
		});
	}
	
	// [Quiz7] QueryDSL : JAVA로 SQL문 작성
//	@Test
	public void quiz7() {
		MemberEntity member = MemberEntity.builder().mid("gjkim9").build();
		QProfileEntity profile = QProfileEntity.profileEntity;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(profile.member.eq(member)); // where profile.member = "ej9"
		builder.and(profile.currentYn.eq(false)); // and currentYn = false
		
		profileRepository.findAll(builder).forEach(pro -> {
			log.info(pro.toString());
		});
	}
	
	// Member를 통해서 Profile에 접근하기(member가 몇 개의 profile을 가지고 있는지 조회하기)
	@Test
	public void getMemberWithProfileCount() {
		memberRepository.getMemberWithProfileCount("kim").forEach(arr -> log.info(Arrays.toString(arr)));;
	}
	
}
