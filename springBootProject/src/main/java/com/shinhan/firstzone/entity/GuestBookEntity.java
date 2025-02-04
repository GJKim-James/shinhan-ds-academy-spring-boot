package com.shinhan.firstzone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // JPA가 관리한다.
@Table(name = "t_guestbook") // 선언해주지 않을 경우 클래스 이름인 GuestBookEntity로 테이블 생성됨
public class GuestBookEntity extends BaseEntity {
	
	@Id // id(PK)가 없는 경우 : 오류 메시지; has no identifier
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment, Oracle은 sequence 사용
	private Long gno;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(length = 1500, nullable = false)
	private String content;
	
	@Column(length = 150, nullable = false)
	private String writer;

	@Override
	public String toString() {
		return "GuestBookEntity 정보 [gno=" + gno + ", title=" + title + ", content=" + content + ", writer=" + writer + ","
				+ "등록일=" + getRegDate() + ", 수정일=" + getModDate() + "]";
	}

}
