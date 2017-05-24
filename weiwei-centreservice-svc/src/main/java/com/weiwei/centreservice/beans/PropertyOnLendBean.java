package com.weiwei.centreservice.beans;

import com.weiwei.svr.dbmodel.PropertyLendJoinTable;

public class PropertyOnLendBean extends PropertyLendJoinTable{
	public String submit_date;

	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}
}
