package com.shinhan.firstzone;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.hr.DeptEntity;
import com.shinhan.firstzone.hr.DeptRepository;
import com.shinhan.firstzone.hr.EmpEntity;
import com.shinhan.firstzone.hr.EmpRepository;
import com.shinhan.firstzone.hr.EmpService;
import com.shinhan.firstzone.hr.JobEntity;
import com.shinhan.firstzone.hr.JobRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class HrCRUDTest {
	
	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	DeptRepository deptRepo;
	
	@Autowired
	EmpRepository empRepo;
	
	@Autowired
	EmpService empService;
	
	// tbl_jobs에 데이터 등록
//	@Test
	public void insertJob() {
		// 데이터를 배열로 설정
	    Object[][] jobData = {
	    		{"AD_PRES", "President", 20080L, 40000L},
	    		{"AD_VP", "Administration Vice President", 15000L, 30000L},
	    		{"AD_ASST", "Administration Assistant", 3000L, 6000L},
	    		{"FI_MGR", "Finance Manager", 8200L, 16000L},
	    		{"FI_ACCOUNT", "Accountant", 4200L, 9000L},
	    		{"AC_MGR", "Accounting Manager", 8200L, 16000L},
	    		{"AC_ACCOUNT", "Public Accountant", 4200L, 9000L},
	    		{"SA_MAN", "Sales Manager", 10000L, 20080L},
	    		{"SA_REP", "Sales Representative", 6000L, 12008L},
	    		{"PU_MAN", "Purchasing Manager", 8000L, 15000L},
	    		{"PU_CLERK", "Purchasing Clerk", 2500L, 5500L},
	    		{"ST_MAN", "Stock Manager", 5500L, 8500L},
	    		{"ST_CLERK", "Stock Clerk", 2008L, 5000L},
	    		{"SH_CLERK", "Shipping Clerk", 2500L, 5500L},
	    		{"IT_PROG", "Programmer", 4000L, 10000L},
	    		{"MK_MAN", "Marketing Manager", 9000L, 15000L},
	    		{"MK_REP", "Marketing Representative", 4000L, 9000L},
	    		{"HR_REP", "Human Resources Representative", 4000L, 9000L},
	    		{"PR_REP", "Public Relations Representative", 4500L, 10500L}
	    };
	    
	    // jobData 배열을 반복하여 JobEntity 객체를 생성하고 저장
	    for (Object[] data : jobData) {
	        JobEntity jobEntity = JobEntity.builder()
	                .job_id((String) data[0])
	                .job_title((String) data[1])
	                .min_salary((Long) data[2])
	                .max_salary((Long) data[3])
	                .build();
	        
	        jobRepo.save(jobEntity);
	    }
	}
	
	// tbl_departments에 데이터 등록
//	@Test
	public void insertDept() {
//		List<Object[]> deptData = List.of(
//		        new Object[] {10L, "Administration", 200L, List.of(1700L)}, 
//		        new Object[] {20L, "Marketing", 201L, List.of(1800L)},
//		        new Object[] {30L, "Purchasing", 114L, List.of(1700L)},
//		        new Object[] {40L, "Human Resources", 203L, List.of(2400L)},
//		        new Object[] {50L, "Shipping", 121L, List.of(1500L)},
//		        new Object[] {60L, "IT", 103L, List.of(1400L)},
//		        new Object[] {70L, "Public Relations", 204L, List.of(2700L)},
//		        new Object[] {80L, "Sales", 145L, List.of(2500L)},
//		        new Object[] {90L, "Executive", 100L, List.of(1700L)},
//		        new Object[] {100L, "Finance", 108L, List.of(1700L)},
//		        new Object[] {110L, "Accounting", 205L, List.of(1700L)},
//		        new Object[] {120L, "Treasury", null, List.of(1700L)},
//		        new Object[] {130L, "Corporate Tax", null, List.of(1700L)},
//		        new Object[] {140L, "Control And Credit", null, List.of(1700L)},
//		        new Object[] {150L, "Shareholder Services", null, List.of(1700L)},
//		        new Object[] {160L, "Benefits", null, List.of(1700L)},
//		        new Object[] {170L, "Manufacturing", null, List.of(1700L)},
//		        new Object[] {180L, "Construction", null, List.of(1700L)},
//		        new Object[] {190L, "Contracting", null, List.of(1700L)},
//		        new Object[] {200L, "Operations", null, List.of(1700L)},
//		        new Object[] {210L, "IT Support", 105L, List.of(1800L)},
//		        new Object[] {220L, "NOC", null, List.of(1700L)},
//		        new Object[] {230L, "IT Helpdesk", null, List.of(1700L)},
//		        new Object[] {240L, "Government Sales", null, List.of(1700L)},
//		        new Object[] {250L, "Retail Sales", null, List.of(1700L)},
//		        new Object[] {260L, "Recruiting", null, List.of(1700L)},
//		        new Object[] {270L, "Payroll", null, List.of(1700L)}
//		);
		
		DeptEntity dept = DeptEntity.builder()
				.department_id(10L)
				.department_name("Administration")
				.build();
		
		DeptEntity dept2 = DeptEntity.builder()
				.department_id(60L)
				.department_name("IT")
				.build();
		
		DeptEntity dept3 = DeptEntity.builder()
				.department_id(100L)
				.department_name("Finance")
				.build();
		
		DeptEntity dept4 = DeptEntity.builder()
				.department_id(110L)
				.department_name("Accounting")
				.build();
		
		DeptEntity dept5 = DeptEntity.builder()
				.department_id(90L)
				.department_name("Executive")
				.build();
		
		deptRepo.save(dept5);
	}
	
	// tbl_employees에 데이터 등록
//	@Test
	public void insertEmp() {
		Date date = Date.valueOf(LocalDate.of(2007, 12, 07));
		JobEntity job = JobEntity.builder().job_id("FI_ACCOUNT").build();
		DeptEntity dept = DeptEntity.builder().department_id(100L).build();
		
		EmpEntity emp = EmpEntity.builder()
				.first_name("Alexander")
				.last_name("Hunold")
				.email("ahunold@gmail.com")
				.phone_number("590-423-4567")
				.hire_date(date)
				.job(job)
				.salary(9000L)
				.commission_pct(null)
				.manager(null)
				.dept(dept)
				.build();
		
		EmpEntity emp2 = EmpEntity.builder()
				.first_name("Steven")
				.last_name("King")
				.email("sking@gmail.com")
				.phone_number("515-123-4567")
				.hire_date(date)
				.job(job)
				.salary(24000L)
				.commission_pct(null)
				.manager(null)
				.dept(dept)
				.build();
		
		EmpEntity emp3 = EmpEntity.builder()
				.first_name("Shelley")
				.last_name("Higgins")
				.email("shiggins@gmail.com")
				.phone_number("515-123-8080")
				.hire_date(date)
				.job(job)
				.salary(12008L)
				.commission_pct(null)
				.manager(null)
				.dept(dept)
				.build();
		
		EmpEntity emp4 = EmpEntity.builder()
				.first_name("Luis")
				.last_name("Popp")
				.email("lpopp@gmail.com")
				.phone_number("515-124-4567")
				.hire_date(date)
				.job(job)
				.salary(6900L)
				.commission_pct(null)
				.manager(null)
				.dept(dept)
				.build();
		
		empRepo.save(emp4);
	}
	
	// 하나의 부서의 여러 직원 입력
//	@Test
	public void insertEmpByDept() {
		deptRepo.findById(60L).ifPresentOrElse(dept -> {
			// 존재하면 직원 추가
			Date date = Date.valueOf(LocalDate.of(2013, 05, 13));
			
			List<EmpEntity> emplist = dept.getEmplist();
			
			JobEntity job = JobEntity.builder().job_id("IT_PROG").build();
			EmpEntity manager = EmpEntity.builder().employee_id(2L).build();
			
			IntStream.rangeClosed(1, 3).forEach(i -> {
				EmpEntity emp = EmpEntity.builder()
						.first_name("지성" + i)
						.last_name("박")
						.email("jspark" + i + "@naver.com")
						.phone_number("010-1313-131" + i)
						.hire_date(date)
						.job(job)
						.salary(8000L)
						.commission_pct(0.5)
						.manager(manager)
						.dept(dept)
						.build();
				
				emplist.add(emp);
			});
			
			deptRepo.save(dept);
		}, () -> {
			// 존재하지 않으면 부서 insert, 직원 insert
		});
	}
	
	// 하나의 부서의 여러 직원 입력(110번 부서, 3명의 직원)
//	@Test
	public void insertEmpByDept2() {
		List<EmpEntity> emplist = new ArrayList<>();
		DeptEntity dept = DeptEntity.builder()
				.department_id(110L)
				.department_name("Accounting")
				.build();
		
		IntStream.rangeClosed(1, 3).forEach(i -> {
			EmpEntity emp = EmpEntity.builder()
					.first_name("광진" + i)
					.last_name("김")
					.email("gjkim" + i + "@naver.com")
					.phone_number("010-1234-567" + i)
//					.hire_date(date)
//					.job(job)
					.salary(4000L)
					.commission_pct(0.1)
//					.manager(manager)
					.dept(dept)
					.build();
			
			emplist.add(emp);
		});
		
		dept.setEmplist(emplist);
		deptRepo.save(dept);
	}
	
	// tbl_employees 모두 조회
//	@Transactional
//	@Test
	public void selectAllEmp() {
		empRepo.findAll().forEach(emp -> {
			System.out.println("[기본 정보(LAZY이므로 연관 관계 제회)] : " + emp);
			System.out.println("[job] : " + emp.getJob()); // @Transactional 선언하지 않으면 get 불가
			System.out.println("[manager] : " + emp.getManager()); // @Transactional 선언하지 않으면 get 불가
			System.out.println("[dept] : " + emp.getDept()); // @Transactional 선언하지 않으면 get 불가
		});
	}
	
	// tbl_employees 조건 조회
	// 키를 가지고 조회 : findById()
	// job_id = "IT_PROG", job_id가 null인 데이터 조회 => EmpRepository에 함수 정의
//	@Test
	public void selectById() {
		// job_id가 IT_PROG인 직원 조회
		JobEntity job = JobEntity.builder().job_id("IT_PROG").build();
		empRepo.findByJob(job).forEach(emp -> {
			System.out.println("[job_id가 IT_PROG인 직원] : " + emp);
		});
		System.out.println("============================================================");
		// job_id가 null인 직원 조회 후 job_id 수정
		empRepo.findByJobIsNull().forEach(emp -> {
			System.out.println("[job_id가 null인 직원] : " + emp);
			
			JobEntity job2 = JobEntity.builder().job_id("AC_ACCOUNT").build();
			emp.setJob(job2);
			empRepo.save(emp);
		});
	}
	
	// manager_id가 null인 직원들의 manager를 직원 번호 4로 수정
//	@Test
	public void selectByManager() {
		EmpEntity manager = EmpEntity.builder().employee_id(4L).build();
		
		empRepo.findByManagerIsNull().forEach(emp -> {
			emp.setManager(manager);
			empRepo.save(emp);
		});
	}
	
//	@Test
	public void selectByHireDate() {
		empRepo.findByHireDateIsNull().forEach(emp -> {
			emp.setHire_date(new Date(2025 - 1900, 01, 01)); // month 값 1월이 0, 2월 -> 1
			empRepo.save(emp);
		});
	}
	
//	@Test
//	public void selectByHireDate2() {
//		Long[] arr = { 9L, 10L, 11L };
//		empRepo.findAllById(Arrays.asList(arr)).forEach(emp -> {
//			emp.setHire_date(LocalDate);
//			empRepo.save(emp);
//		});
//	}
	
	@Transactional
	@Test
	public void selectAllService() {
		empService.selectAllEmp().forEach(emp -> {
			System.out.println(emp);
		});
	}

}
