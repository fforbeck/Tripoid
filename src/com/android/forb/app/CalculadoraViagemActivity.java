package com.android.forb.app;

import static android.graphics.Color.rgb;
import static com.android.forb.util.NumberUtil.isZeroOrNull;
import static com.android.forb.util.NumberUtil.round;
import static com.android.forb.util.NumberUtil.toDouble;
import static com.android.forb.util.StringUtil.isBlank;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.forb.util.AppUtil;
import com.android.forb.util.ToastUtil;

public class CalculadoraViagemActivity extends TabActivity implements View.OnClickListener {

	private static final int MAX_NUM_ITENS = 10;

	private ItemController itemControler;

	private Button buttonCalcular;
	private TextView tvResultado;
	private EditText etKm;
	private EditText etKmL;
	private EditText etValorCombustivel;
	private TextView tvResultadoPorPassageiro;
	private EditText etNumeroPassageiros;
	private View buttonAddItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		tvResultado = (TextView) findViewById(R.id.tvResultado);
		tvResultadoPorPassageiro = (TextView) findViewById(R.id.tvResultadoPorPassageiro);

		etKm = (EditText) findViewById(R.id.etKm);
		etKmL = (EditText) findViewById(R.id.etKmL);
		etValorCombustivel = (EditText) findViewById(R.id.etValorCombustivel);
		etNumeroPassageiros = (EditText) findViewById(R.id.etNumeroPassageiros);

		buttonCalcular = (Button) findViewById(R.id.btCalcular);
		buttonCalcular.setOnClickListener(CalculadoraViagemActivity.this);

		buttonAddItem = (Button) findViewById(R.id.btAddItem);
		buttonAddItem.setOnClickListener(CalculadoraViagemActivity.this);

		itemControler = ItemController.getInstance();

		setValues();
	}
	
	private void setValues() {
		etKm.setText("300");
		etKmL.setText("11.5");
		etValorCombustivel.setText("1.80");
		etNumeroPassageiros.setText("4");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btCalcular:
			if (isValidState()) {
				doCalcular();
			}
			break;

		case R.id.btAddItem:
			doAddItem();
			break;
		}
	}

	private void doCalcular() {
		double sum = itemControler.sumItens(this);
		final Double amount = calcular(sum);

		tvResultado.setTextSize(25);
		tvResultado.setTextColor(rgb(53, 201, 252));
		tvResultado.setText(String.valueOf(amount));

		// 26 164 169
		// 215 250 233
		// 262 88 248
		// 36 246 155
		// 77 81 214
		// 150 131 247
		// 120 218 54
		// 53 201 252

		tvResultadoPorPassageiro.setTextColor(rgb(120, 218, 54));
		tvResultadoPorPassageiro.setText(String
				.valueOf(dividirPorNumeroPassageiros(amount)));

		AppUtil.hideSwKeyBoard(etNumeroPassageiros,
				CalculadoraViagemActivity.this);
	}

	private void doAddItem() {
		if (itemControler.getNumItens() == MAX_NUM_ITENS) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					"Não é permitido inserir mais itens.");
			return;
		}

		itemControler.newItem(this,
				(LinearLayout) findViewById(R.id.firstLinearLayout));
	}

	private boolean isValidState() {
		if (isBlankFields()) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					"Por favor preencha todos os campos.");

			return false;
		}

		if (isInvalidFields()) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					"Não é permitido o valor 0 (zero) ou nulo.");

			return false;
		}

		return true;
	}

	private boolean isBlankFields() {
		return isBlank(etKmL) || isBlank(etKm) || isBlank(etNumeroPassageiros)
				|| isBlank(etValorCombustivel);
	}

	private boolean isInvalidFields() {
		try {
			Double double1 = toDouble(etKmL);
			Double double2 = toDouble(etKm);
			Double double3 = toDouble(etNumeroPassageiros);
			Double double4 = toDouble(etValorCombustivel);
			return isZeroOrNull(double1) || isZeroOrNull(double2)
					|| isZeroOrNull(double3) || isZeroOrNull(double4);

		} catch (IllegalArgumentException e) {
			return false;
		}

	}

	private Double calcular(double sum) {
		return round(sum
				+ (toDouble(etKm) / toDouble(etKmL) * toDouble(etValorCombustivel)));
	}

	private Double dividirPorNumeroPassageiros(double amout) {
		return round(amout / toDouble(etNumeroPassageiros));
	}

}