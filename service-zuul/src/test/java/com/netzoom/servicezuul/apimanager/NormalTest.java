package com.netzoom.servicezuul.apimanager;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalTest {

	@Test
	public void BCrypt_test(){
		System.out.println(new BCryptPasswordEncoder().encode(1+""));
	}

	@Test
	public void pattern_test(){
		String patternString = "/api/*";
		String content = "/test";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(content);
		System.out.println(matcher.lookingAt());

		System.out.println(Pattern.matches(patternString, content));

	}
}
