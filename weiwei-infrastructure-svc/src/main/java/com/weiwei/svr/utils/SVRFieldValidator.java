package com.weiwei.svr.utils;

public class SVRFieldValidator {
	public static boolean isNullOrBlank(String fieldName){
		return ((null == fieldName) || (0 == fieldName.trim().length()));
	}
}
