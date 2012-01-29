package com.android.forb.app;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class SplashScreen extends Activity {

	private MediaPlayer wavePlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
//		wavePlayer = MediaPlayer.create(SplashScreen.this, R.raw.wave_sound);
//		wavePlayer.start();
		
		final Thread timer = new Thread() {
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					final Intent openMain = new Intent("com.android.forb.app.MYT");
					startActivity(openMain);
				}
			};
		};
		timer.start();
	}
	/**
	 * chamado antes da activity parar a execução. Então, 
	 * para não ficar em background é finalizada.
	 */
	@Override
	protected void onPause() {
		super.onPause();
//		wavePlayer.stop();
		this.finish();
	}
}
