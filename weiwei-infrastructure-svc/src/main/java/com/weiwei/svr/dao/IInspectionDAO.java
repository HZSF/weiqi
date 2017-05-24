package com.weiwei.svr.dao;

import java.util.List;

import com.weiwei.svr.dbmodel.TableUserInspection;

public interface IInspectionDAO {
	public List<TableUserInspection> getBookedInspectionByUsername(String username);
}
