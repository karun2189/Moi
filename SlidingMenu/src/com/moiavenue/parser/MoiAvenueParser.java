package com.moiavenue.parser;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.respmodel.CompanyNewsList;
import com.moiavenue.service.ResponseListener;

public class MoiAvenueParser {

	public static void getDataObject(int mReqType, String mJsonString,
			ResponseListener mListener) {
		switch (mReqType) {
		case MoiAvenueUtilities.GET_COMPANY_NEWS:
			CompanyNewsList companyNewsList = new CompanyNewsList();
			companyNewsList.setCompanyNewsList(readCompanyNewsListJsonToMap(mJsonString));
			mListener.onResponseReceived(mReqType, companyNewsList);
			break;
		}
	}
	
	private static ArrayList<CompanyNewsData> readCompanyNewsListJsonToMap(
			String jsonString) {
		ArrayList<CompanyNewsData> map = new ArrayList<CompanyNewsData>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper
					.readValue(
							jsonString,
							new TypeReference<ArrayList<CompanyNewsData>>() {
							});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
