package com.shinhan.firstzone.hr;

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
public class JobDTO {
	
	String job_id;
	String job_title;
	Long min_salary;
	Long max_salary;

}
