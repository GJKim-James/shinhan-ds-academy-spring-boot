package com.shinhan.firstzone.onetomany;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"files"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_pdsboard")
// 하나의 Board는 여러 개의 File을 갖는다.(OneToMany)
public class PDSBoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;
	private String pname;
	private String pwriter;
	
	// cascade : 부모의 DML이 자식에 영향을 미친다.
	// fetch(EAGER) : 부모의 조회 시 자식이 같이 조회된다.
	// fetch(LAZY) : 부모의 조회 시 자식이 같이 조회되지 않는다.
	// LAZY 사용한 경우 : @ToString(exclude = {"files"}) 설정되어 있지 않으면 오류, getFiles() 사용 오류
	// @Transactional : LAZY임에도 getFiles() 사용 가능
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pdsno") // tbl_pdsfile에 pdsno 칼럼(FK)이 생성됨
	List<PDSFileEntity> files;
	
	/*
	 * @JoinColumn의 설정이 없으면 중간 table이 생성된다.
	 * create table tbl_pdsboard_files (
	 *     pdsboard_pid bigint not null,
	 *     files_fno bigint not null
	 * ) engine=InnoDB
	 */

}
