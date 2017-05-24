package com.weiwei.intellectual.beans;

public class TrademarkNamingEscapeBean {
	public String area;
	public String areaNum;
	public TrademarkNamingEscapeBean(){
		
	}
	public TrademarkNamingEscapeBean(String area, String areaNum) {
		super();
		this.area = area;
		this.areaNum = areaNum;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	
}
