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
public class DeptDTO {
	
	Long department_id;
	String department_name;
	
	Long manager_id;
	String manager_name; // first_name + last_name
	
	Long empCount;
	
}
