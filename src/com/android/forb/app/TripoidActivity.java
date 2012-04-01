package com.android.forb.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;

public class TripoidActivity extends TabActivity {

	private static final String TAG_CALC_CONSUMO = "calc_consumo";
	private static final String TAG_CALC_COMBUSTIVEL = "calc_combustivel";
	private static final String TAG_CALC_VIAGEM = "calc_viagem";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	private void init() {
		final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		final Resources res = getResources();
		
		createTabCalculadoraViagem(tabHost, res);
		createTabCalculadoraCombustivel(tabHost, res);
		createTabCalculadoraConsumo(tabHost, res);
	}

	private void createTabCalculadoraViagem(final TabHost tabHost, final Resources res) {
		newTabSpec(tabHost, 
				TAG_CALC_VIAGEM, 
				getString(R.string.lbl_calculadora), 
				res.getDrawable(R.drawable.ic_tab_calc_viagem), 
				new Intent(TripoidActivity.this, CalcViagemActivity.class));
	}
	
	private void createTabCalculadoraCombustivel(final TabHost tabHost, final Resources res) {
		newTabSpec(tabHost, 
				TAG_CALC_COMBUSTIVEL, 
				getString(R.string.lbl_combustivel), 
				res.getDrawable(R.drawable.ic_tab_calc_comb), 
				new Intent(TripoidActivity.this, CalcCombustivelActivity.class));
	}
	
	private void createTabCalculadoraConsumo(TabHost tabHost, Resources res) {
		newTabSpec(tabHost, 
				TAG_CALC_CONSUMO, 
				getString(R.string.lbl_consumo), 
				res.getDrawable(R.drawable.ic_tab_calc_consumo), 
				new Intent(TripoidActivity.this, CalcConsumoActivity.class));
	}
	
	private void newTabSpec(final TabHost tabHost, String tag, String label, Drawable icon, Intent intent) {
		tabHost.addTab(tabHost
				.newTabSpec(tag)
				.setContent(intent)
				.setIndicator(label, icon));
	}

}