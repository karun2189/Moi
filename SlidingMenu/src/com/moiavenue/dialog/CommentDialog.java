package com.moiavenue.dialog;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.adapter.CommentsAdapter;
import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.parser.MoiAvenueParser;
import com.moiavenue.reqmodel.LoginReq;
import com.moiavenue.reqmodel.PostCommentReq;
import com.moiavenue.respmodel.CommonResponse;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.respmodel.LoginResponse;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;
import com.moiavenue.ui.ActivityBaseDrawer;
import com.moiavenue.ui.ActivityLogin;
import com.moiavenue.ui.ImageAsyc;

public class CommentDialog {

	private ListView mCommentsList;

	CommentsAdapter mCommentAdapter;
	private EditText mComment;
	private Button mSend;
	private ImageView mClose;
	private Dialog mDialog;
	private Context mContext;
	private TextView mTitle;
	private CompanyNewsData mCompanyNewsData;

	public void showCommentDialog(final Context context,
			CompanyNewsData dataEntityDetails) {
		mContext = context;
		mCompanyNewsData = dataEntityDetails;
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_comments, null);
		dialog.setContentView(layout);

		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.BOTTOM;

		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		mTitle = (TextView) dialog.findViewById(R.id.comments);

		mTitle.setText(dataEntityDetails.getPostedFileMessage());

		if (dataEntityDetails.getPostedFileUrl().length() > 0) {
			final ImageView iv = ((ImageView) dialog
					.findViewById(R.id.posted_image));
			iv.setVisibility(View.VISIBLE);
			
//			ImageRequest ir = new ImageRequest(
//					dataEntityDetails.getPostedFileUrl(),
//					new Response.Listener<Bitmap>() {
//
//						@Override
//						public void onResponse(Bitmap response) {
//
//							iv.setImageBitmap(response);
//
//						}
//					}, 200, 200, null, null);
			
			ImageAsyc imageAsyc = new ImageAsyc(mContext, iv);
			imageAsyc.execute(dataEntityDetails.getPostedFileUrl());

		}

		mComment = (EditText) dialog.findViewById(R.id.enter_comment);
		// mComment.setTypeface(TypefaceSingleton.getInstance().getHelvetica(
		// context));

		mSend = (Button) dialog.findViewById(R.id.btn_send_comment);

		mCommentsList = (ListView) dialog.findViewById(R.id.comments_listView);
		mCommentsList.setAdapter(new CommentsAdapter(context,
				R.layout.adapter_comments, mCompanyNewsData
						.getPreviousCommentsList()));
		// mSend.setTypeface(TypefaceSingleton.getInstance().getHelvetica(context));

		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sendPostCommentRequest(mComment.getText().toString().trim());

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

	private void sendPostCommentRequest(String trim) {

		PostCommentReq commonReq = new PostCommentReq();
		commonReq.setAction(AppConstants.POST_COMMENT);
		// Toast.makeText(
		// mContext,
		// AppConstants.POST_COMMENT + " hi " + trim + " ed"
		// + ActivityLogin.id + "  nam" + ActivityLogin.name
		// + " id " + mCompanyNewsData.getPostId(),
		// Toast.LENGTH_LONG).show();
		commonReq.setCommented_message(trim);
		commonReq.setCommenter_id(ActivityLogin.id);
		commonReq.setCommented_file("byte");
		commonReq.setCommenter_name(ActivityLogin.name);
		commonReq.setPost_id(Integer.parseInt(mCompanyNewsData.getPostId()));

		WebserviceManager.getResponseForRequest(mContext, commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							MoiAvenueParser.getDataObject(
									MoiAvenueUtilities.POST_COMMENT,
									response.toString(),
									new ResponseListener() {

										@Override
										public void onResponseReceived(
												int mRequestType,
												Object mResponseObject) {
											// TODO Auto-generated method stub
											CommonResponse loginResponse = (CommonResponse) mResponseObject;

											((ActivityBaseDrawer) (mContext))
													.refreshFragment();

											// Toast.makeText(
											// mContext,
											// "succ"
											// + loginResponse
											// .getErrorMessage(),
											// Toast.LENGTH_LONG).show();
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
