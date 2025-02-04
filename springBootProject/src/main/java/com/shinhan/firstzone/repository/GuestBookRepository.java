package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.entity.GuestBookEntity;

// interface : 규격서(정의)
// JPA가 구현체를 만든다.
// Mapping(DB의 객체(table)와 Java의 객체(Entity))
// <GuestBookEntity, Long> : <Entity 클래스, PK의 타입>
public interface GuestBookRepository extends CrudRepository<GuestBookEntity, Long>,
											 PagingAndSortingRepository<GuestBookEntity, Long>,
											 QuerydslPredicateExecutor<GuestBookEntity> { // Querydsl를 이용한 동적 SQL의 처리
	
	// 1. CrudRepository의 기본 제공 메소드들 : findAll(), findById(), save(), count() 등
	
	// 2. 규칙에 맞는 함수 정의 findBy??? --- 복잡한 문장은 한계가 있음
	// https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods 참고
	List<GuestBookEntity> findByWriter(String writer);
	List<GuestBookEntity> findByContentContaining(String content);
	List<GuestBookEntity> findByGnoGreaterThan(Long gno);
	
	// 3. JPQL(JPA Query Language) : * 지원하지 않음
	@Query("select book from GuestBookEntity book "
			+ "where book.writer = ?1 and book.content like %?2% order by book.gno desc")
	List<GuestBookEntity> findByWriterContent2(String writer, String content);
	
	@Query("select book from GuestBookEntity book "
			+ "where book.writer = :ww and book.content like %:cc% order by book.gno desc")
	List<GuestBookEntity> findByWriterContent3(@Param("ww") String writer, @Param("cc") String content);
	
	// GuestBookEntity를 #{#entityName} 이렇게 가져오는 것이 이상적
	@Query("select book from #{#entityName} book "
			+ "where book.writer = :ww and book.content like %:cc% order by book.gno desc")
	List<GuestBookEntity> findByWriterContent4(@Param("ww") String writer, @Param("cc") String content);
	
	@Query("select book.gno, book.title, book.content from #{#entityName} book "
			+ "where book.writer = :ww and book.content like %:cc% order by book.gno desc")
	List<Object[]> findByWriterContent5(@Param("ww") String writer, @Param("cc") String content);
	
	// nativeQuery 사용 가능하지만 권장하지 않음(nativeQuery : * 사용하듯 원래 SQL 형태로 작성하는 것)
	@Query(value = "select * from t_guestbook book "
			+ "where book.writer = :ww and book.content like %:cc% order by book.gno desc", nativeQuery = true)
	List<GuestBookEntity> findByWriterContent6(@Param("ww") String writer, @Param("cc") String content);

}
