package com.moiavenue.commonutility;

import com.moiavenue.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class DialogHelper {

	public interface DialogCallback {
		void onOk();
	}

	public static void makeDialog(Context context, DialogType type,
			final DialogCallback callback) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setCancelable(false);

		alertDialog.setTitle(getDialogTitle(context, type));

		alertDialog.setMessage(getDefaultDialogMessage(context, type));

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null)
							callback.onOk();
					}
				});

		alertDialog.show();
	}
	
	

	public static void makeDialog(Context context, String message,
			final DialogCallback callback) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setCancelable(false);

		alertDialog.setTitle(context.getResources().getString(
				R.string.dialog_error_title));
		
	

		alertDialog.setMessage(message);

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null)
							callback.onOk();
					}
				});

		alertDialog.show();
	}

	public static void makeDialog(Context context, DialogType type,
			String message, final DialogCallback callback) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setCancelable(false);

		alertDialog.setTitle(getDialogTitle(context, type));

		alertDialog.setMessage(message);

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null)
							callback.onOk();
					}
				});

		alertDialog.show();
	}

	public static String getDialogTitle(Context context, DialogType type) {

		String dialogTitle = "";

		switch (type) {
		case ERROR:
			dialogTitle = context.getResources().getString(
					R.string.dialog_error_title);
			break;
		case NETWORK:
			dialogTitle = context.getResources().getString(
					R.string.dialog_network_title);
			break;
		case INFO:
			dialogTitle = context.getResources().getString(
					R.string.dialog_information_title);
			break;
		case MAINTENANCE:
			dialogTitle = context.getResources().getString(
					R.string.dialog_maintenance_title);
			break;
		default:
			dialogTitle = context.getResources().getString(
					R.string.dialog_information_title);
			break;
		}
		return dialogTitle;
	}

	public static String getDefaultDialogMessage(Context context,
			DialogType type) {

		String dialogMessage = "";

		switch (type) {
		case ERROR:
			dialogMessage = context.getResources().getString(
					R.string.dialog_error_message);
			break;
		case NETWORK:
			dialogMessage = context.getResources().getString(
					R.string.dialog_network_message);
			break;
		case INFO:
			dialogMessage = context.getResources().getString(
					R.string.dialog_information_message);
			break;
		case MAINTENANCE:
			dialogMessage = context.getResources().getString(
					R.string.dialog_maintenance_message);
			break;
		default:
			dialogMessage = context.getResources().getString(
					R.string.dialog_information_message);
			break;
		}
		return dialogMessage;
	}

}
