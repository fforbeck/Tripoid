package com.android.forb.app;

import static com.android.forb.util.NumberUtil.isZeroOrNull;
import static com.android.forb.util.NumberUtil.round;
import static com.android.forb.util.NumberUtil.toDouble;
import static com.android.forb.util.StringUtil.isBlank;

import com.android.forb.util.NumberUtil;
import com.android.forb.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculadoraCombustivelActivity extends Activity implements View.OnClickListener {

	private EditText etPrecoEtanol;
	private EditText etPrecoGasolina;
	private Button btVerCombustivelRecomendado;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_calculadora_combustivel);
		init();
	}

	private void init() {
		etPrecoEtanol = (EditText) findViewById(R.id.etPrecoEtanol);
		etPrecoGasolina = (EditText) findViewById(R.id.etPrecoGasolina);
		btVerCombustivelRecomendado = (Button) findViewById(R.id.btVerCombustivelRecomendado);
		btVerCombustivelRecomendado.setOnClickListener(CalculadoraCombustivelActivity.this);

		setValues();
	}

	private void setValues() {
		etPrecoEtanol.setText("1.75");
		etPrecoGasolina.setText("2.75");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btVerCombustivelRecomendado:
			if (isValidState()) {
				recomendarCombustivel();
			}
			break;
		}
	}

	private boolean isValidState() {
		if (isBlankFields()) {
			ToastUtil.show(CalculadoraCombustivelActivity.this,
					getString(R.string.msg_campos_obrigatorios));

			return false;
		}

		if (isInvalidFields()) {
			ToastUtil.show(CalculadoraCombustivelActivity.this,
					getString(R.string.msg_valor_invalido));

			return false;
		}

		return true;
	}

	private boolean isBlankFields() {
		return isBlank(etPrecoEtanol) || isBlank(etPrecoGasolina);
	}

	private boolean isInvalidFields() {
		try {
			return isZeroOrNull(etPrecoEtanol) || isZeroOrNull(etPrecoGasolina);

		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private void recomendarCombustivel() {
		double result = round(toDouble(etPrecoEtanol) / toDouble(etPrecoGasolina));
		if (NumberUtil.ge(result, 0.70)) {
			ToastUtil.show(CalculadoraCombustivelActivity.this,
					getString(R.string.msg_recomendar_gasolina));
		} else {
			ToastUtil.show(CalculadoraCombustivelActivity.this,
					getString(R.string.msg_recomendar_etanol));
		}
	}

}