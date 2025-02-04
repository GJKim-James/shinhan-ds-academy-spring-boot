package com.shinhan.firstzone.twoway;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Repository <-- CrudRepository <-- PagingAndSortingRepository <-- JpaRepository
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Long>,
											 QuerydslPredicateExecutor<FreeBoardEntity> {
	
	// 1. 기본 제공 메소드 이용
	// JpaRepository => findAll(Pageable pageable)
	// QuerydslPredicateExecutor => findAll(Predicate predicate)
	
	// 2. 규칙에 맞는 함수 정의 추가하기
	// 특정 writer가 작성한 board 조회
	List<FreeBoardEntity> findByWriter(String writer);
	
	Page<FreeBoardEntity> findByWriter(String writer, Pageable pageable);
	
	// 3. JPQL 사용하기(함수 이름 규칙 없음)
	@Query("SELECT board.title, COUNT(reply) "
			+ "FROM FreeBoardEntity board JOIN FreeReplyEntity reply "
			+ "ON(board = reply.board) "
			+ "GROUP BY board.title "
			+ "ORDER BY board.title")
	public List<Object[]> getBoardInfo();
	
	// 위 SQL에서 ON 구문 사용하지 않고 같은 결과 출력하기
	@Query("SELECT board.title, COUNT(reply) "
			+ "FROM FreeBoardEntity board JOIN board.replies "
			+ "GROUP BY board.title "
			+ "ORDER BY board.title")
	public List<Object[]> getBoardInfo2();
	
	// nativeQuery 사용(권장하지 않음)
	@Query(value = "SELECT board.title, COUNT(reply) "
			+ "FROM tbl_freeboard board JOIN tbl_free_replies reply "
			+ "ON(board.bno = reply.board_bno) "
			+ "GROUP BY board.title "
			+ "ORDER BY board.title", nativeQuery = true)
	public List<Object[]> getBoardInfo3();

}
