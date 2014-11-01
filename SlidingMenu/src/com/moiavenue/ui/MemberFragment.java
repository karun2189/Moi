package com.moiavenue.ui;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.reqmodel.GetMembersReq;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class MemberFragment extends Fragment {

	private int mStartIndex = 0;
	private int mNoOfows = 50;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_members, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getMembersListWS();
	}
	
	private void getMembersListWS(){
		GetMembersReq commonReq = new GetMembersReq();
		commonReq.setAction(AppConstants.GET_MEMBERS);
		commonReq.setEnd_row("" + mNoOfows);
		commonReq.setStart_row("" + mStartIndex);
		WebserviceManager.getResponseForRequest(getActivity(), commonReq,new WebserviceListener() {
			
			@Override
			public void onSucess(JSONObject response) {
				if(response != null)
					Log.d("","Get members response : " + response.toString());
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Toast.makeText(getActivity(), "Error in Response",
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
