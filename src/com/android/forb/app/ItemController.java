package com.android.forb.app;

import static com.android.forb.util.NumberUtil.toDouble;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ItemController extends Activity {

	private static ItemController instance = null;

	private final Set<Integer> itensIDs = new HashSet<Integer>();

	private static int idCounter = 0;

	private ItemController() {

	}

	public static ItemController getInstance() {
		if (instance == null)
			instance = new ItemController();
		return instance;
	}

	public boolean newItem(final Context context, final LinearLayout linearLayout) {
		itensIDs.add(++idCounter);

		final LinearLayout externLinerLayout = new LinearLayout(context);
		externLinerLayout.setId(7 * idCounter);
		externLinerLayout.setWeightSum(100);
		externLinerLayout.setOrientation(LinearLayout.HORIZONTAL);

		final LinearLayout textLinearLayout = new LinearLayout(context);
		textLinearLayout.setId(1000 + idCounter);
		textLinearLayout.setWeightSum(60);
		textLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		final EditText editText = new EditText(context);
		editText.setId(100 + idCounter);
		editText.setHint("Exemplo: 30.50 (Pneu furado)");
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		editText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		textLinearLayout.addView(editText);
		externLinerLayout.addView(textLinearLayout);

		final LinearLayout buttonLinearLayout = new LinearLayout(context);
		buttonLinearLayout.setId(10 + idCounter);
		buttonLinearLayout.setWeightSum(40);
		buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		final Button button = new Button(context);
		button.setId(idCounter);
		button.setText("X");
		button.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (itensIDs.size() == 0) {
					return;
				}

				int toRemoveId = v.getId();
				linearLayout.removeView(((TripoidActivity) context).findViewById(7 * toRemoveId));
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

	public int getNumItens() {
		return itensIDs.size();
	}
	
	public double sumItens(Context context) {
		double sum = 0.0;
		for (Integer id : itensIDs) {
			try {
				sum += toDouble((EditText) ((TripoidActivity) context).findViewById(100 + id));
			} catch (Exception e) {
				// ignored because edit text is empty
			}
		}
		return sum;
	}
}
