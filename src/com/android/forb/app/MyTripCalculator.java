package com.android.forb.app;

import static android.graphics.Color.rgb;
import static com.android.forb.util.NumberUtil.isZeroOrNull;
import static com.android.forb.util.NumberUtil.round;
import static com.android.forb.util.NumberUtil.toDouble;
import static com.android.forb.util.StringUtil.isBlank;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.forb.util.AppUtil;
import com.android.forb.util.ToastUtil;

public class MyTripCalculator extends Activity implements View.OnClickListener {

	private static final int MAX_NUM_ITENS = 7;
	private Button buttonCalcular;
	private TextView tvResultado;
	private EditText etKm;
	private EditText etKmL;
	private EditText etValorCombustivel;
	private TextView tvResultadoPorPassageiro;
	private EditText etNumeroPassageiros;
	private View buttonAddItem;
	private static int idCounter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
		buttonCalcular.setOnClickListener(MyTripCalculator.this);

		buttonAddItem = (Button) findViewById(R.id.btAddItem);
		buttonAddItem.setOnClickListener(MyTripCalculator.this);

		setValues();
	}

	private void setValues() {
		etKm.setText("300");
		etKmL.setText("11.5");
		etValorCombustivel.setText("1.80");
		etNumeroPassageiros.setText("4");
	}

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
		double sum = sumItens();
		tvResultado.setText("Total da despesa: R$ " + calcular(sum));
		final Random rd = new Random();
		tvResultado.setTextSize(25);
		int r = rd.nextInt(265), g = rd.nextInt(265), b = rd.nextInt(265);
		tvResultado.setTextColor(rgb(r,g,b));
		
		System.out.println(r + " " + g + " " + b);
		
		tvResultadoPorPassageiro.setText("Despesa por passageiro: R$ "
				+ dividirPorNumeroPassageiros(sum));
		tvResultadoPorPassageiro.setTextColor(rgb(r, g, b));

		AppUtil.hideSwKeyBoard(etNumeroPassageiros, MyTripCalculator.this);
	}

	private double sumItens() {
		double sum = 0.0;
		for (int i = 1; i <= idCounter; i++) {
			try {
				sum += toDouble((EditText) findViewById(100 + idCounter));
			} catch (Exception e) {
				// ignored because edit text is empty
			}
		}
		return sum;
	}

	private void doAddItem() {
		if (idCounter == MAX_NUM_ITENS) {
			ToastUtil.show(MyTripCalculator.this,
					"Não é permitido inserir mais itens.");
			return;
		}

		idCounter++;

		final LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);

		final LinearLayout externLinerLayout = new LinearLayout(this);
		externLinerLayout.setId(7 * idCounter);
		externLinerLayout.setWeightSum(100);
		externLinerLayout.setOrientation(LinearLayout.HORIZONTAL);

		final LinearLayout textLinearLayout = new LinearLayout(this);
		textLinearLayout.setId(1000 + idCounter);
		textLinearLayout.setWeightSum(80);
		textLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		textLinearLayout.setPadding(10, 0, 0, 5);
		final EditText editText = new EditText(this);
		editText.setId(100 + idCounter);
		editText.setHint("Exemplo: 30.50 (Pneu furado)");
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		textLinearLayout.addView(editText);
		externLinerLayout.addView(textLinearLayout);

		final LinearLayout buttonLinearLayout = new LinearLayout(this);
		buttonLinearLayout.setId(10 + idCounter);
		buttonLinearLayout.setWeightSum(25);
		buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLinearLayout.setPadding(0, 0, 10, 5);
		final Button button = new Button(this);
		button.setId(idCounter);
		button.setText("X");
		button.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (idCounter == 0) {
					return;
				}

				final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
				mLinearLayout.removeView(findViewById(7 * v.getId()));
				idCounter--;
			}
		});

		buttonLinearLayout.addView(button);
		externLinerLayout.addView(buttonLinearLayout);
		mainLinearLayout.addView(externLinerLayout);
	}

	private boolean isValidState() {
		if (isBlankFields()) {
			ToastUtil.show(MyTripCalculator.this,
					"Por favor preencha todos os campos.");

			return false;
		}

		if (isInvalidFields()) {
			ToastUtil.show(MyTripCalculator.this,
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
		return round(sum + (toDouble(etKm) / toDouble(etKmL)
						* toDouble(etValorCombustivel)));
	}

	private Double dividirPorNumeroPassageiros(double sum) {
		return round(calcular(sum) / toDouble(etNumeroPassageiros));
	}

}