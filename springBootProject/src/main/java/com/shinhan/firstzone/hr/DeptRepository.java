package com.shinhan.firstzone.hr;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<DeptEntity, Long> {
	
	// (조건 조회 시) 함수 이름 규칙에 맞는 메소드 추가

}
