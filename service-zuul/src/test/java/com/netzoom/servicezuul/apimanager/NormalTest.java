package com.netzoom.servicezuul.apimanager;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NormalTest {

	@Test
	public void BCrypt_test(){
		System.out.println(new BCryptPasswordEncoder().encode(1+""));
	}
}
