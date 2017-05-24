package com.weiwei.intellectual.common.request;

public class TrademarkRequest {
	public String searchType;
	public String categoryNumber;
	public String searchContent;
	public String applicationNumber;
	public String searchMethod;
	public String officialCookies;
	public String detailLink;
	
	public String pageNum;
	public String pageSize;
	public String sum;
	public String countPage;
	
	public String getOfficialCookies() {
		return officialCookies;
	}
	public void setOfficialCookies(String officialCookies) {
		this.officialCookies = officialCookies;
	}
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	public void setCategoryNumber(String categoryNumber) {
		this.categoryNumber = categoryNumber;
	}
	public String getSearchType() {
		return searchType;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getSearchMethod() {
		return searchMethod;
	}
	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getCategoryNumber() {
		return categoryNumber;
	}
	public void setCategoryNum(String categoryNum) {
		this.categoryNumber = categoryNum;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getCountPage() {
		return countPage;
	}
	public void setCountPage(String countPage) {
		this.countPage = countPage;
	}
	
	
}
