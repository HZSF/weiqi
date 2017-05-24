package com.weiwei.intellectual.beans;

public class PatentAchievementTransformBean {
	public int id;
	public String patent_id;
	public String title;
	public String apply_date;
	public double price;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
