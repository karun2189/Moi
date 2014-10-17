package com.moiavenue.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.moiavenue.AppConstants;
import com.moiavenue.R;
import com.moiavenue.adapter.HomeAdapter;
import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.parser.MoiAvenueParser;
import com.moiavenue.reqmodel.CompanyNewsReq;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.respmodel.CompanyNewsList;
import com.moiavenue.service.ResponseListener;
import com.moiavenue.service.WebserviceListener;
import com.moiavenue.service.WebserviceManager;

public class CompanyNewsFragment extends Fragment {
	private int mStartIndex = 0, mEndofRecord = -1, mEndIndex = 10;
	private int mNoOfows = 50;
	private ListView mListView;
	private ArrayList<CompanyNewsData> mCompanyNewsDatasList = new ArrayList<CompanyNewsData>();

	public CompanyNewsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_company_news,
				container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initComponents(view);
		sendWebservice();
		// setAdapter();
	}

	public void refresh() {
		sendWebservice();
	}

	//

	private void sendWebservice() {
		CompanyNewsReq commonReq = new CompanyNewsReq();
		commonReq.setAction(AppConstants.GET_COMPANY_NEWS);
		commonReq.setEnd_row("" + mNoOfows);
		commonReq.setStart_row("" + mStartIndex);

		WebserviceManager.getResponseForRequest(getActivity(), commonReq,
				new WebserviceListener() {

					@Override
					public void onSucess(JSONObject response) {
						try {

							MoiAvenueParser.getDataObject(
									MoiAvenueUtilities.GET_COMPANY_NEWS,
									response.toString(),
									new ResponseListener() {

										@Override
										public void onResponseReceived(
												int mRequestType,
												Object mResponseObject) {
											// TODO Auto-generated method stub
											CompanyNewsList companyNewsList = (CompanyNewsList) mResponseObject;
											mEndofRecord = Integer
													.parseInt(companyNewsList
															.getEndOfRecord());
											for (CompanyNewsData companyNewsData : companyNewsList
													.getCompanyNewsList()) {
												mCompanyNewsDatasList
														.add(companyNewsData);
											}
											// Toast.makeText(
											// getActivity(),
											// "succ"
											// + mCompanyNewsData
											// .getMessage(),
											// Toast.LENGTH_LONG).show();

											if (isEndIndex()) {
												onSuccessRecevingData();
											} else {
												mStartIndex = mEndIndex + 1;
												mEndIndex = mEndIndex
														+ mNoOfows;
												if (mEndIndex > mEndofRecord) {
													mEndIndex = mEndofRecord;
												}
												// setAdapter();
												sendWebservice();

											}
										}
									});

							// if (response.has("status")
							// && (response.getInt("status") ==
							// AppConstants.SUCCESS)) {
							//
							// onSuceeLoginResponse();
							// } else if (response.has("message")) {
							// Toast.makeText(
							// ActivityLogin.this,
							// "Error in Response"
							// + response.get("message"),
							// Toast.LENGTH_LONG).show();
							// }

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

	private boolean isEndIndex() {
		return mEndIndex >= mEndofRecord;
	}

	private void onSuccessRecevingData() {
		setAdapter();

	}

	private void setAdapter() {
		((ActivityBaseDrawer) getActivity()).setCount(mCompanyNewsDatasList
				.size());
		HomeAdapter homeAdapter = new HomeAdapter(getActivity(),
				R.layout.adapter_company_news, mCompanyNewsDatasList);
		mListView.setAdapter(homeAdapter);
	}

	private void initComponents(View view) {
		mListView = (ListView) view.findViewById(R.id.news_list);
		// mCompanyNewsData.setCompanyNewsList(new
		// ArrayList<CompanyNewsData>());

	}
}
