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
import com.android.forb.util.NumberUtil;
import com.android.forb.util.ToastUtil;

public class CalculadoraCombustivelActivity extends Activity implements View.OnClickListener {

	private EditText etPrecoEtanol;
	private EditText etPrecoGasolina;
	private Button btVerCombustivelRecomendado;
	private TextView tvCombustivelRecomendado;
	private View dialogLayout;
	private AlertDialog alertDialog;

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
		
		alertDialog = createDialog();
		
		setValues();
	}
	
	private AlertDialog createDialog() {
		final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		dialogLayout = inflater.inflate(R.layout.dialog_resultado_calc_combustivel,
		                               (ViewGroup) findViewById(R.id.layout_root_calc_combust));
		
		final Builder builder = new AlertDialog.Builder(CalculadoraCombustivelActivity.this);
		builder.setView(dialogLayout);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
                return;
          } });
		
		final AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getString(R.string.lbl_combustivel));
		alertDialog.setCancelable(true);
		return alertDialog;
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
			setTextOnTVCombustivelRecomendado(result, getString(R.string.msg_recomendar_gasolina));
		} else {
			setTextOnTVCombustivelRecomendado(result, getString(R.string.msg_recomendar_etanol));
		}
		
		alertDialog.show();
		
		AppUtil.hideSwKeyBoard(etPrecoGasolina, CalculadoraCombustivelActivity.this);
	}

	private void setTextOnTVCombustivelRecomendado(double result, String recomend) {
		tvCombustivelRecomendado = (TextView) dialogLayout.findViewById(R.id.tvCombustivelRecomendado);
		tvCombustivelRecomendado.setTextColor(rgb(120, 218, 54));
		tvCombustivelRecomendado.setText(recomend);
	}
	
}