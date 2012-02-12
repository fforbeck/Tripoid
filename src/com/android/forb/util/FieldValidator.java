package com.android.forb.util;

import static com.android.forb.util.NumberUtil.isZeroOrNull;

public class FieldValidator {
	
	public static boolean isValid(Number dbls[]) {
		try {
			for (Number number : dbls) {
				isZeroOrNull(number);
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}

	}

}
