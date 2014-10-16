package com.moiavenue.dialog;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.adapter.CommentsAdapter;
import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.parser.MoiAvenueParser;
import com.moiavenue.reqmodel.LoginReq;
import com.moiavenue.reqmodel.PostCommentReq;
import com.moiavenue.reqmodel.PostFeedbackReq;
import com.moiavenue.respmodel.CommonResponse;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.respmodel.LoginResponse;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;
import com.moiavenue.ui.ActivityLogin;

public class FeedbackDialog {


	CommentsAdapter mCommentAdapter;
	private EditText mComment;
	private Button mSend;
	private ImageView mClose;
	private Dialog mDialog;
	private Context mContext;

	public void showFeedback(final Context context) {
		mContext = context;
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_feedback, null);
		dialog.setContentView(layout);

		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.BOTTOM;

		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		mComment = (EditText) dialog.findViewById(R.id.enter_feedback);

		mSend = (Button) dialog.findViewById(R.id.btn_send_comment);

		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sendPostFeedbackRequest(mComment.getText().toString().trim());

			}

		});
		mDialog = dialog;

		mClose = (ImageView) dialog.findViewById(R.id.close_comment);

		mClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();

			}
		});
		dialog.show();

	}

	private void sendPostFeedbackRequest(String trim) {

		PostFeedbackReq commonReq = new PostFeedbackReq();
		commonReq.setAction(AppConstants.POST_FEEDBACK);
		commonReq.setPost_id(8);
		commonReq.setFeedback_message(trim);
		commonReq.setPoster_id("1");
		commonReq.setUser_id("1");

		WebserviceManager.getResponseForRequest(mContext, commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							MoiAvenueParser.getDataObject(
									MoiAvenueUtilities.POST_FEEDBACK,
									response.toString(),
									new ResponseListener() {

										@Override
										public void onResponseReceived(
												int mRequestType,
												Object mResponseObject) {
											// TODO Auto-generated method stub
											CommonResponse loginResponse = (CommonResponse) mResponseObject;
//
											Toast.makeText(
													mContext,
													""+loginResponse
																	.getErrorMessage(),
													Toast.LENGTH_LONG).show();
											
											mDialog.dismiss();

										}
									});

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(VolleyError error) {
						Toast.makeText(mContext, "Error in Response",
								Toast.LENGTH_LONG).show();
					}
				});

	}

}
