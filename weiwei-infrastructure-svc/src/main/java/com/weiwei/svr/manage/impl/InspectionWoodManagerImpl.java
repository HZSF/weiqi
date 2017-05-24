package com.weiwei.svr.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IInspectionWoodDAO;
import com.weiwei.svr.manage.IInspectionWoodManager;

@Service
public class InspectionWoodManagerImpl implements IInspectionWoodManager {

	@Autowired
	private IInspectionWoodDAO inspectionWoodDAO;
	
	public String insertNewWoodInspectionBooking(String username) {
		return inspectionWoodDAO.insertNewWoodInspectionBooking(username);
	}

	public void cancelBookedWookInspection(String username) {
		inspectionWoodDAO.cancelBookedWookInspection(username);
	}

}
