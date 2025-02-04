package com.shinhan.firstzone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // @SpringBootApplication에서 찾아서 실행
public class AppConfig {
	
	public AppConfig() {
		log.info("---------- [@Configuration] AppConfig 생성 ----------");
	}
	
	@Bean // 객체를 만든다. (= @Component)
	public SpringComponent1 makeBean() {
		return new SpringComponent1();
	}

}
