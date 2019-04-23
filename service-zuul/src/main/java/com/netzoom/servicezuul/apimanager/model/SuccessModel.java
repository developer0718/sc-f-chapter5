package com.netzoom.servicezuul.apimanager.model;

import com.netzoom.servicezuul.apimanager.util.Constant;

/**
 * 成功消息传递模型
 * @author tanzj
 */
public class SuccessModel extends BaseModel {

	public SuccessModel(Object message) {
		super(Constant.SUCCESS, message);
	}
}
