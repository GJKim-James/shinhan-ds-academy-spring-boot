package com.shinhan.firstzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy // LoggingAdvice, StopWatchAdvice의 @Aspect 스캔
@SpringBootApplication
@ComponentScan(basePackages = {"com.shinhan.firstzone", "net.shinhan"})
@EnableJpaAuditing // BaseEntity의 @EntityListeners 감지
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
