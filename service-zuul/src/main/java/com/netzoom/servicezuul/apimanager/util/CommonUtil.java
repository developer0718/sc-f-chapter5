package com.netzoom.servicezuul.apimanager.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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
}
