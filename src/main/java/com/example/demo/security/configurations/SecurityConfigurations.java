package com.example.demo.security.configurations;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.example.demo.security.controllers.LoginController;

/*
 insert into user values (1,1,"admin","antoine","cronier","admin");
 insert into role values (1,"admin");
 insert into user_roles values (1,1);
 */

@Configuration
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

//	@Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.
//                jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder);
//    }
	
//	@Bean
//    public CookieSerializer cookieSerializer() {
//            DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//            serializer.setCookieName("JSESSIONID"); 
//            serializer.setCookiePath("/"); 
//            serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
//            return serializer;
//    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//			.and()
				.authorizeRequests()
					.antMatchers("/", "/index", "/css/**", "/javascript/**"
							,"/registration")
						.permitAll()
//					.antMatchers("/users/edit/**").access("hasRole('ROLE_ADMIN')")
					.anyRequest()
						.authenticated()
			.and()
				.formLogin()
					.loginPage(LoginController.LOGIN)
					.usernameParameter(LoginController.FORM_USERNAME)
					.passwordParameter(LoginController.FORM_PASSWORD)
					.permitAll()
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login")
			.and()
				.httpBasic()
		;
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
//	@Bean
//	GrantedAuthorityDefaults grantedAuthorityDefaults() {
//	    return new GrantedAuthorityDefaults("ROLE_");
//	}
}
