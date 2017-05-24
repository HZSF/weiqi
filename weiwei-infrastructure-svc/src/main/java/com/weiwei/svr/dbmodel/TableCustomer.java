package com.weiwei.svr.dbmodel;

public class TableCustomer {
	public int id;
	public String contactsName;
	public String userName;
	public String mobilePhonenumber;
	public String phoneNumber;
	public String email;
	public String companyName;
	public String companyAddress;
	public String isVIP; //'Y' or 'N'
	public String isAnonymous;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhonenumber() {
		return mobilePhonenumber;
	}
	public void setMobilePhonenumber(String mobilePhonenumber) {
		this.mobilePhonenumber = mobilePhonenumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIsVIP() {
		return isVIP;
	}
	public void setIsVIP(String isVIP) {
		this.isVIP = isVIP;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
}
