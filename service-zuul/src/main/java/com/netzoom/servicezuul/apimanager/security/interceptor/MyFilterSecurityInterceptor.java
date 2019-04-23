package com.netzoom.servicezuul.apimanager.security.interceptor;

import com.netzoom.servicezuul.apimanager.security.manager.MyAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author tanzj
 * 每种受支持的安全对象类型（方法调用或Web请求）都有自己的拦截器类
 * 它是AbstractSecurityInterceptor的子类
 * AbstractSecurityInterceptor 是一个实现了对受保护对象的访问进行拦截的抽象类。
 */
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	@Autowired
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	@Autowired
	public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
		super.setAccessDecisionManager(myAccessDecisionManager);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
		invoke(filterInvocation);
	}

	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {

		InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
		try {
			//执行下一个拦截器
			filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
}
