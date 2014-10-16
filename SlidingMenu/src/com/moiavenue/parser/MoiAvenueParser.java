package com.moiavenue.parser;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.moiavenue.commonutility.MoiAvenueUtilities;
import com.moiavenue.respmodel.CommonResponse;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.respmodel.CompanyNewsList;
import com.moiavenue.respmodel.LoginResponse;
import com.moiavenue.service.ResponseListener;

public class MoiAvenueParser {

	public static void getDataObject(int mReqType, String mJsonString,
			ResponseListener mListener) {
		switch (mReqType) {
		case MoiAvenueUtilities.GET_COMPANY_NEWS:

			mListener.onResponseReceived(mReqType,
					readCompanyNewsListJsonToMap(mJsonString));
			break;
		case MoiAvenueUtilities.LOGIN_REQ:

			mListener.onResponseReceived(mReqType,
					getLoginResponse(mJsonString));
			break;
		case MoiAvenueUtilities.REG_REQ:
			mListener.onResponseReceived(mReqType,
					getLoginResponse(mJsonString));
			break;
		case MoiAvenueUtilities.FORGOT_PASSWORD_REQ:
			mListener.onResponseReceived(mReqType,
					getForgotPassword(mJsonString));
			break;
		case MoiAvenueUtilities.POST_COMMENT:
			mListener.onResponseReceived(mReqType,
					getForgotPassword(mJsonString));
			break;
		case MoiAvenueUtilities.POST_FEEDBACK:
			mListener.onResponseReceived(mReqType,
					getForgotPassword(mJsonString));
			break;
		}
	}

	private static CommonResponse getForgotPassword(String jsonString) {
		CommonResponse map = new CommonResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(jsonString,
					new TypeReference<CommonResponse>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static LoginResponse getLoginResponse(String jsonString) {
		LoginResponse map = new LoginResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(jsonString,
					new TypeReference<LoginResponse>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static CompanyNewsList readCompanyNewsListJsonToMap(
			String jsonString) {
		CompanyNewsList map = new CompanyNewsList();
		// ArrayList<CompanyNewsData> map = new ArrayList<CompanyNewsData>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(jsonString,
					new TypeReference<CompanyNewsList>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
