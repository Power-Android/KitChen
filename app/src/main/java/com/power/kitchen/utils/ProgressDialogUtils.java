package com.power.kitchen.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.app.MyApplication;


/**
 * 进度条对话框
 */
public class ProgressDialogUtils extends Dialog {

	private static ProgressDialogUtils myProgressDialog;

	private ProgressDialogUtils(Context context, int theme) {
		super(context, theme);
	}

	public ProgressDialogUtils(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static ProgressDialogUtils getInstance() {
		if (myProgressDialog == null) {
			myProgressDialog = new ProgressDialogUtils(
					MyApplication.APP_CONTEXT, R.style.dialog);
			myProgressDialog.setContentView(R.layout.layout_loading);
			myProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		}
		return myProgressDialog;
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param cancelable
	 * 
	 * @return
	 */
	public static ProgressDialogUtils show(Context context, String title,
                                           String message, boolean cancelable) {
		myProgressDialog = new ProgressDialogUtils(context,
				R.style.dialog);
		myProgressDialog.setContentView(R.layout.layout_loading);
		myProgressDialog.setCancelable(cancelable);
		myProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		TextView tv_message = (TextView) myProgressDialog
				.findViewById(R.id.tipTextView);
		if (null == message || "".equals(message))
			tv_message.setVisibility(View.GONE);
		else
			tv_message.setText(message);
		return myProgressDialog;
	}

	public static ProgressDialogUtils show(Context context, String title,
                                           String message) {
		return show(context, title, message, false);
	}

	public static ProgressDialogUtils show(Context context, String message) {
		return show(context, "", message, false);
	}

	public static ProgressDialogUtils show(Context context) {
		return show(MyApplication.APP_CONTEXT, "");
	}

	@Override
	public void cancel() {
		super.cancel();
		myProgressDialog = null;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (myProgressDialog != null) {
			myProgressDialog = null;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

	public static ProgressDialogUtils show(String title, String message,
                                           boolean cancelable) {
		myProgressDialog = new ProgressDialogUtils(MyApplication.APP_CONTEXT,
				R.style.dialog);
		myProgressDialog.setContentView(R.layout.layout_loading);
		myProgressDialog.setCancelable(cancelable);
		myProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		TextView tv_message = (TextView) myProgressDialog
				.findViewById(R.id.tipTextView);
		if (null == message || "".equals(message))
			tv_message.setVisibility(View.GONE);
		else
			tv_message.setText(message);
		return myProgressDialog;
	}

	public static void show(String title, String message) {
		show(MyApplication.APP_CONTEXT, title, message, false);
	}

	public static ProgressDialogUtils show(String message) {
		return show(MyApplication.APP_CONTEXT, "", message, false);
	}
}