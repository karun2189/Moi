package com.moiavenue.reqmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegistrationReq extends CommonReq implements Serializable {

	private String pass;

	private String email;

	private String name;
	
	private String number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

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
