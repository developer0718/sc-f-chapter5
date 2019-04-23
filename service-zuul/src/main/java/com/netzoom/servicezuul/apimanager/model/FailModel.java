package com.netzoom.servicezuul.apimanager.model;

import com.netzoom.servicezuul.apimanager.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 失败消息传输模型
 * @author tanzj
 */
public class FailModel extends BaseModel {

	Logger logger = LoggerFactory.getLogger(FailModel.class);

	public FailModel(Object message) {
		super(Constant.FAIL, message);
	}

}
