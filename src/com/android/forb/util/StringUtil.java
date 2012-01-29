package com.android.forb.util;

import android.widget.EditText;

public class StringUtil {

	public static final boolean isBlank(EditText editText) {
		return editText == null || editText.getText() == null || editText.getText().toString() == null 
				|| editText.getText().toString().trim().equals("");
	}

}
