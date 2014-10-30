package com.moiavenue.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.moiavenue.R;
import com.moiavenue.commonutility.ProfileImageSelectionUtil;

public class UploadFragment extends Fragment {

	private EditText mUploadText;
	private Button mUploadButton;
	private ImageView mUploadImage;
	private String mUpload;
	private String mImagePath;

	public UploadFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_upload, container,
				false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	private void initViews(View view) {
		mUploadText = (EditText) view.findViewById(R.id.upload_text);
		mUploadButton = (Button) view.findViewById(R.id.upload_button);
		mUploadImage = (ImageView) view.findViewById(R.id.upload_image);
		mUploadButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadButtonClicked();
			}

		});
	}

	private void uploadButtonClicked() {

		if (isValid()) {
			callUploadWs();
		}
	}

	private void callUploadWs() {

	}

	private int sendTestimonialVideoAndContentForWS(String folderName) {
		/*
		 * String file = Constants.LOCALTESTIMONIALFOLDER + File.separator; File
		 * files = context.getExternalFilesDir(null); File fileToRead = new
		 * File(files + File.separator + file);
		 */
		
		String uploadImagePath = "";int responseCodeFromServer = 0;
		Log.i("", "Testimonial folder " + folderName);
		if (folderName != null && !folderName.isEmpty()) {
			File fileToRead = new File(folderName);
			if (fileToRead.exists()) {
				File[] fileList = fileToRead.listFiles();
				if (fileList.length != 0) {
					for (int i = 0; i < fileList.length; i++) {
						if (fileList[i].getName().contains(".txt")) {
							
					
							uploadImagePath = fileList[i]
									.getAbsolutePath();
							Log.i("", "Testimonial VideoContent Path: "
									+ uploadImagePath);
						}
					}
				}
			}
		}
		try {
			InputStream inputStream;
			inputStream = new FileInputStream(new File(uploadImagePath));
			InputStreamBody inputStreamBody = new InputStreamBody(inputStream,
					uploadImagePath);
			DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("UploadImage",
					inputStreamBody);

			HttpPost localHttpPost = new HttpPost("");
			localHttpPost.setEntity(builder.build());
			HttpResponse localHttpResponse = localDefaultHttpClient
					.execute(localHttpPost);
			 responseCodeFromServer = localHttpResponse.getStatusLine()
					.getStatusCode();
			Log.i("", "Response code from httpclient.."
					+ responseCodeFromServer);
		} catch (Exception e) {
			Log.d("exception", e.toString());
		}
		return responseCodeFromServer;
	}

	private boolean isValid() {
		boolean valid = false;
		if (mUploadText.getText().toString().trim().length() > 0) {
			mUpload = mUploadText.getText().toString().trim();
			valid = true;
		}
		if (mUploadImage.getDrawable() != null) {
			// ProfileImageSelectionUtil.saveBitmap(filePath, bitmap);
			valid = true;
		}

		return valid;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String filePath = "upload_image.jpg";
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == ProfileImageSelectionUtil.CAMERA
					|| requestCode == ProfileImageSelectionUtil.GALLERY) {

				Bitmap image = ProfileImageSelectionUtil.getImage(data,
						getActivity());

				if (image != null) {
					if (requestCode == ProfileImageSelectionUtil.CAMERA) {
						if (ProfileImageSelectionUtil.isUriTrue) {
							image = ProfileImageSelectionUtil
									.getCorrectOrientationImage(getActivity(),
											data.getData(), image);
						} else {
							image = ProfileImageSelectionUtil
									.getCorrectOrientationImage(getActivity(),
											image);
						}
					} else {

						Uri selectedImage = data.getData();

						image = ProfileImageSelectionUtil
								.getCorrectOrientationImage(getActivity(),
										selectedImage, image);
					}

					// mSaveText.setVisibility(View.VISIBLE);

					String extStorageDirectory = Environment
							.getExternalStorageDirectory().toString();

					// you can create a new file name "test.jpg" in sdcard
					// folder.
					File folder = new File(extStorageDirectory + File.separator
							+ "MoiAvenue");
					if (!folder.exists()) {
						folder.mkdirs();
					}
					filePath = folder + File.separator + filePath;

					ProfileImageSelectionUtil.saveBitmap(filePath, image);

					mUploadImage.setImageBitmap(image);
					// bitmap = image;
					// Toast.makeText(getActivity(),
					// bitmap.getWidth() + " hei" + bitmap.getHeight(),
					// Toast.LENGTH_SHORT).show();
					// mAllImages.add(image);
				}
			}
			//

		}

	}
}
