package com.moiavenue.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.reqmodel.RegistrationReq;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class ActivityRegisteration extends Activity implements OnClickListener {

	Button btnCancel;
	Button btnRegistration;
	EditText etUserName;
	EditText etPassword;
	EditText etReenterPassword;
	EditText etNumber;
	EditText etEmailAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		init();

	}

	private void init() {

		etUserName = (EditText) findViewById(R.id.reg_username_et);
		etPassword = (EditText) findViewById(R.id.reg_password_et);
		etReenterPassword = (EditText) findViewById(R.id.reg_reenter_password_et);
		etNumber = (EditText) findViewById(R.id.reg_number);
		etEmailAddress = (EditText) findViewById(R.id.reg_mail_id);

		btnRegistration = (Button) findViewById(R.id.register_tv);
		btnCancel = (Button) findViewById(R.id.cancel_tv);

		btnCancel.setOnClickListener(this);
		btnRegistration.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.register_tv:
			callRegWs();
			break;
		case R.id.cancel_tv:
			finish();
			break;
		}
	}

	private void callRegWs() {
		if (onValidateRegistration()) {
			callService();
		}
	}

	private void callService() {

		RegistrationReq commonReq = new RegistrationReq();
		commonReq.setAction(AppConstants.REG_ACTION);
		commonReq.setEmail(etEmailAddress.getText().toString().trim());
		commonReq.setName(etUserName.getText().toString().trim());
		commonReq.setNumber(etNumber.getText().toString().trim());
		commonReq.setPass(etPassword.getText().toString().trim());

		WebserviceManager.getResponseForRequest(this, commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							if (response.has("status")
									&& (response.getInt("status") == AppConstants.SUCCESS)) {

								onSucessRegResponse();
							} else if (response.has("message")) {
								Toast.makeText(
										ActivityRegisteration.this,
										"Error in Response"
												+ response.get("message"),
										Toast.LENGTH_LONG).show();
							}

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(VolleyError error) {
						Toast.makeText(ActivityRegisteration.this,
								"Error in Response", Toast.LENGTH_LONG).show();
					}
				});
	}

	private void onSucessRegResponse() {
		startActivity(new Intent(this, ActivityLogin.class));
		finish();
	}

	private boolean onValidateRegistration() {
		if (etUserName.getText().toString().trim().length() == 0) {
			etUserName.setError("User Name Cannot be Empty");
			etUserName.setFocusable(true);
			etUserName.requestFocus();
			return false;
		}
		if (etEmailAddress.getText().toString().trim().length() == 0) {
			etEmailAddress.setError("Email Address Cannot be Empty");
			etEmailAddress.setFocusable(true);
			etEmailAddress.requestFocus();
			return false;
		}
		if (etNumber.getText().toString().trim().length() == 0) {
			etNumber.setError("Phone Number Cannot be Empty");
			etNumber.setFocusable(true);
			etNumber.requestFocus();
			return false;
		}
		if (etPassword.getText().toString().trim().length() == 0) {
			etPassword.setError("Password Cannot be Empty ");
			etPassword.setFocusable(true);
			etPassword.requestFocus();
			return false;
		}
		if (!(etPassword.getText().toString().trim()
				.equalsIgnoreCase(etReenterPassword.getText().toString().trim()))) {
			etReenterPassword.setError("Password Mismatch");
			etReenterPassword.setFocusable(true);
			etReenterPassword.requestFocus();
			return false;
		}
		return true;
	}
}
