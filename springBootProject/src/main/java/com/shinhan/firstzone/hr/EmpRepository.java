package com.shinhan.firstzone.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// JPA가 interface로 CRUD 구현한다.
// JPA의 처리 단위는 Entity
public interface EmpRepository extends JpaRepository<EmpEntity, Long> {
	
	// 함수 이름 규칙에 맞는 함수 정의
	List<EmpEntity> findByJob(JobEntity job);
	List<EmpEntity> findByJobIsNull();
	List<EmpEntity> findByManagerIsNull();
	
	// 오류(필드명이 camel_case가 아닌 _ 사용)
//	List<EmpEntity> findByHireDateIsNull();
	
	// JPQL 작성
	@Query("SELECT emp FROM #{#entityName} emp WHERE emp.hire_date IS NULL")
	List<EmpEntity> findByHireDateIsNull();

}
