package com.weiwei.service.domain;

public class DomainCredential {
	private String password;
	private String phoneNumber;
	private String companyName;
	
	public DomainCredential(String pwd, String pn, String comName){
		password = pwd;
		phoneNumber = pn;
		companyName = comName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
