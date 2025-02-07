package com.shinhan.firstzone.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.hr.EmpDTO;
import com.shinhan.firstzone.hr.EmpService;


@RestController
@RequestMapping("/emp")
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	// 조회
	@GetMapping("/list")
	public List<EmpDTO> selectAllService() {
		return empService.selectAllEmp();
	}
	
	// 상세보기
	@GetMapping("/detail/{empid}")
	public EmpDTO selectById(@PathVariable Long empid) {
		return empService.selectById(empid);
	}
	
	// 입력
	@PostMapping("/insert")
	public EmpDTO insertEmp(@RequestBody EmpDTO emp) {
		return empService.insertEmp(emp);
	}
	
	// 수정
	@PutMapping("/update")
	public EmpDTO updateEmp(@RequestBody EmpDTO emp) {
		return empService.updateEmp(emp);
	}
	
	// 삭제
	@DeleteMapping(value = "/delete/{empid}", produces = "text/plain;charset=utf-8")
	public String deleteEmp(@PathVariable Long empid) {
		empService.deleteEmp(empid);
		
		return "직원 삭제 완료";
	}

}
