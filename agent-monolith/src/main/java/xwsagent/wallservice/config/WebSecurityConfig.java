package xwsagent.wallservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import xwsagent.wallservice.security.RestAuthenticationEntryPoint;
import xwsagent.wallservice.security.TokenAuthenticationFilter;
import xwsagent.wallservice.security.TokenUtils;
import xwsagent.wallservice.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Autowired
	private UserService jwtUserDetailsService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenUtils tokenUtils;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			 
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()

				
				.anyRequest().authenticated().and()
				
				.cors().and()

				
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class)
		
		.headers().frameOptions().disable();

        http.csrf().disable();
		
	}
	

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.GET, "/api/post/all");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/**");
		web.ignoring().antMatchers(HttpMethod.DELETE, "/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/**",  "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}
	
	
}
