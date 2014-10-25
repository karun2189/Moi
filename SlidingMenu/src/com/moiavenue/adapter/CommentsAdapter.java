package com.moiavenue.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moiavenue.R;
import com.moiavenue.respmodel.CommentsDataDetails;

public class CommentsAdapter extends BaseAdapter {
	Context mContext;
	int layout;
	private ArrayList<CommentsDataDetails> mCommentsDataDetails = new ArrayList<CommentsDataDetails>();

	public CommentsAdapter(Context context, int layout,
			ArrayList<CommentsDataDetails> photoData) {
		super();
		this.mContext = context;
		this.layout = layout;
		mCommentsDataDetails = photoData;
	}

	class Holder {

		TextView mCommentProfileName, mCommentMsg, mLikeCount, mCommentDate;

		ImageView mCommentProfileImage;

	}

	@Override
	public int getCount() {

		return mCommentsDataDetails.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (mCommentsDataDetails.size() > position
				&& mCommentsDataDetails.get(position) != null) {

			final CommentsDataDetails commentdata = mCommentsDataDetails
					.get(position);

			holder = new Holder();
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, null);

			((TextView) convertView.findViewById(R.id.comments_profile_name))
					.setText(commentdata.getCommenterName());

			((TextView) convertView.findViewById(R.id.comments_message))
					.setText(commentdata.getComment());

			convertView.setTag(holder);

		}
		holder = (Holder) convertView.getTag();

		return convertView;

	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
