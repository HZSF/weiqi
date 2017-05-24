package com.weiwei.svr.manage;

public interface IInspectionTextileManager {
	public String insertNewTextileInspectionBooking(String username) ;
	public void cancelBookedTextileInspection(String username);
}
