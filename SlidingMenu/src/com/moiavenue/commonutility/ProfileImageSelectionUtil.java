package com.moiavenue.commonutility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.moiavenue.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileImageSelectionUtil {

	public static final int CAMERA = 100;
	public static final int GALLERY = 200;
	public static final int CAMERA_VIDEO = 300;
	public static final int VIDEO_GALLERY = 400;
	public static final int FIRSTIMAGE = 1;
	public static final int SECONDIMAGE = 2;
	public static final int CAMERAIMAGE = 3;
	public static String IMAGEPATH = "IMAGEPATH";
	public static String CURERENTPOS = "CURERENTPOS";
	static Dialog alertOptionDialog;
	public static boolean isUriTrue;

	/**
	 * @return
	 * 
	 *         Check the Image capture functionality has the bug in device
	 */
	public static boolean hasImageCaptureBug() {

		// list of known devices that have the bug
		ArrayList<String> devices = new ArrayList<String>();
		devices.add("android-devphone1/dream_devphone/dream");
		devices.add("generic/sdk/generic");
		devices.add("vodafone/vfpioneer/sapphire");
		devices.add("tmobile/kila/dream");
		devices.add("verizon/voles/sholes");
		devices.add("google_ion/google_ion/sapphire");

		boolean bool = devices.contains(android.os.Build.BRAND + "/"
				+ android.os.Build.PRODUCT + "/" + android.os.Build.DEVICE);

		return bool;

	}

	public static void openCamera(Activity context, int requestCode) {
		Intent cameraIntent = null;
		if (requestCode == 100) {
			cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			if (ProfileImageSelectionUtil.hasImageCaptureBug()) {
				cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory().getPath())));
			} else {
				File dir = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
				File output = new File(dir, "camerascript.png");
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(output));
			}
		} else {
			cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
			if (ProfileImageSelectionUtil.hasImageCaptureBug()) {
				cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory().getPath())));
			} else {
				File dir = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
				File output = new File(dir, "cameravideo.mp4");
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(output));
			}
		}

		context.startActivityForResult(cameraIntent, requestCode);

		// context.overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);

	}

	private static String imagePathGlobal;

	// private static String imagePathGlobal;

	public static Bitmap getImage(Intent data, Activity context) {

		try {
			Bitmap image = null;
			String imagePath = null;
			Uri uri = null;

			if (hasImageCaptureBug()) {
				File fi = new File(Environment.getExternalStorageDirectory()
						.getPath());
				try {
					uri = Uri.parse(android.provider.MediaStore.Images.Media
							.insertImage(context.getContentResolver(),
									fi.getAbsolutePath(), null, null));
					if (!fi.delete()) {
						Log.i("logMarker", "Failed to delete " + fi);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				if (data == null) {

					isUriTrue = false;
					File fi = new File(
							Environment
									.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
							"camerascript.png");

					imagePath = fi.getAbsolutePath();

					imagePathGlobal = imagePath;

					System.out.println("ImagepAth" + imagePath);

				} else {

					isUriTrue = true;
					uri = data.getData();
				}

			}

			if (uri != null || imagePath != null) {

				try {

					if (uri != null) {
						imagePath = getRealPathFromURI(uri, context);

						image = getBitmap(imagePath);
					} else if (imagePath != null) {
						image = getBitmap(imagePath);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @param contentUri
	 * @param context
	 * @return
	 * 
	 *         Get original path from the given URI
	 */
	public static String getRealPathFromURI(Uri contentUri, Activity context) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj,
				null, null, null);
		if (cursor.moveToFirst()) {
			;
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}

	private static void selectImageFromGallery(Activity context, int requestCode) {

		Intent intent = new Intent();

		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);

		context.startActivityForResult(intent, requestCode);

		// context.overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);

	}

	private static void selectVideoFromGallery(Activity context, int requestCode) {

		Intent intent = new Intent();

		intent.setType("video/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		context.startActivityForResult(intent, requestCode);

		// context.overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);

	}

	public static Dialog showOption(final Activity context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.phot_selection, null);

		TextView alertTitle = (TextView) view.findViewById(R.id.alertTitle);
//		alertTitle.setTypeface(TypefaceSingleton.getInstance().getHelvetica(
//				context));
		builder.setView(view);

		Button dialogOkButton = (Button) view.findViewById(R.id.option1);
//		dialogOkButton.setTypeface(TypefaceSingleton.getInstance()
//				.getHelvetica(context));
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
				openCamera(context, ProfileImageSelectionUtil.CAMERA);
			}
		});

		Button dialogCancelButton = (Button) view.findViewById(R.id.option2);
//		dialogCancelButton.setTypeface(TypefaceSingleton.getInstance()
//				.getHelvetica(context));
		// if button is clicked, close the custom dialog

		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
				selectImageFromGallery(context,
						ProfileImageSelectionUtil.GALLERY);
			}
		});

		ImageView okButton = (ImageView) view.findViewById(R.id.option3);
		// okButton.setTypeface(TypefaceSingleton.getInstance().getHelvetica(
		// context));

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
			}
		});

		alertOptionDialog = builder.show();

		alertOptionDialog.show();
		return alertOptionDialog;
	}

	public static Dialog showOptionNew(final Activity context,
			final String option) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.phot_selection, null);

		TextView alertTitle = (TextView) view.findViewById(R.id.alertTitle);
		if (option.equalsIgnoreCase("pic")) {
			alertTitle.setText("Choose Picture");
		} else {
			alertTitle.setText("Choose Video");
		}
//		alertTitle.setTypeface(TypefaceSingleton.getInstance().getHelvetica(
//				context));
		builder.setView(view);

		Button dialogOkButton = (Button) view.findViewById(R.id.option1);
//		dialogOkButton.setTypeface(TypefaceSingleton.getInstance()
//				.getHelvetica(context));
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
				if (option.equalsIgnoreCase("pic")) {
					openCamera(context, ProfileImageSelectionUtil.CAMERA);
				} else {
					openCamera(context, ProfileImageSelectionUtil.CAMERA_VIDEO);
				}
			}

		});

		Button dialogCancelButton = (Button) view.findViewById(R.id.option2);
//		dialogCancelButton.setTypeface(TypefaceSingleton.getInstance()
//				.getHelvetica(context));
		// if button is clicked, close the custom dialog

		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
				if (option.equalsIgnoreCase("pic")) {
					selectImageFromGallery(context,
							ProfileImageSelectionUtil.GALLERY);
				} else {
					selectVideoFromGallery(context,
							ProfileImageSelectionUtil.VIDEO_GALLERY);
				}
			}
		});

		ImageView okButton = (ImageView) view.findViewById(R.id.option3);
		// okButton.setTypeface(TypefaceSingleton.getInstance().getHelvetica(
		// context));

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertOptionDialog.cancel();
			}
		});

		alertOptionDialog = builder.show();

		alertOptionDialog.show();
		return alertOptionDialog;
	}

	public static Bitmap getBitmap(String path) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(path), null, o);
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			Bitmap bitmap = BitmapFactory.decodeStream(
					new FileInputStream(path), null, o2);
			return bitmap;
		} catch (FileNotFoundException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	public static Bitmap getCorrectOrientationImage(Context context,
			Uri selectedImage, Bitmap image) {

		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		int rotate = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(filePath);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 1);

			Matrix matrix = new Matrix();

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				rotate = 90;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
				rotate = 180;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				rotate = 270;
			}
			if (rotate != 0) {
				int w = image.getWidth();
				int h = image.getHeight();

				matrix.preRotate(rotate);
				// Rotate the bitmap
				image = Bitmap.createBitmap(image, 0, 0, w, h, matrix, true);
				image = image.copy(Bitmap.Config.ARGB_8888, true);
			}
		} catch (Exception exception) {
			Log.d("check", "Could not rotate the image");
		}

		return image;
	}

	public static Bitmap getCorrectOrientationImage(Context context,
			Bitmap image) {

		int rotate = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(imagePathGlobal);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 1);

			Matrix matrix = new Matrix();

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				rotate = 90;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
				rotate = 180;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				rotate = 270;
			}
			if (rotate != 0) {
				int w = image.getWidth();
				int h = image.getHeight();

				matrix.preRotate(rotate);
				// Rotate the bitmap
				image = Bitmap.createBitmap(image, 0, 0, w, h, matrix, true);
				image = image.copy(Bitmap.Config.ARGB_8888, true);
			}
		} catch (Exception exception) {
			Log.d("check", "Could not rotate the image");
		}

		return image;
	}

	public static void saveBitmap(String filePath, Bitmap bitmap) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			//
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			File file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			// write the bytes in file
			FileOutputStream fo = new FileOutputStream(file);

			fo.write(bytes.toByteArray());

			// remember close de FileOutput
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
