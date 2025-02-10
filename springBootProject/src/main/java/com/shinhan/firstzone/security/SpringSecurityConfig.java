package com.shinhan.firstzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//SpringBoot 3.4.1 버전 사용 중
// @Configuration, @EnableWebSecurity, @Bean : application 시작 시 해석함
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 정적 자원 : “/css/*”, “/js/*”, “/assets/*”, “/vendor/**”
	// 파일 : *, 폴더 : **
	private static final String[] MANAGER_LIST = {"/security/manager"};
	private static final String[] ADMIN_LIST = {"/security/admin"};
	private static final String[] USER_LIST = {"/security/user", "/webboard/*"};
	private static final String[] WHITE_LIST = {"/security/all", "/auth/*"};
	
	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			// 권한이 필요한 페이지들 설정
			auth.requestMatchers(MANAGER_LIST).hasRole("MANAGER");
			auth.requestMatchers(ADMIN_LIST).hasRole("ADMIN");
			auth.requestMatchers(USER_LIST).hasRole("USER");
			
			// 특정 URL들 허용
			auth.requestMatchers(WHITE_LIST).permitAll();
			
			// 나머지는 반드시 로그인하고 접근 가능
			auth.anyRequest().authenticated();
		});
		
		// http.csrf().disable(); disable이 아니면 post, put, delete 방식의 요청 시 반드시 csrf 토큰을 가지고 요청해야한다.
//		http.csrf(c -> c.disable()); // default는 csrf가 enabled(활성화)
		
		// 로그인하는 화면(username은 mid로 받음, 로그인 성공 시 /auth/loginSuccess)
		http.formLogin(login -> {
			login.loginPage("/auth/login").usernameParameter("mid").defaultSuccessUrl("/auth/loginSuccess").permitAll();
		});
		
		// 로그아웃
		http.logout(out -> {
			out.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).logoutSuccessUrl("/auth/login")
			.invalidateHttpSession(true);
		});
		
		// 예외 처리
		http.exceptionHandling(handling -> {
			handling.accessDeniedPage("/auth/accessDenied");
		});
		
		return http.build();
	}

}
