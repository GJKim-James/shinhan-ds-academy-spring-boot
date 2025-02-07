package com.shinhan.firstzone.hr;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"job", "manager", "dept"}) // select 수행 시 job, manager, dept에 대한 내용은 toString() 하지 않음
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_employees")
public class EmpEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long employee_id;
	
	String first_name;
	String last_name;
	String email;
	String phone_number;
	Date hire_date;
	
	/*
	 * JPA 종속적인 엔티티의 영속성 전이에 대한 설정(CascadeType)
	 * ALL: 모든 변경에 대해 전이
	 * PERSIST: 저장 시에만 전이
	 * MERGE: 병합 시에만 전이
	 * REMOVE: 삭제 시에만 전이
	 * REFRESH: 엔티티 매니저의 refresh() 호출 시 전이
	 * DETACH: 부모 엔티티가 detach되면 자식 엔티티 역시 detach
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id") // 칼럼명 지정
	JobEntity job;
	
	Long salary;
	Double commission_pct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id") // 칼럼명 지정
	EmpEntity manager;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id") // 칼럼명 지정
	DeptEntity dept;

}

/*
 * Hibernate:
 * 	alter table if exists tbl_employees
 * 		add constraint FKt7f0eccsg9eujld961h8nfinj
 * 		foreign key (department_id)
 * 		references tbl_departments (department_id)
 * Hibernate:
 * 	alter table if exists tbl_employees
 * 		add constraint FKhrym0t1hwdl9ffntw83rgeuoh
 * 		foreign key (job_id)
 * 		references tbl_jobs (job_id)
 * Hibernate:
 * 	alter table if exists tbl_employees
 * 		add constraint FK9wb7mmo1js3le9cld91g3jbug
 * 		foreign key (manager_id)
 * 		references tbl_employees (employee_id)
 */
