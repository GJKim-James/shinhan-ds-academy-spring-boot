package com.shinhan.firstzone.twoway;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"replies"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_freeboard")
public class FreeBoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@NonNull // 자바에서 설정
	@Column(nullable = false) // DB 설정
	private String title;
	private String writer;
	private String content;
	
	@CreationTimestamp // insert 시 자동값(insert 시각)
	private Timestamp regdate;
	
	@UpdateTimestamp // update 시 자동값(update 시각)
	private Timestamp updatedate;
	
	// cascade : 부모의 DML이 자식에 영향을 미친다.
	// fetch(EAGER) : 부모의 조회 시 자식이 같이 조회된다.(즉시 로딩)
	// fetch(LAZY) : 부모의 조회 시 자식이 같이 조회되지 않는다.(지연 로딩)
	// LAZY 사용한 경우 : @ToString(exclude = {"replies"}) 설정되어 있지 않으면 오류, getReplies() 사용 오류
	// @Transactional : LAZY임에도 getReplies() 사용 가능
	
	// 하나의 board는 여러 개의 reply를 갖는다.
	// SQL 문장에 IN을 사용함으로써 속도 증가 효과를 볼 수 있다.(WHERE r1_0.board_bno IN (1, 2, 3, ...))
	// 원래는 WHERE r1_0.board_bno = 1, WHERE r1_0.board_bno = 2, WHERE r1_0.board_bno = 3 하나씩 조회
	@BatchSize(size = 100)
	// mappedBy는 PDSBoardEntity.java의 @JoinColumn(name = "pdsno") 역할과 동일, 다만 양방향일 경우 mappedBy를 사용할 뿐.
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonIgnore // Jackson이 JSON 생성 시 무시하기 즉, board 조회 시 댓글은 제외
	List<FreeReplyEntity> replies;

}
