package com.weiwei.centreservice.common.request;

public class PropertyAddFavoriteRequest {
	public int id;
	public int category; // sell:1, lend:2
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
