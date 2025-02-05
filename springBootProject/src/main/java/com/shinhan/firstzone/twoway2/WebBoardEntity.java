package com.shinhan.firstzone.twoway2;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.firstzone.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "replies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // JPA 관리 대상, @Table 생략 시 @Entity 이름이 table 이름으로 사용됨
@Table(name = "tbl_webboards")
public class WebBoardEntity {

	@Id // PK, 필수
	@GeneratedValue(strategy = GenerationType.IDENTITY) // oracle:sequence, mysql:identity
	// IDENTITY : MariaDB의 칼럼에 auto_increment 적용됨
	// AUTO : MariaDB의 sequence 역할을 하는 table이 생성됨
	private Long bno;
	
//	@NonNull // WebBoard가 build 될 때 반드시 setting 해야 한다.
	@Column(nullable = false) // DB 칼럼이 not null
	private String title;
	
	// WebBoardEntity가 MemberEntity 참조
	@ManyToOne // 여러 개의 게시글은 한 명의 user가 작성
	private MemberEntity writer; // writer_mid 이름으로 칼럼 생성
	
	@Column(length = 100)
	private String content;

	@CreationTimestamp
	private Timestamp regdate; // yyyy-MM-dd hh:mm:ss

	@UpdateTimestamp // 생성 시 생성일자, 수정 시 변경된다.
	private Timestamp updatedate;

	// @BatchSize(size = 100)
	@JsonIgnore
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<WebReplyEntity> replies;
	// @OneToMany와 @ManyToMany는 기본이 지연 로딩(LAZY)이다.
	// @ManyToOne이 EAGER임. 양방향이므로 reply에서 board 정보 필요하므로 N번 호출됨
	// 그러므로 OneToMany에서 지연 로딩으로 변경하여도 N번 쿼리 호출된다.
	// 해결 방안은 BatchSize 조정 @BatchSize(size = 100)

}
