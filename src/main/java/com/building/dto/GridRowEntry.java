package com.building.dto;

import java.util.ArrayList;
import java.util.List;

public class GridRowEntry {
	private long id;
	private final List<String> data = new ArrayList<String>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data.clear();
		this.data.addAll(data);
	}
	public void addData(String str) {
		this.data.add(str);
	}


}
