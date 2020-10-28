package com.henrysgrocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class BillingItem {
	private BasketItem basketItem;
	private double basketItemPrice;
}
