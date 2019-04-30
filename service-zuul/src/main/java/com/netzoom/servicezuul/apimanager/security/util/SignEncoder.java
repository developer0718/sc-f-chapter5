package com.netzoom.servicezuul.apimanager.security.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义无加密PasswordEncoder
 * @author tanzj
 */
public class SignEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence charSequence) {
		return charSequence.toString();
	}

	@Override
	public boolean matches(CharSequence charSequence, String s) {
		return charSequence.toString().equals(s);
	}
}
