package com.weiwei.parent.common.base;

public class StringUtility {
	public static boolean isEmptyString(String str){
		if(str == null || "".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}
}
