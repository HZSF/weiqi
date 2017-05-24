package com.weiwei.admin.validation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.admin.common.request.RegisterFormSubmitRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.svr.manage.ICustomerManager;

public class RegisterFormValidator {
	
	public String errorCode = "000";
	
	public boolean validateMandatoryFields(RegisterFormSubmitRequest data){
		boolean error = false;
		if(data == null)
			error = true;
		else if(StringUtility.isEmptyString(data.getUsername()) || StringUtility.isEmptyString(data.getPassword()) || StringUtility.isEmptyString(data.getPhoneNumber())
				|| StringUtility.isEmptyString(data.getCompanyName())){
			error = true;
		}
		return error;
	}
	
	public boolean validateExistingCustomer(RegisterFormSubmitRequest data){
		boolean existingCustomer = false;
		String username = data.getUsername();
		ApplicationContext ctx;
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		if(customerManager.existingCustomer(username)){
			existingCustomer = true;
			errorCode = Constants.ERROR_002;
		}
		return existingCustomer;
	}
	
	public boolean validateExistingPhone(RegisterFormSubmitRequest data){
		boolean existingPhone = false;
		String mobilePhone = data.getPhoneNumber();
		ApplicationContext ctx;
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		if(customerManager.existingMobilePhone(mobilePhone)){
			existingPhone = true;
			errorCode = Constants.ERROR_003;
		}
		return existingPhone;
	}
	

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
