package com.moiavenue.respmodel;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompanyNewsData {

	@JsonProperty("type")
	private String type;

	@JsonProperty("posted_person_name")
	private String postedPersonName;

	@JsonProperty("posted_person_profile_image_url")
	private String postedPersonProfileImageUrl;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	@JsonProperty("post_id")
	private String postId;

	@JsonProperty("poster_id")
	private String posterId;

	@JsonProperty("posted_time")
	private String postedTime;

	@JsonProperty("posted_file_url")
	private String postedFileUrl;

	@JsonProperty("posted_file_message")
	private String postedFileMessage;

	@JsonProperty("previous_comments")
	private ArrayList<CommentsDataDetails> previousCommentsList = new ArrayList<CommentsDataDetails>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostedPersonName() {
		return postedPersonName;
	}

	public void setPostedPersonName(String postedPersonName) {
		this.postedPersonName = postedPersonName;
	}

	public String getPostedPersonProfileImageUrl() {
		return postedPersonProfileImageUrl;
	}

	public void setPostedPersonProfileImageUrl(
			String postedPersonProfileImageUrl) {
		this.postedPersonProfileImageUrl = postedPersonProfileImageUrl;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public String getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
	}

	public String getPostedFileUrl() {
		return postedFileUrl;
	}

	public void setPostedFileUrl(String postedFileUrl) {
		this.postedFileUrl = postedFileUrl;
	}

	public String getPostedFileMessage() {
		return postedFileMessage;
	}

	public void setPostedFileMessage(String postedFileMessage) {
		this.postedFileMessage = postedFileMessage;
	}

	public ArrayList<CommentsDataDetails> getPreviousCommentsList() {
		return previousCommentsList;
	}

	public void setPreviousCommentsList(
			ArrayList<CommentsDataDetails> previousCommentsList) {
		this.previousCommentsList = previousCommentsList;
	}
}
