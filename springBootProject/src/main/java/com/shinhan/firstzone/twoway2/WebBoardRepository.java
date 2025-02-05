package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

// 상수, 추상메소드, static 메소드, default 메소드, private 메소드, 일반 메소드 불가
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>,
											QuerydslPredicateExecutor<WebBoardEntity> {
	
	// 규칙에 맞는 함수 이름 정의
	// 특정 Member가 작성한 board 정보 조회
	List<WebBoardEntity> findByWriter(MemberEntity writer);
	
	public default Predicate makePredicate(String type, String keyword) {
		QWebBoardEntity board = QWebBoardEntity.webBoardEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(board.bno.goe(0)); // WHERE bno >= 0
		
		if (type == null) {
			return builder;
		}
		
		switch (type) {
		case "title" -> {
			builder.and(board.title.contains(keyword));
		}
		case "content" -> {
			builder.and(board.content.contains(keyword));
		}
		case "writer" -> {
			builder.and(board.writer.mid.eq(keyword));
		}
		}
		
		return builder;
	}

}
