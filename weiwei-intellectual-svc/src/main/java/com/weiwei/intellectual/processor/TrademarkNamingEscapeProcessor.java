package com.weiwei.intellectual.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.weiwei.intellectual.beans.TrademarkNamingEscapeBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;

public class TrademarkNamingEscapeProcessor extends BaseProcessor{
	private String str;
	private String url = "http://www.qiming2.com/GetSers.asp?s=";
	private String result;
	private String[] areaNumArray;
	List<TrademarkNamingEscapeBean> list = new ArrayList<TrademarkNamingEscapeBean>();
	@Override
	protected void preProcess(Map scopes){
		str = (String)scopes.get(Constants.TRADEMARK_TRADE_NAMING_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
	        Socket sock = new Socket("www.qiming2.com", 80);
	        PrintWriter pw = new PrintWriter(sock.getOutputStream());
	        pw.println("GET http://www.qiming2.com/GetSers.asp?s="+escape(str)+" HTTP/1.1");
	        pw.println("Host: http://www.qiming2.com");
	        pw.println("");
	        pw.flush();
	        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "gb2312"));
	        String t;
	        while((t = br.readLine()) != null){ 
	        	if(t.startsWith("[") && t.endsWith("]")){
	        		result = t;
	        		break;
	        	}
	        }
	        br.close();
	        
	        if (!StringUtility.isEmptyString(result)) {
                StringBuilder b = new StringBuilder(result);
                b.replace(result.lastIndexOf("]"), result.lastIndexOf("]")+1, "");
                b.replace(result.indexOf("["), result.indexOf("[")+1, "");
                result = b.toString();
                if(!StringUtility.isEmptyString(result)){
	                result = result.replace("'", "");
	                areaNumArray = result.split(",");
	                for (int i = 0; i < areaNumArray.length; i++) {
	                    String item_str = areaNumArray[i];
	                    if(item_str.contains("@@")) {
	                        item_str = item_str.substring(0, item_str.indexOf("@@"));
	                        if (item_str.contains("::")) {
	                            item_str = item_str.replace("::", "（群组");
	                            item_str += "）";
	                        }
	                        list.add(new TrademarkNamingEscapeBean(item_str, areaNumArray[i]));
	                    }
	                }
                }
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, list);
		return event;
	}
	
	private String escape(String src){
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for(i=0; i<src.length(); i++){
            j = src.charAt(i);
            if(Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)){
                tmp.append(j);
            }else if(j < 256){
                tmp.append("%");
                if(j < 16){
                    tmp.append("0");
                }
                tmp.append(Integer.toString(j, 16));
            }else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }
}
