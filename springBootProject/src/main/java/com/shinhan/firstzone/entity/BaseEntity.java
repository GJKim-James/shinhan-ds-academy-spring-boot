package com.shinhan.firstzone.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass // table 생성 안함, 부모 클래스로 사용(공통 맵핑 정보가 필요할 때 사용하며 부모 클래스에서 선언하고 속성만 상속 받아서 사용하고 싶을 때 사용)
@EntityListeners(value = {AuditingEntityListener.class}) // entity 객체가 생성, 변경되는 것을 감지
public abstract class BaseEntity {
	
	@CreatedDate // 생성 일자를 관리하는 필드에 현재 날짜를 주입
	@Column(name = "regdate") // reg_date로 생성되는 것이 싫다면 직접 선언해줌
	private LocalDateTime regDate; // insert 시 자동으로 들어감, 카멜 표기법 : DB에는 대문자 앞에 _가 들어간 형태로 생성됨(reg_date)
	
	@LastModifiedDate
	@Column(name = "moddate") // mod_date로 생성되는 것이 싫다면 직접 선언해줌
	private LocalDateTime modDate; // insert 시, update 시에 자동으로 들어감

}
