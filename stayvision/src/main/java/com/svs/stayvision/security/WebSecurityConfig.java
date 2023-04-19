package com.svs.stayvision.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private DataSource dataSource;	// 임포트 패키지 주의
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {
		hs.csrf().disable()
		.authorizeRequests()
		.antMatchers("/",
					"/checkId",
					"/boardRead",
					"/boardList",
					"/boardWrite",
					"/insert",
					"/js/**",
					"/img/**",
					"/error")
		.permitAll()					// 설정한 리소스의 접근을 인증 없이 사용 허가
		.antMatchers("/adminapproval").hasRole("ADMIN")
		.anyRequest().authenticated()	// 위의 경로 이외에는 모두 로그인
		.and()
		.formLogin()					// 일반적인 폼을 이용한 로그인 처리/실패 방법을 사용
		.loginPage("/login")	// 내가 사용할 폼은 시큐리티에서 제공하는 기본 폼이 아닌 사용자가 만든 폼 사용
		.loginProcessingUrl("/login").permitAll()	// 인증처리 URL. 로그인 폼의 action 속성 값 지정
		.usernameParameter("id")	// 로그인 폼 아이디의 name 속성
		.passwordParameter("pw")	// 로그인 폼 비밀번호의 name 속성
		.defaultSuccessUrl("/loginafter")// 로그인 후 페이지 이동
		.and()
		.logout()
		.logoutSuccessUrl("/").permitAll()	// 로그아웃 성공 시에 이동할 URL
		.and()
		.cors()
		.and()
		.httpBasic();
		
		return hs.build();
	}
	
	// 인증용 쿼리
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		String userNameQueryforEnabled = 
				"select USER_ID username, USER_PW password, enabled " +
				"from member " + 
				"where USER_ID = ?";
		
		String userNameQueryforRole =
				"select USER_ID username, ROLENAME role_name " + 
				"from member " +
				"where USER_ID = ?";
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(userNameQueryforEnabled)
		.authoritiesByUsernameQuery(userNameQueryforRole);
	}
	
	// 단방향 비밀번호 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
