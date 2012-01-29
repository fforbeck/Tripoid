package com.android.forb.util;

import static android.view.Gravity.CENTER_VERTICAL;
import static android.widget.Toast.LENGTH_SHORT;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static final void show(Context context, String msg) {
		final Toast toast = Toast.makeText(context, msg, LENGTH_SHORT);
		toast.setGravity(CENTER_VERTICAL, 0, 0);
		toast.show();
	}

}
