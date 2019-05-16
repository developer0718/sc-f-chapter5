package com.netzoom.servicezuul.apimanager.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 获得不带‘-’的UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 打log
	 * @param object
	 */
	public static void makeLogInfo(Object object){
		logger.info("执行结果："+ JSON.toJSONString(object));
	}

	/**
	 * 字符串通配方法
	 * @param patternString 通配符 如/api/*
	 * @param content 需要匹配的内容 如/api/test
	 * @return boolean
	 */
	public static boolean StringMatcher(String patternString,String content){
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(content);
		return matcher.lookingAt();
	}
}
