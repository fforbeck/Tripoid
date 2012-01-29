package com.android.forb.app;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.forb.app.MyTripCalculator;

public class TripCalculatorTest extends
		ActivityInstrumentationTestCase2<MyTripCalculator> {

	private MyTripCalculator tripCalcActivity;
	private EditText etKm;
	private EditText etKmL;
	private EditText etNumeroPassageiros;
	private EditText etValorCombustivel;
	private Button buttonCalcular;
	private TextView resultadoTotal;
	private TextView resultadoDividido;

	public TripCalculatorTest() {
		super(MyTripCalculator.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tripCalcActivity = this.getActivity();
		etKm = (EditText) tripCalcActivity.findViewById(com.android.forb.app.R.id.etKm);
		etKmL = (EditText) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.etKmL);
		etNumeroPassageiros = (EditText) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.etNumeroPassageiros);
		etValorCombustivel = (EditText) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.etValorCombustivel);
		buttonCalcular = (Button) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.btCalcular);
		resultadoTotal = (TextView) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.tvResultado);
		resultadoDividido = (TextView) tripCalcActivity
				.findViewById(com.android.forb.app.R.id.tvResultadoPorPassageiro);
	}

	public void testPreconditions() {
		assertNotNull(tripCalcActivity);
		assertNotNull(etKm);
		assertNotNull(etKmL);
		assertNotNull(etNumeroPassageiros);
		assertNotNull(etValorCombustivel);
		assertNotNull(buttonCalcular);
		assertNotNull(resultadoTotal);
		assertNotNull(resultadoDividido);
	}

	public void testValoresCorretos() {
		tripCalcActivity.runOnUiThread(new Runnable() {
			public void run() {
				etKm.setText("300");
				etKmL.setText("11.5");
				etNumeroPassageiros.setText("4");
				etValorCombustivel.setText("1.79");

				buttonCalcular.requestFocus();
				buttonCalcular.performClick();
			}
		});

		assertEquals("Total da despesa: 46.7", resultadoTotal.getText()
				.toString());
		assertEquals("Despesa por passageiro: 11.68", resultadoDividido
				.getText().toString());
	}

	public void testValoresNulos() {
		tripCalcActivity.runOnUiThread(new Runnable() {
			public void run() {
				etKm.setText(null);
				etKmL.setText(null);
				etNumeroPassageiros.setText(null);
				etValorCombustivel.setText(null);

				buttonCalcular.requestFocus();
				buttonCalcular.performClick();
			}
		});
		// String displayToast =
		// tripCalcActivity.getIntent().getStringExtra(DISPLAY_TOAST);
		// assertEquals("Por favor preencha todos os campos.", displayToast);
	}

	public void testValoresZero() {
		tripCalcActivity.runOnUiThread(new Runnable() {
			public void run() {
				etKm.setText("0.0");
				etKmL.setText("0.0");
				etNumeroPassageiros.setText("0");
				etValorCombustivel.setText("0.0");

				buttonCalcular.requestFocus();
				buttonCalcular.performClick();
			}
		});
		// String displayToast =
		// tripCalcActivity.getIntent().getStringExtra(DISPLAY_TOAST);
		// assertEquals("Por favor preencha todos os campos.", displayToast);
	}

}
