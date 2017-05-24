package com.weiwei.centreservice.common.base;

import java.util.Map;

public class UtilityMethods {
	public static String getMapValueString(Map map, String key){
		if(map == null)
			return "";
		if(map.containsKey(key)){
			return (String)map.get(key);
		}else{
			return "";
		}
	}
}
