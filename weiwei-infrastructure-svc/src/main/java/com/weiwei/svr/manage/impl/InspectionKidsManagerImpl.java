package com.weiwei.svr.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IInspectionKidsDAO;
import com.weiwei.svr.manage.IInspectionKidsManager;

@Service
public class InspectionKidsManagerImpl implements IInspectionKidsManager {

	@Autowired
	private IInspectionKidsDAO inspectionKidsDAO;
	
	public String insertNewKidsInspectionBooking(String username) {
		return inspectionKidsDAO.insertNewKidsInspectionBooking(username);
	}

	public void cancelBookedKidsInspection(String username) {
		inspectionKidsDAO.cancelBookedKidsInspection(username);
	}

}
