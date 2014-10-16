package com.moiavenue.adapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.moiavenue.R;
import com.moiavenue.dialog.CommentDialog;
import com.moiavenue.respmodel.CompanyNewsData;
import com.moiavenue.ui.ImageAsyc;

public class HomeAdapter extends BaseAdapter {
	private Context mContext;
	private int mLayoutId;
	private List<CompanyNewsData> mCompanyNewsDataList;
	private View convertView;

	public HomeAdapter(Context context, int layout,
			List<CompanyNewsData> photosDataList) {
		super();
		this.mContext = context;
		this.mLayoutId = layout;
		mCompanyNewsDataList = photosDataList;
	}

	class Holder {

	}

	@Override
	public int getCount() {
		return mCompanyNewsDataList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (mCompanyNewsDataList.size() > position
				&& mCompanyNewsDataList.get(position) != null) {

			final CompanyNewsData photoData = mCompanyNewsDataList
					.get(position);
			holder = new Holder();
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mLayoutId, null);

			TextView camera = (TextView) convertView
					.findViewById(R.id.comment_icon);

			((TextView) convertView.findViewById(R.id.posted_person_name))
					.setText(photoData.getPostedPersonName());
			((TextView) convertView.findViewById(R.id.posted_message))
					.setText(photoData.getPostedFileMessage());

			if (photoData.getPostedFileUrl().length() > 0) {
				final ImageView iv = ((ImageView) convertView
						.findViewById(R.id.posted_image));
				iv.setVisibility(View.VISIBLE);

//				ImageRequest ir = new ImageRequest(
//						photoData.getPostedFileUrl(),
//						new Response.Listener<Bitmap>() {
//
//							@Override
//							public void onResponse(Bitmap response) {
//
//								iv.setImageBitmap(response);
//
//							}
//						}, 0, 0, null, null);
				
				ImageAsyc imageAsyc = new ImageAsyc(mContext, iv);
				imageAsyc.execute(photoData.getPostedFileUrl());

			}

			camera.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// ProfileImageSelectionUtil.showOption((Activity)mContext);
					CommentDialog commentDialog = new CommentDialog();
					commentDialog.showCommentDialog(mContext, photoData);
				}
			});
			convertView.setTag(holder);

		}
		holder = (Holder) convertView.getTag();

		return convertView;

	}

	@Override
	public Object getItem(int position) {
		return mCompanyNewsDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
}
