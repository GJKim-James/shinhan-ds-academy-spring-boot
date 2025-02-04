package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>,
										   QuerydslPredicateExecutor<ProfileEntity> {
	
	// 규칙에 맞는 함수들을 정의한다.
	// [Quiz2] profile의 fname like '%face%'인 data 조회
	List<ProfileEntity> findByFnameContaining(String fname);
	
	// [Quiz3] profile의 currentYn이 true인 data 조회
	List<ProfileEntity> findByCurrentYnTrue();
	List<ProfileEntity> findByCurrentYn(boolean yn);
	
	// [Quiz4] profile의 'gjkim9'의 profile data 조회
	List<ProfileEntity> findByMember(MemberEntity entity);
	
	// [Quiz5] 특정 member 'gjkim9'의 currentYn이 true인 profile data 조회
	List<ProfileEntity> findByMemberAndCurrentYn(MemberEntity entity, boolean yn);
	
	// [Quiz6] Quiz5 내용을 JPQL 사용하여 data 조회하기
	@Query("select profile from #{#entityName} profile where member = :mm and currentYn = :yn")
	List<ProfileEntity> quiz6(@Param("mm") MemberEntity entity, @Param("yn") boolean yn);
	
	// [Quiz6] JPQL nativeQuery
	@Query(value = "select * from tbl_profile where member_mid = :mm and current_yn = :yn", nativeQuery = true)
	List<ProfileEntity> quiz6_1(@Param("mm") String mid, @Param("yn") boolean yn);

}
