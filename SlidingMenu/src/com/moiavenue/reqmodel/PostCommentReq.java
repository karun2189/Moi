package com.moiavenue.reqmodel;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class PostCommentReq extends CommonReq implements Serializable {
	private String commenter_name;
	private String commenter_id;
	private String commented_message;
	private String commented_file;
	private int post_id;

	public String getCommenter_name() {
		return commenter_name;
	}

	public void setCommenter_name(String commenter_name) {
		this.commenter_name = commenter_name;
	}

	public String getCommenter_id() {
		return commenter_id;
	}

	public void setCommenter_id(String commenter_id) {
		this.commenter_id = commenter_id;
	}

	public String getCommented_message() {
		return commented_message;
	}

	public void setCommented_message(String commented_message) {
		this.commented_message = commented_message;
	}

	public String getCommented_file() {
		return commented_file;
	}

	public void setCommented_file(String commented_file) {
		this.commented_file = commented_file;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

}
