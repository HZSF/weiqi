package com.weiwei.svr.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IHealthyDAO;
import com.weiwei.svr.manage.IHealthyManager;

@Service
public class HealthyManagerImpl implements IHealthyManager {

	@Autowired
	private IHealthyDAO healthyDAO;
	
	public String insertNewHeathCheckBooking(String username) {
		return healthyDAO.insertNewHeathCheckBooking(username);
	}

	public void cancelHealthCheckBooking(String username) {
		healthyDAO.cancelHealthCheckBooking(username);
	}

	public boolean getHealthCheckIsApplied(String username) {
		return healthyDAO.getHealthCheckIsApplied(username);
	}

}
