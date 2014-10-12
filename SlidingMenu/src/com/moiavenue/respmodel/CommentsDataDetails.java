package com.moiavenue.respmodel;

import org.codehaus.jackson.annotate.JsonProperty;

public class CommentsDataDetails {

	@JsonProperty("commenter_name")
	private String commenterName;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("commenter_image_url")
	private String commenterImage;

	@JsonProperty("commenter_comment_file_url")
	private String commenterCommentFile;

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommenterImage() {
		return commenterImage;
	}

	public void setCommenterImage(String commenterImage) {
		this.commenterImage = commenterImage;
	}

	public String getCommenterCommentFile() {
		return commenterCommentFile;
	}

	public void setCommenterCommentFile(String commenterCommentFile) {
		this.commenterCommentFile = commenterCommentFile;
	}
}
