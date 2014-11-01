package com.moiavenue.ui;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.parser.MoiAvenueParser;
import com.moiavenue.reqmodel.PostFeedbackReq;
import com.moiavenue.respmodel.CommonResponse;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class FeedBackFragment extends Fragment {

	private Button mSend;
	private EditText mComment;

	public FeedBackFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_feedback, container,
				false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initComponents(view);
	}

	private void initComponents(View view) {

		mComment = (EditText) view.findViewById(R.id.enter_feedback);
		mSend = (Button) view.findViewById(R.id.btn_send_comment);

		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sendPostFeedbackRequest(mComment.getText().toString().trim());

			}

		});
	}

	private void sendPostFeedbackRequest(String trim) {

		PostFeedbackReq commonReq = new PostFeedbackReq();
		commonReq.setAction(AppConstants.POST_FEEDBACK);
		commonReq.setPost_id(8);
		commonReq.setFeedback_message(trim);
		commonReq.setPoster_id("1");
		commonReq.setUser_id("1");

		WebserviceManager.getResponseForRequest(getActivity(), commonReq,
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
													getActivity(),
													""
															+ loginResponse
																	.getErrorMessage(),
													Toast.LENGTH_LONG).show();

										}
									});

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(VolleyError error) {
						Toast.makeText(getActivity(), "Error in Response",
								Toast.LENGTH_LONG).show();
					}
				});

	}

}
