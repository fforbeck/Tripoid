package com.android.forb.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TripoidActivity extends TabActivity implements View.OnClickListener {
	
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
		
		createTabCalculadoraViagem(tabHost, res);
		createTabCalculadoraCombustivel(tabHost, res);
		createTabCalculadoraConsumo(tabHost, res);
	}

	private void createTabCalculadoraViagem(final TabHost tabHost, final Resources res) {
		final Intent intent = new Intent().setClass(this, CalculadoraViagemActivity.class);
		newTabSpec(tabHost, 
				"calc_viagem", 
				R.id.tabCalcViagem, 
				"Calculadora", 
				res.getDrawable(R.drawable.ic_tab_calc_viagem), 
				intent);
	}
	
	private void createTabCalculadoraCombustivel(final TabHost tabHost, final Resources res) {
		final Intent intent = new Intent().setClass(this, CalculadoraCombustivelActivity.class);
		newTabSpec(tabHost, 
				"calc_viagem", 
				R.id.tabCalcCombustivel, 
				"Combustível", 
				res.getDrawable(R.drawable.ic_tab_calc_comb), 
				intent);
	}
	
	private void createTabCalculadoraConsumo(TabHost tabHost, Resources res) {
		final Intent intent = new Intent().setClass(this, CalculadoraViagemActivity.class);
		newTabSpec(tabHost, 
				"calc_consumo", 
				R.id.tabCalcConsumo, 
				"Consumo", 
				res.getDrawable(R.drawable.ic_tab_calc_consumo), 
				intent);
	}
	
	private void newTabSpec(final TabHost tabHost, String tag, int tabId, String label, Drawable icon, Intent intent) {
		final TabSpec tabSpec = tabHost.newTabSpec(tag);
		tabSpec.setContent(tabId);
		tabSpec.setIndicator(label, icon);
		tabHost.addTab(tabSpec);
	}

	@Override
	public void onClick(View v) {
	}

}