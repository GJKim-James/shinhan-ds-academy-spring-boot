package com.shinhan.firstzone.twoway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeReplyRepository extends JpaRepository<FreeReplyEntity, Long> {
	
	// 1. JpaRepository가 제공하는 기본 메소드 : findById(), findAll(), save() 등
	
	// 2. 규칙에 맞는 함수 사용
	// https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods 참고
	// 댓글 중 board(board_bno)가 null인 데이터 조회
	public List<FreeReplyEntity> findByBoardIsNull();

}
