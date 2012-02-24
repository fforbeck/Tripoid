package com.android.forb.app;

import static android.graphics.Color.rgb;
import static com.android.forb.util.NumberUtil.isZeroOrNull;
import static com.android.forb.util.NumberUtil.round;
import static com.android.forb.util.NumberUtil.toDouble;
import static com.android.forb.util.StringUtil.isBlank;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.forb.util.AppUtil;
import com.android.forb.util.ToastUtil;

public class CalculadoraConsumoActivity extends Activity implements View.OnClickListener {

	private EditText etKmInicial;
	private EditText etKmFinal;
	private Button buttonCalcularConsumo;
	private View dialogLayout;
	private AlertDialog alertDialog;
	private EditText etLitrosComb;
	private TextView tvConsumoMedio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_calculadora_consumo);
		
		init();
	}

	private void init() {
		etKmInicial = (EditText) findViewById(R.id.etKmInicial);
		etKmFinal = (EditText) findViewById(R.id.etKmFinal);
		etLitrosComb = (EditText) findViewById(R.id.etLCombustivel);

		buttonCalcularConsumo = (Button) findViewById(R.id.btCalcularConsumo);
		buttonCalcularConsumo.setOnClickListener(CalculadoraConsumoActivity.this);
		
		alertDialog = createDialog();
		
		setValues();
	}
	
	private void setValues() {
		etKmInicial.setText("2024");
		etKmFinal.setText("2750");
		etLitrosComb.setText("120");
	}
	
	private AlertDialog createDialog() {
		final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		dialogLayout = inflater.inflate(R.layout.dialog_resultado_calc_consumo,
		                               (ViewGroup) findViewById(R.id.layout_root_calc_consumo));
		
		final Builder builder = new AlertDialog.Builder(CalculadoraConsumoActivity.this);
		builder.setView(dialogLayout);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
                return;
          } });
		
		final AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getString(R.string.lbl_title_dialog_consumo));
		alertDialog.setCancelable(true);
		return alertDialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btCalcularConsumo:
			if (isValidState()) {
				calcularConsumo();
			}
			break;
		}
	}

	private boolean isValidState() {
		if (isBlankFields()) {
			ToastUtil.show(CalculadoraConsumoActivity.this,
					getString(R.string.msg_campos_obrigatorios));

			return false;
		}

		if (isInvalidFields()) {
			ToastUtil.show(CalculadoraConsumoActivity.this,
					getString(R.string.msg_valor_invalido));

			return false;
		}

		return true;
	}
	
	private boolean isBlankFields() {
		return isBlank(etKmFinal) 
				|| isBlank(etKmInicial) 
				|| isBlank(etLitrosComb);
	}

	private boolean isInvalidFields() {
		try {
			return isZeroOrNull(etKmFinal) 
					|| isZeroOrNull(etKmInicial)
					|| isZeroOrNull(etLitrosComb);

		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private void calcularConsumo() {
		final double diferenca = toDouble(etKmFinal) - toDouble(etKmInicial);
		final double kmPorLitro = round((diferenca / toDouble(etLitrosComb)));
		
		setTextOnTVConsumoMedio(kmPorLitro);
		
		alertDialog.show();
		
		AppUtil.hideSwKeyBoard(etLitrosComb, CalculadoraConsumoActivity.this);
	}
	
	private void setTextOnTVConsumoMedio(final Double value) {
		tvConsumoMedio = (TextView) dialogLayout.findViewById(R.id.tvConsumoMedio);
		tvConsumoMedio.setTextColor(rgb(120, 218, 54));
		tvConsumoMedio.setText(value + " Km/L");
	}

}