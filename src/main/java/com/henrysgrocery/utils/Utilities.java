package com.henrysgrocery.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilities {

	public static double formatTo2Digits(double currency) {
		return new BigDecimal(currency).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
