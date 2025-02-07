package com.shinhan.firstzone.hr;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
	
	Long employee_id;
	
	String first_name;
	String last_name;
	String email;
	String phone_number;
	Date hire_date;
	
	// Job
	String job_id;
	String job_title;
	
	Long salary;
	Double commission_pct;
	
	// Manager
	Long manager_id;
	
	// Dept
	Long department_id;
	String department_name;

}
