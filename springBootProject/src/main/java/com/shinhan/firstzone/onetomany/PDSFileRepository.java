package com.shinhan.firstzone.onetomany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PDSFileRepository extends JpaRepository<PDSFileEntity, Long> {
	
	// @Query : Select
	
	// Select 문이 아닌 DML 수행하기
	@Modifying
	@Query(value = "DELETE FROM tbl_pdsfile WHERE pdsno IS NULL", nativeQuery = true)
	public void fileDelete();

}
