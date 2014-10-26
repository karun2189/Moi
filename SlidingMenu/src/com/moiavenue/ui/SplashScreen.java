package com.moiavenue.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.moiavenue.R;

/**
 * 
 * Splash Screen of the App
 * 
 */
public class SplashScreen extends Activity {
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_1);

		TextView tv = (TextView) findViewById(R.id.header_text);
		Typeface font = Typeface
				.createFromAsset(getAssets(), "header_font.ttf");
		tv.setTypeface(font);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent mainIntent = new Intent(SplashScreen.this,
						ActivityLogin.class);
				SplashScreen.this.startActivity(mainIntent);
				SplashScreen.this.finish();
			}
		}, 3000);
	}

	/**
	 * To arrest the back button click in the Splash screen
	 */

	@Override
	public void onBackPressed() {
	}
}
