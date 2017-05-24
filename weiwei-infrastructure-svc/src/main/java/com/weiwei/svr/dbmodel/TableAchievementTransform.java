package com.weiwei.svr.dbmodel;

import java.sql.Timestamp;

public class TableAchievementTransform {
	public int id;
	public String patent_id;
	public int customer_id;
	public String title;
	public Timestamp apply_date;
	public String type;
	public double price;
	public int transform_status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPatent_id() {
		return patent_id;
	}
	public void setPatent_id(String patent_id) {
		this.patent_id = patent_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getApply_date() {
		return apply_date;
	}
	public void setApply_date(Timestamp apply_date) {
		this.apply_date = apply_date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTransform_status() {
		return transform_status;
	}
	public void setTransform_status(int transform_status) {
		this.transform_status = transform_status;
	}
	
}
