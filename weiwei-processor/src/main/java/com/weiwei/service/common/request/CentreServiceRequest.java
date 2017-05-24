package com.weiwei.service.common.request;

public class CentreServiceRequest<T> {
	protected String messageName;
	private T messageBody;
	
	public void setMessageName(String s){
		messageName = s;
	}
	public String getMessageName(){
		return messageName;
	}
	public void setMessageBody(T t){
		messageBody = t;
	}
	public T getMessageBody(){
		return messageBody;
	}
}
