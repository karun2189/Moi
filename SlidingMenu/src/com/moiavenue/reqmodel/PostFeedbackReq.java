package com.moiavenue.reqmodel;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class PostFeedbackReq extends CommonReq implements Serializable {

	private int post_id;
	private String user_id;
	private String poster_id;
	private String feedback_message;

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPoster_id() {
		return poster_id;
	}

	public void setPoster_id(String poster_id) {
		this.poster_id = poster_id;
	}

	public String getFeedback_message() {
		return feedback_message;
	}

	public void setFeedback_message(String feedback_message) {
		this.feedback_message = feedback_message;
	}

}
