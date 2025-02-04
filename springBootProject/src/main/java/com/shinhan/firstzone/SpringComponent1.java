package com.shinhan.firstzone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringComponent1 {
	
	public SpringComponent1() {
		log.info("---------- SpringComponent1 생성자 ----------");
	}
	
	public String getData() {
		return "SpringComponent1의 메서드 getData()";
	}

}
