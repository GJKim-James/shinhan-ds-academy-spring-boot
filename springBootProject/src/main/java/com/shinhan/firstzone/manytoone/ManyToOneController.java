package com.shinhan.firstzone.manytoone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class ManyToOneController {
	
	@Autowired
	EmailBookService emailBookService;
	
	@GetMapping("/one/{bookId}")
	public EmailBookEntity q1(@PathVariable Long bookId) {
		return emailBookService.quiz1(bookId);
	}
	
	@GetMapping("/two/{customerId}")
	public List<EmailBookEntity> q2(@PathVariable String customerId) {
		return emailBookService.quiz2(customerId);
	}
	
	@GetMapping("/three")
	public List<ResponseEmailCountDTO> q3() {
		return emailBookService.quiz3();
	}

}
