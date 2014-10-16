package com.moiavenue.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.moiavenue.AppConstants;
import com.moiavenue.ApplicationClass;

public class WebserviceManager {

	public static void getResponseForRequest(Context context, Object obj,
			final WebserviceListener webserviceListener) {
		// Tag used to cancel the request
		String tagReqObj = "reqObj";

		

		Log.e("text", "text " + obj.toString());
		String url = AppConstants.BASE_URL;

		final ProgressDialog pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading...");
		pDialog.show();
		String jsonString = new Gson().toJson(obj);
		System.out.println("text " + jsonString);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST, url,
				jsonObject, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("Sucess", response.toString());
						webserviceListener.onSucess(response);
						pDialog.hide();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("error", "Error: " + error.getMessage());
						// hide the progress dialog
						webserviceListener.onFailure(error);
						pDialog.hide();
					}
				});

		// Adding request to request queue
		ApplicationClass.getInstance().addToRequestQueue(jsonObjReq, tagReqObj);
	}
}
