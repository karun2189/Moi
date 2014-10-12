package com.moiavenue.reqmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompanyNewsReq implements Serializable {

	private String action;
	private String start_row;
	private String end_row;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStart_row() {
		return start_row;
	}

	public void setStart_row(String start_row) {
		this.start_row = start_row;
	}

	public String getEnd_row() {
		return end_row;
	}

	public void setEnd_row(String end_row) {
		this.end_row = end_row;
	}

}
