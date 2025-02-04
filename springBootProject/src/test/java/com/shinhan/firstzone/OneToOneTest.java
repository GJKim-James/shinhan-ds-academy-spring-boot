package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.onetoone.UserCellPhoneEntity;
import com.shinhan.firstzone.onetoone.UserCellPhoneEntity2;
import com.shinhan.firstzone.onetoone.UserCellPhoneEntity3;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository2;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository3;
import com.shinhan.firstzone.onetoone.UserEntity;
import com.shinhan.firstzone.onetoone.UserEntity2;
import com.shinhan.firstzone.onetoone.UserEntity3;
import com.shinhan.firstzone.onetoone.UserRepository;
import com.shinhan.firstzone.onetoone.UserRepository2;
import com.shinhan.firstzone.onetoone.UserRepository3;

import jakarta.persistence.CascadeType;

@SpringBootTest
public class OneToOneTest {
	
	// 주 테이블 이용 CRUD
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserCellPhoneRepository phoneRepo;
	
	// 부 테이블 이용 CRUD
	@Autowired
	UserRepository2 userRepo2;
	
	@Autowired
	UserCellPhoneRepository2 phoneRepo2;
	
	@Autowired
	UserRepository3 userRepo3;
	
	@Autowired
	UserCellPhoneRepository3 phoneRepo3;
	
	// 주 테이블(tbl_user)을 이용해서 부 테이블(tbl_usercellphone)에 insert
//	@Test
	void f1() {
		UserCellPhoneEntity phone = UserCellPhoneEntity.builder()
				.phoneNumber("010-1234-5678")
				.model("S24+")
				.build();
		UserCellPhoneEntity newPhone = phoneRepo.save(phone);
		
		UserEntity user = UserEntity.builder()
				.userid("gjk0635")
				.username("김광진")
				.phone(newPhone)
				.build();
		
		phoneRepo.save(phone);
	}
	
	// 부 테이블(tbl_user2)을 이용해서 주 테이블(tbl_usercellphone2)에 insert
//	@Test
	void f2() {
		UserEntity2 user = UserEntity2.builder()
				.userid("abc1004")
				.username("홍길동")
				.build();
		
		UserCellPhoneEntity2 phone = UserCellPhoneEntity2.builder()
				.phoneNumber("010-7777-8888")
				.model("아이폰16")
				.user(user)
				.build();
		
		userRepo2.save(user);
		phoneRepo2.save(phone);
	}
	
	// 부 테이블에서 주의 키를 식별자로 사용하기
	@Test
	void f3() {
		UserCellPhoneEntity3 phone = UserCellPhoneEntity3.builder()
				.phoneNumber("010-1212-3434")
				.model("아이폰15")
				.build();
		
		UserEntity3 user = UserEntity3.builder()
				.userid("a1004")
				.username("김철수")
				.phone(phone)
				.build();
		
		phone.setUser3(user); // 주의! UserEntity3에 cascade = CascadeType.ALL 설정
		userRepo3.save(user);
	}

}
