package com.weiwei.svr.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.utils.Constants;
import com.weiwei.svr.dao.IInspectionDAO;
import com.weiwei.svr.dbmodel.TableUserInspection;
import com.weiwei.svr.manage.IInspectionManager;

@Service
public class InspectionManagerImpl implements IInspectionManager {

	@Autowired
	private IInspectionDAO inspectionDAO;
	
	public boolean[] getInspectionIsApplied(String username) {
		boolean[] isApplied = new boolean[3];
		List<TableUserInspection> resultList = inspectionDAO.getBookedInspectionByUsername(username);
		if(resultList == null || resultList.size() < 1){
			return isApplied;
		}
		TableUserInspection table = resultList.get(0);
		if(table.getIsApplied_wood() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_wood())){
			isApplied[0] = true;
		}
		if(table.getIsApplied_kidsClothe() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_kidsClothe())){
			isApplied[1] = true;
		}
		if(table.getIsApplied_textile() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_textile())){
			isApplied[2] = true;
		}
		return isApplied;
	}

}
