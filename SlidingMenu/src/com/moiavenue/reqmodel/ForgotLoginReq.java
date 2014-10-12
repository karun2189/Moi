package com.moiavenue.reqmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ForgotLoginReq extends CommonReq implements Serializable {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
