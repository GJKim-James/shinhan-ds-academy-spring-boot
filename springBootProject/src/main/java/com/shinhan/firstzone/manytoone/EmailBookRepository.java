package com.shinhan.firstzone.manytoone;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailBookRepository extends CrudRepository<EmailBookEntity, Long>,
											 PagingAndSortingRepository<EmailBookEntity, Long>,
											 QuerydslPredicateExecutor<EmailBookEntity> {
	
	// 1. 기본 제공 함수들이 있다. findAll(), findById(), save(), count() 등
	
	// 2. 규칙에 맞는 함수 정의 추가하기
	List<EmailBookEntity> findByCust(CustomerEntity customerEntity);
	
	// 3. JPQL(nativeQuery가 아닌 경우); 자바의 entity, field와 매칭시켜야 함
	@Query("SELECT cust.customerName, COUNT(book.bookId) "
			+ "FROM CustomerEntity cust LEFT OUTER JOIN EmailBookEntity book "
			+ "ON(cust = book.cust) "
			+ "GROUP BY cust.customerId "
			+ "ORDER BY cust.customerId")
	List<Object[]> findByCustEmailCount();
	
	// 3. JPQL(nativeQuery인 경우)
	@Query(value = "SELECT cust.customer_name, COUNT(book.book_id) "
			+ "FROM tbl_customer cust LEFT OUTER JOIN tbl_emailbook book "
			+ "ON(cust.customer_id = book.cust_customer_id) "
			+ "GROUP BY cust.customer_id "
			+ "ORDER BY cust.customer_id", nativeQuery = true)
	List<Object[]> findByCustEmailCount2();

}
