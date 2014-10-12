package com.moiavenue.reqmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommonReq implements Serializable{
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
