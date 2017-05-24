package com.weiwei.intellectual.trademark;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weiwei.intellectual.beans.TrademarkBean;
import com.weiwei.intellectual.beans.TrademarkResultPageLvlBean;
import com.weiwei.intellectual.common.request.TrademarkRequest;
import com.weiwei.intellectual.imageprocess.ImageRecognize;

public class SearchHelper {
	private HttpClient client = HttpClientBuilder.create().build();
    private String cookies;

    public List<?> getSimilarQueryResult(TrademarkRequest trademarkRequest){
    	try {
            String strUrl = "http://sbcx.saic.gov.cn:9080/tmois/wsjscx_getJscx.xhtml";
            HttpGet request = new HttpGet(strUrl);

            HttpResponse response = client.execute(request);

            setCookies(response.getFirstHeader("Set-Cookie") == null ? "": response.getFirstHeader("Set-Cookie").toString());
            addCookies(response.getLastHeader("Set-Cookie") == null ? "": response.getLastHeader("Set-Cookie").toString());

            String url_image = "http://sbcx.saic.gov.cn:9080/tmois/wszhcx_getCodeImage.xhtml";

            HttpGet request_image = new HttpGet(url_image);
            request_image.setHeader("Cookie", cookies);
            String code = "";
            int count=0;
            while(count++ < 10) {
                HttpResponse response_image = client.execute(request_image);
                HttpEntity entity_image = response_image.getEntity();
                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity_image);

                InputStream instream_image = bufHttpEntity.getContent();
                BufferedImage img = ImageIO.read(instream_image);
                code = ImageRecognize.getCodeFromImage(img);

                String strUrlnew = "http://sbcx.saic.gov.cn:9080/tmois/wszhcx_getImage.xhtml?inputCode=";
                strUrlnew += code;

                HttpGet request2 = new HttpGet(strUrlnew);
                request2.setHeader("Cookie", cookies);

                HttpResponse response2 = client.execute(request2);
                BufferedReader rd2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
                StringBuffer result2 = new StringBuffer();
                String line2 = "";
                while ((line2 = rd2.readLine()) != null) {
                    result2.append(line2);
                }

                if(result2.toString().equalsIgnoreCase("true")){
                    break;
                }
            }

            String strUrl_query = "http://sbcx.saic.gov.cn:9080/tmois/wsjscx_getSlectListBySys.xhtml?intcls=";
            strUrl_query += trademarkRequest.getCategoryNumber();
            strUrl_query += "&content=";
            //strUrl_query += URLEncoder.encode(URLEncoder.encode(trademarkRequest.getSearchContent(), "UTF-8"), "UTF-8");
            strUrl_query += trademarkRequest.getSearchContent();
            strUrl_query += "&type=";
            strUrl_query += trademarkRequest.getSearchMethod();
            strUrl_query += "&codeShow=";
            strUrl_query += code;
            
            HttpGet request_query = new HttpGet(strUrl_query);
            request_query.setHeader("Cookie", cookies);
            HttpResponse response_query = client.execute(request_query);
            String result_query = EntityUtils.toString(response_query.getEntity());
            
            Document doc = Jsoup.parse(result_query);
            
            return retrieveTrademarks(doc);
        }catch (Exception e){
        	e.printStackTrace();
        	return null;
        }
    }
    
    private TrademarkResultPageLvlBean retrievePageLvlInfo(Document doc){
    	TrademarkResultPageLvlBean bean = new TrademarkResultPageLvlBean();
    	if(doc.toString().contains("javascript:dopage(3)")){
    		bean.setHasNextPage("Y");
    	}else{
    		bean.setHasNextPage("N");
    	}
    	Element table = doc.select("table").first();
    	Element body = table.select("CENTER").first();
    	Elements inputs = body.getElementsByTag("input");
    	for(Element input : inputs){
    		String value = input.attr("name");
    		if(value.equalsIgnoreCase("pagenum"))
    			bean.setPageNum(input.attr("value"));
    		else if(value.equalsIgnoreCase("pagesize"))
    			bean.setPageSize(input.attr("value"));
    		else if(value.equalsIgnoreCase("sum"))
    			bean.setSum(input.attr("value"));
    		else if(value.equalsIgnoreCase("countpage"))
    			bean.setCountPage(input.attr("value"));
    	}
    	bean.setOfficialCookies(cookies);
    	return bean;
    }
    
    private List<?> retrieveTrademarks(Document doc){
    	if(doc == null){
    		return null;
    	}
    	
    	List<Object> resultRows = new ArrayList();
        resultRows.add(retrievePageLvlInfo(doc));
        
    	Element table = doc.select("table").first();
        Elements tableRows = table.getElementsByTag("tr");
        Element subTable = null;
        for(Element tr : tableRows){
        	Element tableData = tr.select("td").first();
        	if(null != tableData.select("table") && !tableData.select("table").isEmpty()){
        		subTable = tableData.select("table").first();
        	}
        }
        
        Elements subTableRows = subTable.getElementsByTag("tr");
        boolean titleRow = true;
        
        for(Element tr : subTableRows){
        	if(titleRow){
        		titleRow = false;
        	}else{
        		Elements table_data = tr.select("td");
        		TrademarkBean trademark = new TrademarkBean();
        		int count_data=1;
        		for(Element td : table_data){
        			switch(count_data){
        			case 1:
        				break;
        			case 2:
        				break;
        			case 3:
        				String regNum = td.select("a").text();
        				trademark.setRegNum(regNum);
        				String detailLinkJS = td.select("a").attr("href");
        				String detailLink = detailLinkJS.substring(detailLinkJS.indexOf("'"), detailLinkJS.indexOf(","));
        				detailLink = detailLink.replaceAll("'", "");
        				trademark.setDetailLink(detailLink);
        				break;
        			case 4:
        				String categoryNum = td.select("a").text();
        				trademark.setCategoryNum(categoryNum);
        				break;
        			case 5:
        				String name = td.select("a").text();
        				trademark.setName(name);
        				break;
        			}
        			
        			count_data++;
        		}
        		resultRows.add(trademark);
        	}
		}
        return resultRows;
    }
    
    public List<?> getSimilarNextPageResult(TrademarkRequest trademarkRequest){
    	try{
    		cookies = trademarkRequest.getOfficialCookies();
    		String strUrl = "http://sbcx.saic.gov.cn:9080/tmois/wsjscx_getSlectListBySys.xhtml";
    		HttpPost httpPost = new HttpPost(strUrl);
    		httpPost.setHeader("Cookie", cookies);
    		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
    		StringBuilder postData = new StringBuilder("pagenum=");
    		postData.append(trademarkRequest.getPageNum());
    		postData.append("&pagesize=");
    		postData.append(trademarkRequest.getPageSize());
    		postData.append("&sum=");
    		postData.append(trademarkRequest.getSum());
    		postData.append("&countpage=");
    		postData.append(trademarkRequest.getCountPage());
    		postData.append("&goNum=");
    		postData.append(Integer.valueOf(trademarkRequest.getPageNum())-1);
    		postData.append("&type=");
    		postData.append(trademarkRequest.getSearchMethod());
    		postData.append("&intcls=");
    		postData.append(trademarkRequest.getCategoryNumber());
    		postData.append("&content=");
    		postData.append(trademarkRequest.getSearchContent().toUpperCase());
    		postData.append("&si=&sf=");
    		StringEntity se = new StringEntity(postData.toString());
    		httpPost.setEntity(se);
    		HttpResponse response = client.execute(httpPost);
    		String result = EntityUtils.toString(response.getEntity());
            
    		Document doc = Jsoup.parse(result);
            return retrieveTrademarks(doc);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public List<?> getSimilarDetailResult(TrademarkRequest trademarkRequest){
    	try{
    		cookies = trademarkRequest.getOfficialCookies();
    		String code = verificationCodeProcessing();
    		String strUrl = "http://sbcx.saic.gov.cn:9080" + trademarkRequest.getDetailLink() + "&codeShow=" + code;
    		HttpGet request = new HttpGet(strUrl);
            request.setHeader("Cookie", cookies);
            HttpResponse response = client.execute(request);
            BufferedReader rd2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line2 = "";
            while ((line2 = rd2.readLine()) != null) {
                result.append(line2);
            }
            return null;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private String verificationCodeProcessing(){
    	String url_image = "http://sbcx.saic.gov.cn:9080/tmois/wszhcx_getCodeImage.xhtml";

    	try{
	        HttpGet request_image = new HttpGet(url_image);
	        request_image.setHeader("Cookie", cookies);
	        String code = "";
	        int count=0;
	        while(count++ < 10) {
	            HttpResponse response_image = client.execute(request_image);
	            HttpEntity entity_image = response_image.getEntity();
	            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity_image);
	
	            InputStream instream_image = bufHttpEntity.getContent();
	            BufferedImage img = ImageIO.read(instream_image);
	            code = ImageRecognize.getCodeFromImage(img);
	
	            String strUrlnew = "http://sbcx.saic.gov.cn:9080/tmois/wszhcx_getImage.xhtml?inputCode=";
	            strUrlnew += code;
	
	            HttpGet request2 = new HttpGet(strUrlnew);
	            request2.setHeader("Cookie", cookies);
	
	            HttpResponse response2 = client.execute(request2);
	            BufferedReader rd2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
	            StringBuffer result2 = new StringBuffer();
	            String line2 = "";
	            while ((line2 = rd2.readLine()) != null) {
	                result2.append(line2);
	            }
	
	            if(result2.toString().equalsIgnoreCase("true")){
	                break;
	            }
	        }
	        return code;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public void setCookies(String cookies){
        String cook = cookies.substring(cookies.indexOf("JSESSIONID"));
        String[] cookie = cook.split(";");
        cookie[0].replace(";", "");
        this.cookies = cookie[0].trim();
    }
    public void addCookies(String cookies){
        cookies = cookies.substring(cookies.indexOf("SANGFOR_AD_TMOIS"));
        String[] cookie = cookies.split(";");
        this.cookies += ";"+cookie[0];
    }
}
