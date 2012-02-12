package com.android.forb.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		final Thread timer = new Thread() {
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					final Intent openMain = new Intent("com.android.forb.app.TRIPOIDACT");
					startActivity(openMain);
				}
			};
		};
		timer.start();
	}
}
