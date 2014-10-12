package com.moiavenue.reqmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginReq extends CommonReq implements Serializable {
	
	private String pass;
	private String email;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
