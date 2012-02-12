package com.android.forb.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TripoidActivity extends TabActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	private void init() {
		final TabHost tabHost = getTabHost();
		tabHost.setup();

		final Resources res = getResources();
		Intent intent;
		
		intent = new Intent().setClass(this, CalculadoraViagemActivity.class);
		newTabSpec(tabHost, 
				"calc_viagem", 
				R.id.tabCalcViagem, 
				"Viagem", 
				res.getDrawable(R.drawable.ic_tab_calc_viagem), 
				intent);
		
		intent = new Intent().setClass(this, CalculadoraCombustivelActivity.class);
		newTabSpec(tabHost, 
				"calc_viagem", 
				R.id.tabCalcCombustivel, 
				"Combustível", 
				res.getDrawable(R.drawable.ic_tab_calc_comb), 
				intent);
		
	    tabHost.setCurrentTab(2);
	}
	
	private void newTabSpec(final TabHost tabHost, String tag, int tabId, String label, Drawable icon, Intent intent) {
		final TabSpec tabSpec = tabHost.newTabSpec(tag);
		tabSpec.setContent(tabId);
		tabSpec.setIndicator(label, icon);
		tabHost.addTab(tabSpec);
	}

}