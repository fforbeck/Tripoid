package com.android.forb.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import android.widget.EditText;

public class NumberUtil {

	public static final String format(Double value) {
		if (value == null) {
			return "";
		}

		return new DecimalFormat("###,###,###.00").format(value);
	}

	public static final boolean isZeroOrNull(EditText editText) {
		return isZeroOrNull(toDouble(editText));
	}

	public static final boolean isZeroOrNull(Number number) {
		return number == null || number.equals(0) || number.equals(0L)
				|| number.equals(0F) || number.equals(0D);
	}

	public static final Double round(Double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN)
				.doubleValue();
	}

	public static final Double toDouble(EditText editText) {
		if (editText == null || editText.getText() == null
				|| StringUtil.isBlank(editText)) {
			throw new IllegalArgumentException(
					"EditText can't be null or empty");
		}

		return Double.parseDouble(editText.getText().toString());

	}

	public static boolean ge(double valueA, double valueB) {
		return Double.compare(valueA, valueB) >= 0;
	}

}
