package com.weiwei.admin.processor;

import java.util.Map;
import java.util.Random;

import com.ruanwei.interfacej.SmsClientSend;
import com.weiwei.admin.common.base.Constants;
import com.weiwei.admin.common.request.RegisterFormSubmitRequest;
import com.weiwei.admin.validation.RegisterFormValidator;
import com.weiwei.service.processors.base.BaseProcessor;

public class RegisterSubmitProcessor extends BaseProcessor{
	
	private RegisterFormSubmitRequest request;
	public String result;

	@Override
	protected String executeProcess(Map scopes) {
		RegisterFormValidator validator = new RegisterFormValidator();
		boolean hasError = validator.validateMandatoryFields(request);
		if(!hasError)
			hasError = validator.validateExistingPhone(request);
		if(!hasError)
			hasError = validator.validateExistingCustomer(request);
		if(hasError){
			String errorCode = validator.getErrorCode();
			return Constants.EVENT_FAIL + "_" + errorCode;
		}else{
			Random random = new Random();
	    	StringBuilder sb = new StringBuilder();
	    	for(int i=0; i<6; i++){
	    		sb.append(random.nextInt(10));
	    	}
	    	
	    	result = SmsClientSend.sendSms(Constants.SMS_SERVICE_URL, Constants.SMS_SERVICE_USERID, Constants.SMS_SERVICE_ACCOUNT, 
	    			Constants.SMS_SERVICE_PASSWORD, request.getPhoneNumber(), Constants.SMS_SERVICE_CONTENT+sb.toString()+Constants.SMS_SERVICE_SIGNATURE);
	    	
	 //   	System.out.println(result);
	    	
	    	return sb.toString();
		}
	}

	public RegisterFormSubmitRequest getRequest() {
		return request;
	}

	public void setRequest(RegisterFormSubmitRequest request) {
		this.request = request;
	}
}
