package com.moiavenue.ui;

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
import com.moiavenue.reqmodel.LoginReq;
import com.moiavenue.respmodel.LoginResponse;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class ActivityLogin extends Activity implements OnClickListener {

	private Button btnLogin;
	private Button btnForgotlogin;
	private Button btnRegistration;
	private EditText etUserName;
	private EditText etPassword;
	private String mUserName;
	private String mPassword;
	public static String name = "test";
	public static String id = "1";
	

	// private Context context = ActivityLogin.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	private void init() {
		btnLogin = (Button) findViewById(R.id.login_login_tv);
		btnForgotlogin = (Button) findViewById(R.id.login_forgot_tv);
		etUserName = (EditText) findViewById(R.id.login_userid_et);
		etPassword = (EditText) findViewById(R.id.login_password_et);
		btnRegistration = (Button) findViewById(R.id.login_register_tv);

		btnForgotlogin.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegistration.setOnClickListener(this);
	}

	private void onLgoinValidate() {
		mUserName = etUserName.getText().toString();
		mPassword = etPassword.getText().toString();

		if (mUserName.equals("")) {
			etUserName.setError("Enter the User ID");
			etUserName.setFocusable(true);
			etUserName.requestFocus();
		} else if (mPassword.equals("")) {
			etPassword.setError("Enter the Password");
			etPassword.setFocusable(true);
			etPassword.requestFocus();
		} else {
			callService();
		}
	}

	private void callService() {
		LoginReq commonReq = new LoginReq();
		commonReq.setAction(AppConstants.LOGIN_ACTION);
		commonReq.setEmail(mUserName);
		commonReq.setPass(mPassword);

		WebserviceManager.getResponseForRequest(this, commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							MoiAvenueParser.getDataObject(
									MoiAvenueUtilities.LOGIN_REQ,
									response.toString(),
									new ResponseListener() {

										@Override
										public void onResponseReceived(
												int mRequestType,
												Object mResponseObject) {
											// TODO Auto-generated method stub
											LoginResponse loginResponse = (LoginResponse) mResponseObject;
//											Toast.makeText(
//													ActivityLogin.this,
//													"succ"
//															+ loginResponse
//																	.getErrorMessage(),
//													Toast.LENGTH_LONG).show();
											name = loginResponse.getUserName();
											id = loginResponse.getUserId();
											onSuceeLoginResponse();

										}
									});

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(VolleyError error) {
						Toast.makeText(ActivityLogin.this, "Error in Response",
								Toast.LENGTH_LONG).show();
					}
				});
	}

	private void onSuceeLoginResponse() {
		startActivity(new Intent(this, ActivityBaseDrawer.class));
		finish();
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
		case R.id.login_login_tv:
			hideKeyboard(this);
			onLgoinValidate();
			break;
		case R.id.login_forgot_tv:
			hideKeyboard(this);
			Intent forgot = new Intent(ActivityLogin.this,
					ActivityForgotPassword.class);
			startActivity(forgot);
			break;
		case R.id.login_register_tv:
			Intent registration = new Intent(ActivityLogin.this,
					ActivityRegisteration.class);
			startActivity(registration);
			break;
		}
	}
}
