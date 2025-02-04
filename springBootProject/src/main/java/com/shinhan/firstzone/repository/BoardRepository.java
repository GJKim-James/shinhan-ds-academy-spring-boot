package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.firstzone.entity.BoardEntity;

// Repository 설계 : <DB의 객체와 연결된 Entity 설정, PK의 타입>
// JPA가 구현 객체를 만들어 준다.
public interface BoardRepository extends CrudRepository<BoardEntity, Long>,
										 PagingAndSortingRepository<BoardEntity, Long> {
	
	// 1. CrudRepository를 상속받으면 기본 메서드 지원 : findAll(), findById(), save() 등
	
	// 2. 규칙에 맞는 함수를 정의하기
	// https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods 참고
	List<BoardEntity> findByWriter(String user); // where Writer = ?
	List<BoardEntity> findByContentLike(String content); // where Content Like ?
	List<BoardEntity> findByContentContaining(String content); // where Content Like '%' || ? || '%'
	
	// where boardNo >= ? and boardNo <= ?
	List<BoardEntity> findByBoardNoGreaterThanEqualAndBoardNoLessThanEqual(Long boardNo1, Long boardNo2);
	
	// where boardNo between ? and ?
	List<BoardEntity> findByBoardNoBetween(Long boardNo1, Long boardNo2);
	
	// where title like '%' || ?
	// where title like ? || '%'
	List<BoardEntity> findByTitleStartingWith(String keyword);
	List<BoardEntity> findByTitleEndingWith(String keyword);
	
	// where title like ? || '%' and (boardNo between ? and ?) order by boardNo desc
	List<BoardEntity> findByTitleEndingWithAndBoardNoBetweenOrderByBoardNoDesc(String keyword, Long boardNo1, Long boardNo2);

}
