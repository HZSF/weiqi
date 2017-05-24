package com.weiwei.service.controller.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.admin.beans.CustomerInfoBean;
import com.weiwei.admin.common.request.CustomerInfoUpdateAreaRequest;
import com.weiwei.admin.common.request.CustomerInfoUpdateImageRequest;
import com.weiwei.admin.common.request.CustomerInfoUpdateRequest;
import com.weiwei.admin.processor.*;
import com.weiwei.centreservice.beans.CourseBean;
import com.weiwei.centreservice.beans.FinancialAppliedCreditLoanBean;
import com.weiwei.centreservice.beans.FinancialAppliedLendingBean;
import com.weiwei.centreservice.beans.FinancialAppliedLoanBean;
import com.weiwei.centreservice.beans.InspectionIsAppliedBean;
import com.weiwei.centreservice.beans.PropertyOnLendBean;
import com.weiwei.centreservice.beans.PropertyOnSellBean;
import com.weiwei.centreservice.common.request.CommentAddRequest;
import com.weiwei.centreservice.common.request.CommentLikeUnlikeRequest;
import com.weiwei.centreservice.common.request.CourseRequest;
import com.weiwei.centreservice.common.request.CreditLoanRequest;
import com.weiwei.centreservice.common.request.OnLendingFormSubmitRequest;
import com.weiwei.centreservice.common.request.PropertyAddFavoriteRequest;
import com.weiwei.centreservice.common.request.PropertyAddLendRequest;
import com.weiwei.centreservice.common.request.PropertyAddSellRequest;
import com.weiwei.centreservice.common.request.PropertyCancelFavoriteRequest;
import com.weiwei.centreservice.common.request.PropertyLendListRequest;
import com.weiwei.centreservice.common.request.PropertySellListRequest;
import com.weiwei.centreservice.secure.processor.*;
import com.weiwei.intellectual.beans.PatentAnnualFeeMonitorBean;
import com.weiwei.intellectual.beans.TrademarkBean;
import com.weiwei.intellectual.common.request.PtntAddAchiTransRequest;
import com.weiwei.intellectual.common.request.PtntAddAnnFeeMoRequest;
import com.weiwei.intellectual.common.request.TrademarkAddMonitorRequest;
import com.weiwei.intellectual.common.request.TrademarkAddSellRequest;
import com.weiwei.intellectual.secure.processor.*;
import com.weiwei.parent.common.base.Constants;
import com.weiwei.service.common.response.GeneralServiceResponse;
import com.weiwei.service.domain.DomainCredential;
import com.weiwei.service.domain.DomainUser;
import com.weiwei.service.processors.base.BaseProcessor;

@RestController("memberActivityController")
@RequestMapping("/sweiwei")
public class MemberActivityController {
	
	private Map<String, Object> scopes = new HashMap<String, Object>();
	private BaseProcessor processor;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
    public String test(){
    	System.out.println("memberActivityController tesing");
    	return "Hello " + ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() + "!";
    }
	
	@RequestMapping(value="/ping")
	public String ping(){
		return "weiwei";
	}
	
	@RequestMapping(value="/verifyRegister")
	public String verifyRegistration(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		DomainCredential dc = (DomainCredential)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		scopes.put(Constants.PASSWORD, dc.getPassword());
		scopes.put(Constants.PHONENUMBER, dc.getPhoneNumber());
		scopes.put(Constants.COMPANYNAME, dc.getCompanyName());
		processor = new RegisterPostProcessor();
		String result = processor.doProcess(scopes);
		SecurityContextHolder.clearContext();
		return result;
	}
	
	@RequestMapping(value="/logout")
	public String logout(){
		SecurityContextHolder.clearContext();
		return Constants.EVENT_SUCCESS;
	}
	
	@RequestMapping(value="/getUserInfo")
	public CustomerInfoBean getUserInfo(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new CustomerInfoRetrieveProcessor();
		processor.doProcess(scopes);
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			return (CustomerInfoBean)scopes.get(Constants.SERVICE_RESPONSE);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/setUserInfo")
	public String setUserInfo(@RequestBody CustomerInfoUpdateRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST, serviceRequest);
		processor = new CustomerInfoUpdateProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/setUserInfoArea")
	public String setUserInfoArea(@RequestBody CustomerInfoUpdateAreaRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST, serviceRequest);
		processor = new CustInfoUpdAreaProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/setUserInfoImg")
	public String setUserInfoImg(final HttpServletRequest request, final HttpServletResponse response){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		InputStream fileContent = null;
		CustomerInfoUpdateImageRequest imgRequest = new CustomerInfoUpdateImageRequest();
		try {
			Part filePart = request.getPart("file");
			long size = filePart.getSize();
			fileContent = filePart.getInputStream();
			imgRequest.setImg_data(fileContent);
			imgRequest.setSize(size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
		}
		scopes.put(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST, imgRequest);
		processor = new CustInfoUpdImgProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="getUserInfoImg")
	public byte[] getUserInfoImg(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new CustInfoGetImgProcessor();
		processor.doProcess(scopes);
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			byte[] responseByte = (byte[])scopes.get(Constants.SERVICE_RESPONSE);
			return responseByte;
		}
		return null;
	}
	
	@RequestMapping(value="/trademark/add/monitor")
	public String addTrademarkMonitorService(@RequestBody TrademarkAddMonitorRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.TRADEMARK_ADD_MONITOR_REQUEST, serviceRequest);
		processor = new SecAddTrademarkMonitorProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/trademark/get/monitor")
	public GeneralServiceResponse<TrademarkBean> retrieveMonitoringTrademarkService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecRetrieveTrdmkMonProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<TrademarkBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/trademark/add/sell")
	public String addTrademarkSellService(@RequestBody TrademarkAddSellRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.TRADEMARK_ADD_SELL_REQUEST, serviceRequest);
		processor = new SecAddTrdmkSellProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/trademark/delete/monitor")
	public String deleteTrademarkMonitorService(@RequestBody String regNum){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.TRADEMARK_REQUEST, regNum);
		processor = new SecDeleteTrdmkMonProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/AnnualFeeMonitor")
	public GeneralServiceResponse<PatentAnnualFeeMonitorBean> retrieveMonitoringPatentService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecRetrievePatentAnnualFeeMonitorProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PatentAnnualFeeMonitorBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/patent/add/AnnFeeMon")
	public String addPatentAnnFeeMonitorService(@RequestBody PtntAddAnnFeeMoRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PATENT_ADD_ANN_FEE_MONITOR_REQUEST, serviceRequest);
		processor = new SecAddPtntAnnFeeMonitorProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/delete/AnnFeeMon")
	public String deletePatentAnnFeeMonitorService(@RequestBody String patentId){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PATENTID_REQUEST, patentId);
		processor = new SecDeletePtntAnnFeeMonitorProcesor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/delegate/AnnFeeMon")
	public String SecAddPtntAnnFeeMonDetailProcessor(@RequestBody String patentId){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PATENTID_REQUEST, patentId);
		processor = new SecAddPtntAnnFeeMonDetailProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/add/AchieveTrans")
	public String SecAddPtntAchiTransProcessor(@RequestBody PtntAddAchiTransRequest request){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PATENT_ADD_ARCHIEVE_TRANS_REQUEST, request);
		processor = new SecAddPtntAchiTransProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/registerCourse")
	public String registerCourseService(@RequestBody CourseRequest serviceRequest){
		scopes.put(Constants.CS_TRAIN_COURSE_SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecRegisterCourseProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/retrieveRegisteredCourse")
	public GeneralServiceResponse<CourseBean> retrieveRegistedCourseService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecRetrieveRegistedCourseProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CourseBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/removeRegisteredCourse")
	public String removeRegisteredCourseService(@RequestBody String courseID){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.MEMBER_COURSE_DEREGISTER_SERVICE_REQUEST_COURSEID, courseID);
		processor = new SecDeregisterCourseProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/finance/loan/submitform")
	public String loanService(@RequestBody CreditLoanRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.CS_FINANCE_LOAN_SERVICE_REQUEST, serviceRequest);
		processor = new SecLoanApplicationProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/finance/onlend/submitform")
	public String onlendService(@RequestBody OnLendingFormSubmitRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.CS_FINANCE_ONLEND_SERVICE_REQUEST, serviceRequest);
		processor = new SecOnlendApplicationProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/finance/loan/applied")
	public GeneralServiceResponse<FinancialAppliedLoanBean> appliedLoanService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecAppliedLoanProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<FinancialAppliedLoanBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/finance/loan/creditLoanApplied")
	public FinancialAppliedCreditLoanBean appliedCreditLoanService(@RequestBody String loanId){
		scopes.put(Constants.MEMBER_FINANCE_CREDIT_LOAN_SERVICE_REQUEST_LOANID, loanId);
		processor = new SecAppliedCreditLoanProcessor();
		processor.doProcess(scopes);
		FinancialAppliedCreditLoanBean serviceResponse = new FinancialAppliedCreditLoanBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (FinancialAppliedCreditLoanBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/finance/loan/lendingApplied")
	public FinancialAppliedLendingBean appliedLendingService(@RequestBody String lendId){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.MEMBER_FINANCE_LENDING_SERVICE_REQUEST_LOANID, lendId);
		processor = new SecAppliedLendingProcessor();
		processor.doProcess(scopes);
		FinancialAppliedLendingBean serviceResponse = new FinancialAppliedLendingBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (FinancialAppliedLendingBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/finance/cancel/creditLoan")
	public String cancelAppliedCreditLoanService(@RequestBody String loanId){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.MEMBER_FINANCE_CANCEL_CREDIT_LOAN_SERVICE_REQUEST_LOANID, loanId);
		processor = new SecCancelAppliedCreditLoanProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/finance/cancel/lend")
	public String cancelAppliedLendingService(@RequestBody String lendId){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.MEMBER_FINANCE_CANCEL_LEND_SERVICE_REQUEST_LOANID, lendId);
		processor = new SecCancelAppliedLendingProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/book/wood")
	public String woodInspectionBookService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecBookWoodInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/book/kids")
	public String kidsInspectionBookService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecBookKidsInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/book/textile")
	public String textileInspectionBookService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecBookTextileInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/booked")
	public InspectionIsAppliedBean inspectionBookedQueryService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecBookedInspectionProcessor();
		processor.doProcess(scopes);
		InspectionIsAppliedBean serviceResponse = new InspectionIsAppliedBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (InspectionIsAppliedBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/inspection/cancel/wood")
	public String cancelAppointedWoodInspectionService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecCancelAppointedWoodInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/cancel/kids")
	public String cancelAppointedKidsInspectionService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecCancelAppointedKidsInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/inspection/cancel/textile")
	public String cancelAppointedTextileInspectionService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecCancelAppointedTextileInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/healthcheck/book")
	public String healthcheckBookService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecBookHealthCheckProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/healthcheck/booked")
	public String healthcheckBookedQueryService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecHealthCheckBookedProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/centreservice/healthcheck/cancelbook")
	public String cancelBookedHealthcheckService(){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		processor = new SecCancelBookedHealthCheckProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/comment/announce/add")
	/*public String addAnnounceCommentService(@RequestBody CommentAnnounceRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.COMMENT_ANNOUNCE_ADD_REQUEST, serviceRequest);
		processor = new SecAddCommentProcessor();
		return processor.doProcess(scopes);
	}*/
	public String addAnnounceCommentService(@RequestBody CommentAddRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.COMMENT_ANNOUNCE_ADD_REQUEST, serviceRequest);
		processor = new SecAddNewCommentProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/comment/announce/likeUnlike")
	public String likeUnlikeCommentService(@RequestBody CommentLikeUnlikeRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		processor = new SecLikeCommentProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/sell")
	public String addPropertySellService(@RequestBody PropertyAddSellRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyAddSellProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/lend")
	public String addPropertyLendService(@RequestBody PropertyAddLendRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyAddLendProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/favorites")
	public String addPropertyFavorites(@RequestBody PropertyAddFavoriteRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyAddFavorites();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/cancel/favorites")
	public String cancelPropertyFavorites(@RequestBody PropertyCancelFavoriteRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyCancelFavorites();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/fetch/favorites/sell")
	public GeneralServiceResponse<PropertyOnSellBean> propertyFavoriteSellListService(@RequestBody PropertySellListRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyFavoriteSellListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnSellBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/favorites/lend")
	public GeneralServiceResponse<PropertyOnLendBean> propertyFavoriteSellListService(@RequestBody PropertyLendListRequest serviceRequest){
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new SecPropertyFavoriteLendListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnLendBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
}
