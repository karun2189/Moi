package com.moiavenue.respmodel;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompanyNewsList {
	@JsonProperty("message")
	private String message;
	@JsonProperty("status")
	private int status;
	@JsonProperty("end_of_record")
	private String endOfRecord;

	@JsonProperty("news_list")
	private ArrayList<CompanyNewsData> companyNewsList = new ArrayList<CompanyNewsData>();

	public ArrayList<CompanyNewsData> getCompanyNewsList() {
		return companyNewsList;
	}

	public void setCompanyNewsList(ArrayList<CompanyNewsData> companyNewsList) {
		this.companyNewsList = companyNewsList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEndOfRecord() {
		return endOfRecord;
	}

	public void setEndOfRecord(String endOfRecord) {
		this.endOfRecord = endOfRecord;
	}
}
