package com.weiwei.svr.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IInspectionTextileDAO;
import com.weiwei.svr.manage.IInspectionTextileManager;

@Service
public class InspectionTextileManagerImpl implements IInspectionTextileManager {

	@Autowired
	private IInspectionTextileDAO inspectionTextileDAO;
	
	public String insertNewTextileInspectionBooking(String username) {
		return inspectionTextileDAO.insertNewTextileInspectionBooking(username);
	}

	public void cancelBookedTextileInspection(String username) {
		inspectionTextileDAO.cancelBookedTextileInspection(username);
	}

}
