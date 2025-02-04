package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
	
	// Member를 통해서 Profile에 접근하기(member가 몇 개의 profile을 가지고 있는지 조회하기)
	@Query("select m.mid, count(p) "
			+ "from MemberEntity m left outer join ProfileEntity p "
			+ "on(m = p.member) "
			+ "where m.mid like %?1% "
			+ "group by m.mid")
	List<Object[]> getMemberWithProfileCount(String mid);
	
	// nativeQuery 이용하기
	@Query(value = "SELECT m.mid, COUNT(p.fno) "
			+ "FROM tbl_members m LEFT OUTER JOIN tbl_profile p "
			+ "ON(m.mid = p.member_mid) "
			+ "WHERE m.mid LIKE %?1% "
			+ "GROUP BY m.mid "
			+ "ORDER BY m.mid", nativeQuery = true)
	List<Object[]> getMemberWithProfileCount2(String mid);

}
