package com.shinhan.firstzone.twoway2;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.entity.MemberEntity;

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
@ToString(exclude = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_webreplies")
public class WebReplyEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // oracle:sequence, mysql:identity
	Long rno;
	
	String replyText;
	
	@ManyToOne
	MemberEntity replier; // replier_mid 이름으로 칼럼 생성
	
	@CreationTimestamp
	private Timestamp regdate; //yyyy-MM-dd hh:mm:ss
	
	@UpdateTimestamp // 생성 시 생성일자, 수정 시 변경된다.
	private Timestamp updatedate;
	
	//@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY) // LAZY로 변경해도 OneToMany에서 LAZY가 그대로 수행~
	//@JoinColumn(name="board_bno")
	WebBoardEntity board;
	// @ManyToOne, @OneToOne과 같이 @XXXToOne 어노테이션들은 기본이 즉시 로딩(EAGER)이다.

}
