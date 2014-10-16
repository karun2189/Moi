package com.moiavenue.respmodel;

import org.codehaus.jackson.annotate.JsonProperty;

public class LoginResponse extends CommonResponse {

	@JsonProperty("userid")
	private String userId;
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("profile_image_url")
	private String profileImageUrl;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}
