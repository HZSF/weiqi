package com.weiwei.intellectual.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.weiwei.intellectual.common.request.TrademarkNamingRequest;
import com.weiwei.parent.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;

public class TrademarkNamingProcessor extends BaseProcessor{
	public final static String URLTRADEMARKNAMING = "http://www.qiming2.com/Search/";
	private TrademarkNamingRequest request;
	private String SearchArea = "";
    private String areaNum = "";
    private String SearchWord = "";
    private String AllSerStr = "";
    private String AllSerStrValue = "";
    private String AllSerNum = "1";
    private String AllSerNumLast = "1";
    
    private String result;
    private List<String> resultList = new ArrayList<String>();
    
    @Override
    protected void preProcess(Map scopes){
		request = (TrademarkNamingRequest)scopes.get(Constants.TRADEMARK_TRADE_NAMING_REQUEST);
		SearchArea = request.getArea();
		SearchWord = request.getWord();
		areaNum = request.getAreaNum();
	}
    
	@Override
	protected String executeProcess(Map scopes) {
		try{
            AllSerStr = SearchArea + "||||";
            AllSerStrValue = areaNum+"||||";
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(URLTRADEMARKNAMING);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("area", SearchArea));
            nameValuePairs.add(new BasicNameValuePair("areaNum", areaNum));
            nameValuePairs.add(new BasicNameValuePair("w", SearchWord));
            nameValuePairs.add(new BasicNameValuePair("AllSerStr", AllSerStr));
            nameValuePairs.add(new BasicNameValuePair("AllSerStrValue", AllSerStrValue));
            nameValuePairs.add(new BasicNameValuePair("AllSerNum", AllSerNum));
            nameValuePairs.add(new BasicNameValuePair("AllSerNumLast", AllSerNumLast));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "GB2312"));
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "GB2312");
        }catch (Exception e){
            e.printStackTrace();
            return Constants.EVENT_FAIL;
        }
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		if(event.equalsIgnoreCase(Constants.EVENT_SUCCESS)){
	        try {
	            if(result != null){
	                result = result.substring(result.indexOf("DataShow"));
	                result = result.substring(result.indexOf("<form"), result.indexOf("</form>"));
	                while (result.contains("<tr>")){
	                    result = result.substring(result.indexOf("<tr>"));
	                    if(result.contains("\"DS2\">")) {
	                        result = result.substring(result.indexOf("\"DS2\">"));
	                        String name = result.substring(6, result.indexOf("</td>"));
	                        resultList.add(name);
	                    }else{
	                        return Constants.EVENT_FAIL;
	                    }
	                }
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		}
		scopes.put(Constants.SERVICE_RESPONSE, resultList);
		return event;
	}

}
