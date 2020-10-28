package com.henrysgrocery.model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Discount {
	BUY_2_TINS_SOUP_GET_BREAD_HALF_PRICE("Buy 2 tins of soup and get a loaf of bread half price",
			LocalDate.now().minusDays(1), LocalDate.now().minusDays(1).plusDays(7), StockItem.SOUP, 2, .5),
	APPLES_10_DISCOUNT("Apples have a 10% discount", LocalDate.now().plusDays(3),
			LocalDate.now().plusDays(3).plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()), null, 1, 0.9);

	private String name;
	private LocalDate validFrom;
	private LocalDate validTo;
	private StockItem applicableItemToBuyForDiscount;
	private int countApplicableItemToBuy;
	private double discountedRate;
}
