package com.uhaiin.enums;

/**
 * ApiResponse code 枚举类
 * 
 * @author Jiancai.zhong
 * @date 2024-10-21 10:03:29
 */
public enum ApiResponseEnum {

	/**
	 * 成功
	 */
	SUCCESS(0, "success"),

	/**
	 * 失败
	 */
	FAILURE(-1, "failure");

	public final Integer code;
	public final String message;

	ApiResponseEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}