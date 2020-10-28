package com.henrysgrocery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockItem {
	SOUP("Soup", "Tin", null, 0.65),
	BREAD("Bread", "Loaf", Discount.BUY_2_TINS_SOUP_GET_BREAD_HALF_PRICE, 0.80),
	MILK("Milk", "Bottle", null, 1.3),
	APPLES("Apple", "Single", Discount.APPLES_10_DISCOUNT, 0.1);
	
	private String name;
	private String units;
	private Discount discount;
	private double cost;
}
