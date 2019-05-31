package com.netzoom.servicezuul.apimanager.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netzoom.servicezuul.apimanager.model.BaseModel;
import com.netzoom.servicezuul.apimanager.model.FailModel;
import com.netzoom.servicezuul.apimanager.model.SuccessModel;
import com.netzoom.servicezuul.apimanager.security.filter.LoginAuthenticationFilter;
import com.netzoom.servicezuul.apimanager.security.service.MyUserDetailService;
import com.netzoom.servicezuul.apimanager.security.util.SignEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * spring security 核心配置文件
 *
 * @author tanzj
 */
@EnableWebSecurity
@Configuration
public class Config extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailService userDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//加载用户信息服务并挂载密码加密
		auth.userDetailsService(userDetailService).passwordEncoder(new SignEncoder());
	}

	/**
	 * 静态资源不过滤
	 *
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/index.html", "/static/**");
//		web.ignoring().mvcMatchers("/adminLogin","/api/**");
		web.ignoring().requestMatchers(new AntPathRequestMatcher("/adminLogin"),new AntPathRequestMatcher("/api/**"));
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//添加自定义Login过滤器
		http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		//允许跨域
		http.csrf().disable();
		//配置认证规则
		http.authorizeRequests()
				//放行的url
				.antMatchers( "/css/**", "/js/**", "/error","/actuator/health").permitAll()
				.antMatchers("/api/**").access("permitAll")
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());
	}


	/**
	 * 注册自定义的UsernamePasswordAuthenticationFilter
	 */
	@Bean
	LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				PrintWriter out = httpServletResponse.getWriter();
				System.out.println("认证成功");
				out.write(JSON.toJSONString(new SuccessModel("认证成功")));
				out.flush();
				out.close();
			}
		});
		/**
		 * 失败处理器
		 */
		filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				PrintWriter out = httpServletResponse.getWriter();
				out.write(JSON.toJSONString(new FailModel("认证失败")));
				out.flush();
				out.close();
			}
		});

		//这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}


	/**
	 * 拒绝访问控制器
	 *
	 * @return AccessDeniedHandler
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				httpServletResponse.setStatus(HttpStatus.OK.value());
				httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(new BaseModel("fail", "403.无此权限")));
			}
		};
	}

	/**
	 * 密码加密
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 自定义401返回结果
	 *
	 * @return
	 */
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				httpServletResponse.setStatus(HttpStatus.OK.value());
				httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(new BaseModel("fail", "401认证信息失败")));
			}
		};
	}

	/**
	 * 跨域请求配置
	 * @return CorsConfiguration
	 */
	@Bean
	public CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
