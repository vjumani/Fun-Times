package com.henrysgrocery.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.henrysgrocery.utils.Utilities;

public class UtilitiesTest {
	@Test
	public void givenPrice_whenDecimalsContainsNonZeroDigits_thenReturnAllDigits() {
		double result = Utilities.formatTo2Digits(1.11000000d);
		assertEquals("1.11", String.valueOf(result));
	}

	@Test
	public void givenPrice_whenOnesContainsZero_thenDontIncludeOnes() {
		double result = Utilities.formatTo2Digits(1.10000000d);
		assertEquals("1.1", String.valueOf(result));
	}
	
	@Test
	public void givenPrice_whenDecimalsAllZeros_thenIncludeOneZero() {
		double result = Utilities.formatTo2Digits(1.00000000d);
		assertEquals("1.0", String.valueOf(result));
	}

}
