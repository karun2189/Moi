package com.moiavenue.service;

import org.json.JSONObject;

import com.android.volley.VolleyError;

public interface WebserviceListener {
public void onSucess(JSONObject response);
public void onFailure(VolleyError error);
}
