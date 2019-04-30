package com.netzoom.servicezuul.apimanager.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netzoom.servicezuul.apimanager.model.BaseModel;
import com.netzoom.servicezuul.apimanager.security.filter.LoginAuthenticationFilter;
import com.netzoom.servicezuul.apimanager.security.service.MyUserDetailService;
import com.netzoom.servicezuul.apimanager.security.util.SignEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * EnableWebSecurity 注解以及WebSecurityConfigurerAdapter
 * 一起配合提供基于web的security。自定义类
 * 继承了WebSecurityConfigurerAdapter来重写了一些方法来指定一些特定的Web安全设置。
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
		web.ignoring().antMatchers("/index.html", "/static/**");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//添加自定义Login过滤器
		http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		//允许跨域
		http.csrf().disable();
		//配置认证规则
		http.authorizeRequests()
				.antMatchers("/","index","/login","/login-error","/401","/css/**","/js/**","/user/login","/login/test","/api-c/**","/adminLogin","/error","/api/**").permitAll()
				.anyRequest().authenticated()
				.and()
				//loginPage/loginProcessingURL指登录的接口与默认登录界面
				.formLogin().permitAll().loginProcessingUrl("/login")
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());


	}

	;

	/**
	 * 注册自定义的UsernamePasswordAuthenticationFilter
	 */
	@Bean
	LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
				httpServletResponse.setContentType("application/json;charset=utf-8");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("result", "success");
				jsonObject.put("message", "登录成功");
				PrintWriter out = httpServletResponse.getWriter();
				out.write(jsonObject.toJSONString());
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
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("result", Constant.FAIL);
				jsonObject.put("message", "登录失败，请重新登录");
				PrintWriter out = httpServletResponse.getWriter();
				out.write(jsonObject.toJSONString());
				out.flush();
				out.close();
			}
		});
		filter.setFilterProcessesUrl("/login");

		//这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}


	/**
	 * 不允许访问控制器
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
				httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(new BaseModel("fail", "无此权限")));
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
	 * @return
	 */
	public AuthenticationEntryPoint authenticationEntryPoint(){
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				httpServletResponse.setStatus(HttpStatus.OK.value());
				httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(new BaseModel("fail", "认证信息失败")));
			}
		};
	}
}
