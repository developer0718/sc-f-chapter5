package com.netzoom.servicezuul.apimanager;

import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.model.Permission;
import com.netzoom.servicezuul.apimanager.util.CommonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalTest {

	@Test
	public void BCrypt_test(){
		System.out.println(new BCryptPasswordEncoder().encode(1+""));
	}

	@Test
	public void pattern_test(){
		String patternString = "/api/";
		String content = "/api/te";

		System.out.println(CommonUtil.StringMatcher(patternString,content));
	}


}
