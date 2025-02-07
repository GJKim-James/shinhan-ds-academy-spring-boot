package com.shinhan.firstzone.hr;

import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "emplist")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_departments")
public class DeptEntity {
	
	@Id
	Long department_id;
	String department_name;
	
	@OneToOne // 부서 하나 매니저 한 명
	@JoinColumn(name = "manager_id", nullable = true)
	EmpEntity manager; // 직원 중 한 명이 매니저
	
	@BatchSize(size = 100)
	@JsonIgnore
	@OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // DeptEntity와 양방향 설정
	List<EmpEntity> emplist;

}

/*
 * Hibernate:
 * 	alter table if exists tbl_departments
 * 		drop index if exists UKrefbo4jykmkojho7i2mhjxnu4
 * Hibernate:
 * 	alter table if exists tbl_departments
 * 		add constraint UKrefbo4jykmkojho7i2mhjxnu4 unique (manager_id)
 * Hibernate:
 * 	alter table if exists tbl_departments
 * 		add constraint FKi1gudnijf5o3msjkttbg93fqy
 * 		foreign key (manager_id)
 * 		references tbl_employees (employee_id)
 */
