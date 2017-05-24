package com.weiwei.svr.dao;

public interface IInspectionTextileDAO {
	public String insertNewTextileInspectionBooking(String username) ;
	public void cancelBookedTextileInspection(String username);
}
