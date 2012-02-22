package com.android.forb.app;

import static android.graphics.Color.rgb;
import static com.android.forb.util.NumberUtil.isZeroOrNull;
import static com.android.forb.util.NumberUtil.round;
import static com.android.forb.util.NumberUtil.toDouble;
import static com.android.forb.util.StringUtil.isBlank;

import java.util.HashSet;
import java.util.Set;

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

public class CalculadoraViagemActivity extends Activity implements View.OnClickListener {

	private static final int MAX_NUM_ITENS = 10;
	
	private final Set<Integer> itensIDs = new HashSet<Integer>();

	private static int idCounter = 0;

	private TextView tvResultado;
	private TextView tvResultadoPorPassageiro;
	
	private EditText etKm;
	private EditText etKmL;
	private EditText etValorCombustivel;
	private EditText etNumeroPassageiros;
	
	private Button buttonCalcular;
	private Button buttonAddItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_calculadora_viagem);
		init();
	}
	
	private void init() {
		tvResultado = (TextView) findViewById(R.id.tvResultado);
		tvResultadoPorPassageiro = (TextView) findViewById(R.id.tvResultadoPorPassageiro);

		etKm = (EditText) findViewById(R.id.etKm);
		etKmL = (EditText) findViewById(R.id.etKmL);
		etValorCombustivel = (EditText) findViewById(R.id.etValorCombustivel);
		etNumeroPassageiros = (EditText) findViewById(R.id.etNumeroPassageiros);

		buttonCalcular = (Button) findViewById(R.id.btCalcularValorViagem);
		buttonCalcular.setOnClickListener(CalculadoraViagemActivity.this);

		buttonAddItem = (Button) findViewById(R.id.btAddItem);
		buttonAddItem.setOnClickListener(CalculadoraViagemActivity.this);

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
		case R.id.btCalcularValorViagem:
			if (isValidState()) {
				calcular();
			}
			break;

		case R.id.btAddItem:
			doAddItem();
			break;
		}
	}

	private void calcular() {
		double sum = sumItens();
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
		if (getNumItens() == MAX_NUM_ITENS) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					getString(R.string.msg_numero_limite_itens));
			return;
		}

		newItem((LinearLayout) findViewById(R.id.firstLinearLayout));
	}

	private boolean isValidState() {
		if (isBlankFields()) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					getString(R.string.msg_campos_obrigatorios));

			return false;
		}

		if (isInvalidFields()) {
			ToastUtil.show(CalculadoraViagemActivity.this,
					getString(R.string.msg_valor_invalido));

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
			return isZeroOrNull(etKmL) 
					|| isZeroOrNull(etKm)
					|| isZeroOrNull(etNumeroPassageiros) 
					|| isZeroOrNull(etValorCombustivel);

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
	
	private boolean newItem(final LinearLayout linearLayout) {
		itensIDs.add(++idCounter);

		final LinearLayout externLinerLayout = new LinearLayout(CalculadoraViagemActivity.this);
		externLinerLayout.setId(7 * idCounter);
		externLinerLayout.setWeightSum(100);
		externLinerLayout.setOrientation(LinearLayout.HORIZONTAL);

		final LinearLayout textLinearLayout = new LinearLayout(CalculadoraViagemActivity.this);
		textLinearLayout.setId(1000 + idCounter);
		textLinearLayout.setWeightSum(60);
		textLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		final EditText editText = new EditText(CalculadoraViagemActivity.this);
		editText.setId(100 + idCounter);
		editText.setHint(R.string.hint_novo_item);
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		editText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		textLinearLayout.addView(editText);
		externLinerLayout.addView(textLinearLayout);

		final LinearLayout buttonLinearLayout = new LinearLayout(CalculadoraViagemActivity.this);
		buttonLinearLayout.setId(10 + idCounter);
		buttonLinearLayout.setWeightSum(40);
		buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		final Button button = new Button(CalculadoraViagemActivity.this);
		button.setId(idCounter);
		button.setBackgroundResource(R.drawable.remove_item);
		button.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (itensIDs.size() == 0) {
					return;
				}

				int toRemoveId = v.getId();
				linearLayout.removeView(findViewById(7 * toRemoveId));
				if (itensIDs.contains(toRemoveId)) {
					itensIDs.remove(toRemoveId);
				}
			}
		});

		buttonLinearLayout.addView(button);
		externLinerLayout.addView(buttonLinearLayout);
		linearLayout.addView(externLinerLayout);

		return true;
	}

	private int getNumItens() {
		return itensIDs.size();
	}
	
	private double sumItens() {
		double sum = 0.0;
		for (Integer id : itensIDs) {
			try {
				sum += toDouble((EditText) findViewById(100 + id));
			} catch (Exception e) {
				// ignored because edit text is empty
			}
		}
		return sum;
	}

}