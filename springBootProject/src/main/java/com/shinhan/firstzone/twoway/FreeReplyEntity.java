package com.shinhan.firstzone.twoway;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board") // toString() 호출 시 board 필드는 제외
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_free_replies")
public class FreeReplyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long rno;
	String reply;
	String replier;
	
	@CreationTimestamp // insert 시 자동값(insert 시각)
	private Timestamp regdate;
	
	@UpdateTimestamp // update 시 자동값(update 시각)
	private Timestamp updatedate;
	
	// 연관 관계? 여러 개의 댓글은 하나의 board를 참조한다.(board의 bno를 FK로 갖는다.)
	// 칼럼은 1:n 관계에서 n 쪽에 만들어진다.
	@JsonIgnore // Jackson이 JSON 생성 시 무시하기 즉, board 조회 시 댓글이 오고 댓글의 board는 제외
	@ManyToOne(fetch = FetchType.LAZY)
	private FreeBoardEntity board; // 칼럼 이름은 board_bno

}
