package net.shinhan.myapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController2 {
	
	@GetMapping("/aa")
	public String f1() {
		return "base package 다른 Class 접근 (SpringBootProjectApplication.java 파일에 @ComponentScan(basePackages = {\"com.shinhan.firstzone\", \"net.shinhan\"}) 추가)";
	}

}
