package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

// 상수, 추상메소드, static 메소드, default 메소드, private 메소드, 일반 메소드 불가
public interface WebReplyRepository extends JpaRepository<WebReplyEntity, Long>,
											QuerydslPredicateExecutor<WebReplyEntity> {
	
	// 규칙에 맞는 함수 이름 정의
	// 특정 Member가 작성한 reply 정보 조회
	List<WebReplyEntity> findByReplier(MemberEntity replier);
	
	// 특정 board의 reply 정보 조회
	List<WebReplyEntity> findByBoard(WebBoardEntity board);
	
	public default Predicate makePredicate(String type, String keyword) {
		QWebReplyEntity reply = QWebReplyEntity.webReplyEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(reply.rno.goe(0)); // WHERE rno >= 0
		
		if (type == null) {
			return builder;
		}
		
		switch (type) {
		case "replyText" -> {
			builder.and(reply.replyText.contains(keyword));
		}
		case "replier" -> {
			builder.and(reply.replier.mid.eq(keyword));
		}
		}
		
		return builder;
	}

}
