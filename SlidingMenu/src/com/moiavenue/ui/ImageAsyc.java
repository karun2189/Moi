package com.moiavenue.ui;

import java.io.InputStream;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;


public class ImageAsyc extends AsyncTask<String, String, Bitmap> {
	
	private Context context;
	private ProgressDialog pDialog;
	private Bitmap bitmap;
	private ImageView imgv;
	
	public ImageAsyc(Context context,ImageView iv)
	{
		this.context = context;
		imgv = iv;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("");
		pDialog.show();
	}

	protected Bitmap doInBackground(String... args) {
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					args[0]).getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	protected void onPostExecute(Bitmap image) {
		if (image != null) {
			imgv.setImageBitmap(image);
			pDialog.dismiss();
		} else {
			pDialog.dismiss();
			Toast.makeText(context,
					"Image Does Not exist or Network Error",
					Toast.LENGTH_SHORT).show();
		}
	}
}
