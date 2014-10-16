package com.moiavenue.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.parser.MoiAvenueParser;
import com.moiavenue.reqmodel.ForgotLoginReq;
import com.moiavenue.reqmodel.LoginReq;
import com.moiavenue.respmodel.CommonResponse;
import com.moiavenue.respmodel.LoginResponse;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class ActivityForgotPassword extends Activity implements OnClickListener {

	Button btnForgotlogin;
	Button btnCancel;
	EditText etEmailAddress;
	String mEmailAddress;

	Context context = ActivityForgotPassword.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_login);
		init();

	}

	private void init() {
		btnForgotlogin = (Button) findViewById(R.id.forgot_tv);
		btnCancel = (Button) findViewById(R.id.forgot_cancel_tv);
		btnForgotlogin.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}

	private void onValidate() {
		mEmailAddress = etEmailAddress.getText().toString();

		if (mEmailAddress.equals("")) {
			etEmailAddress.setError("Enter the Email Adress");
			etEmailAddress.setFocusable(true);
			etEmailAddress.requestFocus();
		} else {
			callService();
		}
	}

	private void callService() {
		ForgotLoginReq commonReq = new ForgotLoginReq();
		commonReq.setAction(AppConstants.FORGOT_LOGIN_ACTION);
		commonReq.setEmail(mEmailAddress);
		WebserviceManager.getResponseForRequest(this, commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							// if (response.has("status")
							// && (response.getInt("status") ==
							// AppConstants.SUCCESS)) {
							//
							// onSucessForgotLoginResponse();
							// }
							// else if(response.has("message"))
							// {
							// Toast.makeText(ActivityForgotPassword.this,
							// "Error in Response"+response.get("message"),
							// Toast.LENGTH_LONG).show();
							// }

							MoiAvenueParser.getDataObject(
									MoiAvenueUtilities.REG_REQ,
									response.toString(),
									new ResponseListener() {

										@Override
										public void onResponseReceived(
												int mRequestType,
												Object mResponseObject) {
											// TODO Auto-generated method stub
											CommonResponse loginResponse = (CommonResponse) mResponseObject;
											Toast.makeText(
													ActivityForgotPassword.this,
													"succ"
															+ loginResponse
																	.getErrorMessage(),
													Toast.LENGTH_LONG).show();

											onSucessForgotLoginResponse();
										}
									});

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(VolleyError error) {
						Toast.makeText(ActivityForgotPassword.this,
								"Error in Response" + error.getMessage(),
								Toast.LENGTH_LONG).show();
					}
				});
	}

	private void onSucessForgotLoginResponse() {

		// startActivity(new Intent(this, ActivityBaseDrawer.class));
		Toast.makeText(this, "Please Check your mail", Toast.LENGTH_LONG)
				.show();
	}

	public static void hideKeyboard(Activity activity) {
		try {
			if (activity != null) {
				InputMethodManager imm = (InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				IBinder ib = activity.getCurrentFocus().getWindowToken();

				if (imm != null) {
					imm.hideSoftInputFromWindow(ib, 0);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forgot_tv:
			hideKeyboard(this);
			onValidate();
			break;
		case R.id.forgot_cancel_tv:
			hideKeyboard(this);
			finish();
			break;

		}
	}
}
